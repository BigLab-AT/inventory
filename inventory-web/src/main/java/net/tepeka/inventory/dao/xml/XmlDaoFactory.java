package net.tepeka.inventory.dao.xml;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import net.tepeka.inventory.dao.RaspberryPiDao;

public class XmlDaoFactory {

  public static final HashMap<String, RaspberryPiDao> rpiDaos;

  static {
    rpiDaos = new HashMap<>();
  }

  public static RaspberryPiDao createRaspberryPiDao(String path) throws IOException, JAXBException {
    if (!rpiDaos.containsKey(path)) {
      rpiDaos.put(path, new RaspberryPiXmlDao(path));
    }
    return rpiDaos.get(path);
  }
}
