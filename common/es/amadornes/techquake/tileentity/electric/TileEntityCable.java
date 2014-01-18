package es.amadornes.techquake.tileentity.electric;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import codechicken.multipart.TileMultipart;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import es.amadornes.techquake.api.electricity.EnergyNetwork;
import es.amadornes.techquake.api.electricity.IElectric;
import es.amadornes.techquake.api.electricity.IEnergyConductor;
import es.amadornes.techquake.api.loc.Vector3;

/**
 * TechQuake
 * 
 * TileEntityCable.java
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public class TileEntityCable extends TileMultipart implements IEnergyConductor {
    
    private EnergyNetwork net = null;
    
    @Override
    public EnergyNetwork getEnergyNetwork() {
    
        return net;
    }
    
    @Override
    public void setEnergyNetwork(EnergyNetwork network) {
    
        net = network;
    }
    
    @Override
    public ForgeDirection[] getConnectableSides() {
    
        return ForgeDirection.VALID_DIRECTIONS;
    }
    
    @Override
    public boolean canConnectOnSide(ForgeDirection d) {
    
        TileEntity te = new Vector3(this).getRelative(d).getTileEntity();
        if (te == null || !(te instanceof IElectric)) return false;
        IElectric e = ((IElectric) (te));
        List<ForgeDirection> myDirs = new ArrayList<ForgeDirection>();
        List<ForgeDirection> teDirs = new ArrayList<ForgeDirection>();
        for (ForgeDirection di : getConnectableSides())
            myDirs.add(di);
        for (ForgeDirection di : e.getConnectableSides())
            teDirs.add(di);
        
        return myDirs.contains(d) && teDirs.contains(d.getOpposite());
    }
    
    private boolean firstTick = true;
    
    @Override
    public void updateEntity() {
    
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            if (firstTick) {
                firstTick = false;
                setEnergyNetwork(EnergyNetwork.getEnergyNetwork(worldObj,
                        xCoord, yCoord, zCoord));
            }
            net.tick(this);
        }
    }
    
}
