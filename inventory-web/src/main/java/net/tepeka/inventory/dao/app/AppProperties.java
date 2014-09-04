package net.tepeka.inventory.dao.app;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppProperties {

  private static final String PROPFILE = "app.properties";
  private static final String PROPFILE_DEFAULT = "default-app.properties";

  private static final String PROP_XMLPATH = "xml-path";
  private static final String PROP_APITOKEN = "api-token";

  private final Logger log = LogManager.getLogger(AppProperties.class.getName());

  private String apiToken;
  private String xmlPath;

  private AppProperties() {
  }
  private static AppProperties Instance;
  public static AppProperties getInstance() {
    if (Instance == null) {
      Instance = new AppProperties();
    }
    return Instance;
  }

  public String getXmlPath() {
    if (xmlPath == null) {
      xmlPath = getProperty(PROP_XMLPATH);
    }
    return xmlPath;
  }

  public String getApiToken() {
    if (apiToken == null) {
      apiToken = getProperty(PROP_APITOKEN);
    }
    return apiToken;
  }

  private String getProperty(String property) {
    try {
      return new PropertiesConfiguration(PROPFILE).getString(property);

    } catch (ConfigurationException e) {
      try {
        return new PropertiesConfiguration(PROPFILE_DEFAULT).getString(property);
      
      } catch (ConfigurationException e1) {
        log.error(e1);
        return null;
      }

    }
  }
}
