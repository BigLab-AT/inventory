package net.tepeka.inventory.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.tepeka.inventory.dao.RaspberryPiDao;
import net.tepeka.inventory.dao.app.AppProperties;
import net.tepeka.inventory.dao.dummy.DummyDaoFactory;
import net.tepeka.inventory.dao.xml.XmlDaoFactory;
import net.tepeka.inventory.entity.RaspberryPi;
import net.tepeka.inventory.entity.RaspberryPi.System.InterfaceType;
import net.tepeka.inventory.entity.service.UpdateData;
import net.tepeka.inventory.entity.service.UpdateResult;
import net.tepeka.inventory.model.HardwareRevisionTable.HardwareRevision;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RaspberryManager {

  private static final String HOSTNAME_PREFIX = "rpi";

  private final Logger log = LogManager.getLogger(RaspberryManager.class.getName());
  private final String apiToken;

  private RaspberryPiDao dao;

  public RaspberryManager(String xmlPath) {
    RaspberryPiDao dao = null;
    try {
      dao = XmlDaoFactory.createRaspberryPiDao(xmlPath);
    } catch (Exception e) {
      // error in path
      dao = DummyDaoFactory.createRaspberryPiDao();
      e.printStackTrace();

    } finally {
      this.dao = dao;
    }
    apiToken = AppProperties.getInstance().getApiToken();
  }

  public List<RaspberryPi> getAll() {
    return dao.getAll();
  }

  public RaspberryPi get(int id) {
    return dao.get(id);
  }

  public UpdateResult update(UpdateData data, String apiToken) {
    // -- validate token
    if (!this.apiToken.equals(apiToken)) {
      throw new IllegalAccessError("API token is not valid.");
    }
    // -- get raspberry
    RaspberryPi rpi = dao.get(data.getSerialnumber());
    if (rpi == null) {
      rpi = dao.create();
    }
    // -- update model
    HardwareRevision rev = HardwareRevisionTable.getByCode(data.getHardwareRevisionCode());
    rpi.model = rpi.model == null ? new RaspberryPi.Model() : rpi.model;
    rpi.model.code = data.getHardwareRevisionCode();
    if (rev != null) {
      rpi.model.revision = rev.revision;
      rpi.model.name = rev.model;
      rpi.model.memory = rev.memory;
    } else {
      log.warn(String.format("Did not find hardware revision with code '%s'.", rpi.model.code));
    }
    // -- update system
    Date now = Calendar.getInstance().getTime();
    rpi.system = rpi.system == null ? new RaspberryPi.System() : rpi.system;
    rpi.system.hostname = HOSTNAME_PREFIX + rpi.id;
    rpi.system.lastUpdated = now;
    rpi.system.localTime = data.getLocalTime();
    // -- update system.os
    rpi.system.os = rpi.system.os == null ? new RaspberryPi.System.Os() : rpi.system.os;
    rpi.system.os.kernel = data.getKernelVersion();
    rpi.system.os.name = data.getOs();
    rpi.system.os.version = data.getOsVersion();
    // -- update system.interfaces
    rpi.system.inter = new ArrayList<>();
    for (String v4 : data.getIpv4()) {
      InterfaceType i = new InterfaceType();
      i.version = 4;
      i.address = v4;
      rpi.system.inter.add(i);
    }
    for (String v6 : data.getIpv6()) {
      InterfaceType i = new InterfaceType();
      i.version = 6;
      i.address = v6;
      rpi.system.inter.add(i);
    }
    // -- update
    dao.update(rpi);
    // -- return hostname
    return new UpdateResult(rpi.system.hostname);
  }

}
