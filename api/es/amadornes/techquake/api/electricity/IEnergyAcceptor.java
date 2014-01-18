package es.amadornes.techquake.api.electricity;


/**
 * TechQuake
 * 
 * IEnergyAcceptor.java
 * 
 * This interface must be implemented by any block that wants to input energy.
 * 
 * If you want to be able to store it, please check {@link IEnergyBuffer}
 * If you also want to be able to output it, please check {@link IEnergyEmitter}
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public interface IEnergyAcceptor extends IElectric {
    
    public int getMaxInput();
    
    public int insertEnergy(int amount, boolean doInsert);
    
}
