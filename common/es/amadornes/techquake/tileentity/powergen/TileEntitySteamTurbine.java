package es.amadornes.techquake.tileentity.powergen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import es.amadornes.techquake.api.electricity.EnergyNetwork;
import es.amadornes.techquake.api.electricity.IElectric;
import es.amadornes.techquake.api.electricity.IEnergyBuffer;
import es.amadornes.techquake.api.electricity.IEnergyEmitter;
import es.amadornes.techquake.api.electricity.Unit;

public class TileEntitySteamTurbine extends TileEntity implements
        IFluidHandler, IElectric, IEnergyEmitter, IEnergyBuffer {
    
    private FluidTank tank     = new FluidTank(
                                       FluidContainerRegistry.BUCKET_VOLUME);
    private float     rotation = 0F;
    
    public float getAmount() {
    
        float amt = tank.getFluidAmount();
        amt /= tank.getCapacity();
        return amt;
    }
    
    public float getRotation() {
    
        return rotation;
    }
    
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
    
        if (canFill(from, resource.getFluid())) {
            if (doFill) tank.fill(resource, doFill);
            return Math.min(tank.getCapacity() - tank.getFluidAmount(),
                    resource.amount);
        }
        return 0;
    }
    
    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource,
            boolean doDrain) {
    
        return null;
    }
    
    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
    
        return null;
    }
    
    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
    
        ForgeDirection inputSide = null;
        int meta = getBlockMetadata() % 6;
        switch (meta) {
            case 0:
                inputSide = ForgeDirection.UP;
                break;
            case 1:
                inputSide = ForgeDirection.DOWN;
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
        
        return fluid == FluidRegistry.getFluid("steam") && from == inputSide;
    }
    
    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
    
        return false;
    }
    
    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
    
        return new FluidTankInfo[] { tank.getInfo() };
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tag) {
    
        super.writeToNBT(tag);
        
        NBTTagCompound tankTag = new NBTTagCompound();
        tank.writeToNBT(tankTag);
        tag.setTag("tank", tankTag);
        
        tag.setFloat("rotation", rotation);
        
        tag.setInteger("buffer", buffer);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tag) {
    
        super.readFromNBT(tag);
        
        NBTTagCompound waterTag = tag.getCompoundTag("tank");
        tank.readFromNBT(waterTag);
        
        rotation = tag.getFloat("rotation");
        
        buffer = tag.getInteger("buffer");
    }
    
    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
    
        readFromNBT(pkt.data);
    }
    
    @Override
    public Packet getDescriptionPacket() {
    
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, tag);
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
            
            float mul = 10;
            float speed = getAmount() * mul;
            rotation += speed * 2;
            if (tank.getFluidAmount() > 0) {
                int drain = Math.max(1, Math.min((int) (speed * 2), 5));
                tank.drain(drain, true);
                
                double generated = getMaxStored();
                generated *= getAmount();
                generated = Math.floor(generated);
                setBuffer((int) (buffer + Math.min(getMaxStored() - buffer,
                        generated)));
            }
            
            PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());
        }
    }
    
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
    
        List<ForgeDirection> sides = new ArrayList<ForgeDirection>();
        for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS)
            if (d != ForgeDirection.getOrientation(blockMetadata).getOpposite()) sides
                    .add(d);
        ForgeDirection[] dirs = sides.toArray(new ForgeDirection[sides.size()]);
        sides.clear();
        return dirs;
    }
    
    @Override
    public boolean canConnectOnSide(ForgeDirection d) {
    
        return Arrays.asList(getConnectableSides()).contains(d);
    }
    
    int buffer = 0;
    
    @Override
    public int getMaxStored() {
    
        return 10000;
    }
    
    @Override
    public int getStored() {
    
        return buffer;
    }
    
    @Override
    public void setBuffer(int energy) {
    
        buffer = energy;
    }
    
    @Override
    public int getMaxOutput() {
    
        return Unit.LV.getPacketSize();
    }
    
    @Override
    public int drainEnergy(int amount, boolean doDrain) {
    
        int drained = Math.min(amount, buffer);
        if (doDrain) setBuffer(buffer - amount);
        return drained;
    }
    
}
