package net.tepeka.inventory.entity.service;

import java.util.Date;
import java.util.List;

public class UpdateData {

  private String serialnumber;
  private String kernelVersion;
  private String os;
  private String osVersion;
  private Date localTime;
  private List<String> ipv4;
  private List<String> ipv6;
  private String hostname;
  private Date lastUpdated;
  private String hardwareRevisionCode;

  public String getKernelVersion() {
    return kernelVersion;
  }

  public void setKernelVersion(String kernelVersion) {
    this.kernelVersion = kernelVersion;
  }

  public String getOs() {
    return os;
  }

  public void setOs(String os) {
    this.os = os;
  }

  public String getSerialnumber() {
    return serialnumber;
  }

  public void setSerialnumber(String serialNumber) {
    this.serialnumber = serialNumber;
  }

  public Date getLocalTime() {
    return localTime;
  }

  public void setLocalTime(Date localTime) {
    this.localTime = localTime;
  }

  public List<String> getIpv4() {
    return ipv4;
  }

  public void setIpv4(List<String> ipv4) {
    this.ipv4 = ipv4;
  }

  public List<String> getIpv6() {
    return ipv6;
  }

  public void setIpv6(List<String> ipv6) {
    this.ipv6 = ipv6;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public String getOsVersion() {
    return osVersion;
  }

  public void setOsVersion(String osVersion) {
    this.osVersion = osVersion;
  }

  public Date getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(Date lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  public String getHardwareRevisionCode() {
    return hardwareRevisionCode;
  }

  public void setHardwareRevisionCode(String hardwareRevisionCode) {
    this.hardwareRevisionCode = hardwareRevisionCode;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((serialnumber == null) ? 0 : serialnumber.hashCode());
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
    UpdateData other = (UpdateData) obj;
    if (serialnumber == null) {
      if (other.serialnumber != null)
        return false;
    } else if (!serialnumber.equals(other.serialnumber))
      return false;
    return true;
  }

}