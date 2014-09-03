package net.tepeka.inventory.entity;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
public class RaspberryPi {

  @XmlAttribute(required = true)
  public Integer id;
  @XmlAttribute
  public String serial;
  @XmlElement
  public Model model;
  @XmlElement
  public Possession possession;
  @XmlElement
  public Order order;
  @XmlElement
  public System system;

  public static class Model {
    @XmlAttribute
    public String code;
    @XmlElement
    public String name;
    @XmlElement
    public String revision;
    @XmlElement
    public String memory;
  }

  public static class Possession {
    @XmlAttribute(required = true)
    public String owner;
    @XmlElement
    public String commissioned;
    @XmlElement
    public Purpose purpose;
    @XmlElement
    public String comment;

    public static class Purpose {
      @XmlAttribute
      public String label;
      @XmlElement
      public String description;
      @XmlElementWrapper(name="references")
      public List<ReferenceType> reference;

      public static class ReferenceType {
        @XmlAttribute
        public String source;
        @XmlValue
        public String reference;
      }
    }
  }

  public static class Order {
    @XmlAttribute
    public Date date;
    @XmlElement
    public String shop;
    @XmlElement
    public String comment;
  }

  public static class System {
    @XmlAttribute(required = true)
    public Date lastUpdated;
    @XmlElement
    public String hostname;
    @XmlElement
    public Date localTime;
    @XmlElementWrapper(name="interfaces")
    public List<InterfaceType> inter;
    @XmlElement
    public Os os;

    public static class InterfaceType {
      @XmlAttribute
      public int version;
      @XmlElement
      public String address;
    }

    public static class Os {
      @XmlElement
      public String kernel;
      @XmlElement
      public String name;
      @XmlElement
      public String version;
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    RaspberryPi other = (RaspberryPi) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}