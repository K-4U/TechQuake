package es.amadornes.techquake.api.electricity;

/**
 * TechQuake
 * 
 * IEnergyConductor.java
 * 
 * This interface must be implemented by any block that wants to transfer energy.
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public interface IEnergyRemoteConductor extends IEnergyConductor {
    
    public IElectric[] getConnectedDevices();
    
}
