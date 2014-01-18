package es.amadornes.techquake.api.electricity;

/**
 * TechQuake
 * 
 * Unit.java
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public enum Unit {
    
    HV(256),
    MV(128),
    LV(64);
    
    private int packetSize;
    
    
    private Unit(int packetSize) {
        this.packetSize = packetSize;
    }
    
    
    public int getPacketSize() {
        return packetSize;
    }
    
    public static String NAME = "techquake.energy.unit";
    public static String NAME_SHORT = "techquake.energy.unit.short";
    
}
