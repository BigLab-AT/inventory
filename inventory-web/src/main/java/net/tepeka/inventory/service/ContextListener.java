package net.tepeka.inventory.service;

import net.tepeka.inventory.dao.RaspberryPiDao;
import net.tepeka.inventory.dao.app.AppProperties;
import net.tepeka.inventory.dao.xml.XmlDaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ContextListener implements ServletContextListener {
  private final Logger log = LogManager.getLogger(ContextListener.class.getName());

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    try {
      RaspberryPiDao dao = XmlDaoFactory.createRaspberryPiDao(AppProperties.getInstance().getXmlPath());
      dao.stop();
    } catch (Exception e) {
      log.error("contextDestroyed error",e);
    }
  }
}
