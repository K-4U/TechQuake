package es.amadornes.techquake.tileentity.electric;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import es.amadornes.techquake.api.electricity.EnergyNetwork;
import es.amadornes.techquake.api.electricity.IElectric;
import es.amadornes.techquake.api.electricity.IEnergyRemoteConductor;
import es.amadornes.techquake.api.loc.Vector3;

public class TileEntityElectricPost extends TileEntity implements
        IEnergyRemoteConductor {
    
    private EnergyNetwork net;
    
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
    
        return new ForgeDirection[] { ForgeDirection.NORTH,
                ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST };
    }
    
    @Override
    public boolean canConnectOnSide(ForgeDirection d) {
        TileEntity te = new Vector3(this).getRelative(d).getTileEntity();
        if(te == null || !(te instanceof IElectric))
            return false;
        IElectric e = ((IElectric)(te));
        List<ForgeDirection> myDirs = new ArrayList<ForgeDirection>();
        List<ForgeDirection> teDirs = new ArrayList<ForgeDirection>();
        for(ForgeDirection di : getConnectableSides())
            myDirs.add(di);
        for(ForgeDirection di : e.getConnectableSides())
            teDirs.add(di);
        
        return myDirs.contains(d) && teDirs.contains(d.getOpposite());
    }
    
    List<IElectric> connected = new ArrayList<IElectric>();
    
    @Override
    public IElectric[] getConnectedDevices() {
    
        return connected.toArray(new IElectric[connected.size()]);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        
        NBTTagCompound con = new NBTTagCompound();
        int i = 0;
        for(IElectric e : connected){
            con.setString("c" + i, new Vector3((TileEntity) e).toString());
            i++;
        }
        con.setInteger("amt", i);
        
        tag.setCompoundTag("connected", con);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        NBTTagCompound con = tag.getCompoundTag("connected");
        for(int i = 0; i < con.getInteger("amt"); i++){
            connected.add((IElectric) Vector3.fromString(con.getString("c" + i)).getTileEntity());
        }
    }
    
}
