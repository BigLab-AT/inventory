package net.tepeka.inventory.model;

public final class HardwareRevisionTable {

  public static class HardwareRevision {
    public final String model;
    public final String revision;
    public final String memory;
    public final String[] code;

    public HardwareRevision(String model, String revision, String memory, String... code) {
      this.model = model;
      this.revision = revision;
      this.memory = memory;
      this.code = code == null ? new String[0] : code;
    }
  }

  //@formatter:off
  public static final HardwareRevision[] MODELS = new HardwareRevision[] { 
    new HardwareRevision(
        "Model B", 
        "Revision 1.0", 
        "256MB", 
        "0002"),
    new HardwareRevision(
        "Model B", 
        "Revision 1.0 + ECN0001 (no fuses, D14 removed)", 
        "256MB", 
        "0003"),
    new HardwareRevision(
        "Model B", 
        "Revision 2.0 Mounting holes", 
        "256MB", 
        "0004", "0005", "0006"), 
    new HardwareRevision(
        "Model A", 
        "Mounting holes", 
        "256MB", 
        "0007", "0008", "0009"),    
    new HardwareRevision(
        "Model B", 
        "Revision 2.0 Mounting holes", 
        "512MB", 
        "000d", "000e", "000f"),    
    new HardwareRevision(
        "Model B+", 
        "", 
        "512MB", 
        "0010"),
    new HardwareRevision(
        "Compute Module",
        "",
        "512MB",
        "0011"),
    new HardwareRevision(
        "Model A+",
        "",
        "256MB",
        "0012"),
    new HardwareRevision(
        "Pi 2 Model B",
        "",
        "1GB",
        "a01041")
  };
  //@formatter:on

  public static HardwareRevision getByCode(String code) {
    for (HardwareRevision hardwareRevision : MODELS) {
      for (String c : hardwareRevision.code) {
        if (c.equals(code)) {
          return hardwareRevision;
        }
      }
    }
    return null;
  }

}
