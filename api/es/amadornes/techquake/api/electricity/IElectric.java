package es.amadornes.techquake.api.electricity;

import net.minecraftforge.common.ForgeDirection;

/**
 * TechQuake
 * 
 * IElectric.java
 * 
 * ALWAYS REMEMBER TO ASSIGN AN ENERGY NETWORK ON THE FIRST TICK BEFORE RUNNING ANY CODE!!!!
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public interface IElectric {
    
    public EnergyNetwork getEnergyNetwork();
    public void setEnergyNetwork(EnergyNetwork network);

    public ForgeDirection[] getConnectableSides();
    public boolean canConnectOnSide(ForgeDirection d);
    
}
