package net.tepeka.inventory.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.tepeka.inventory.dao.app.AppProperties;
import net.tepeka.inventory.entity.RaspberryPi;
import net.tepeka.inventory.entity.service.UpdateData;
import net.tepeka.inventory.entity.service.UpdateResult;
import net.tepeka.inventory.model.RaspberryManager;

import org.glassfish.jersey.message.XmlHeader;

@Path("/")
public class RaspberryPiResource {

  @Context
  ServletContext servlet;

  private RaspberryManager raspiMgr;

  @PostConstruct
  public void init() {
    raspiMgr = new RaspberryManager(AppProperties.getInstance().getXmlPath());
  }

  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @XmlHeader("<?xml-stylesheet type=\"text/xsl\" href=\"/inventory/resources/xslt/rpis.xsl\"?>")
  public List<RaspberryPi> getAll() {
    return raspiMgr.getAll();
  }

  @GET
  @Path("/{id}")
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @XmlHeader("<?xml-stylesheet type=\"text/xsl\" href=\"/inventory/resources/xslt/rpi.xsl\"?>")
  public RaspberryPi get(@PathParam("id") Integer id) {
    return raspiMgr.get(id);
  }

  @POST
  @Path("/update")
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
  public UpdateResult update(UpdateData data, @QueryParam(value = "token") String apiToken) {
    return raspiMgr.update(data, apiToken);
  }

}