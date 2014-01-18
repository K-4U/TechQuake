package es.amadornes.techquake.api.electricity;

/**
 * TechQuake
 * 
 * IEnergyEmitter.java
 * 
 * This interface must be implemented by any block that wants to output energy.
 * 
 * If you want to be able to store it, please check {@link IEnergyBuffer}
 * If you also want to be able to input it, please check {@link IEnergyAcceptor}
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public interface IEnergyEmitter extends IElectric {
    
    public int getMaxOutput();
    
    public int drainEnergy(int amount, boolean doDrain);
    
}
