package es.amadornes.techquake.api.electricity;

/**
 * TechQuake
 * 
 * IEnergyBuffer.java
 * 
 * This interface must be implemented by any block that wants to buffer energy.
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public interface IEnergyBuffer extends IElectric {
    
    public int getMaxStored();
    
    public int getStored();
    
    public void setBuffer(int energy);
    
}
