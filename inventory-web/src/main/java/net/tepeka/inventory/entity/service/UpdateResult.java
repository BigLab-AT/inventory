package net.tepeka.inventory.entity.service;

public class UpdateResult {

  private String hostname;

  public UpdateResult() {
  }

  public UpdateResult(String hostname) {
    this.hostname = hostname;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
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
    UpdateResult other = (UpdateResult) obj;
    if (hostname == null) {
      if (other.hostname != null)
        return false;
    } else if (!hostname.equals(other.hostname))
      return false;
    return true;
  }

}
