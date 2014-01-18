package es.amadornes.techquake.tileentity.electric.machine;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import es.amadornes.techquake.api.electricity.EnergyNetwork;
import es.amadornes.techquake.api.electricity.IElectric;
import es.amadornes.techquake.api.electricity.IEnergyAcceptor;
import es.amadornes.techquake.api.electricity.IEnergyBuffer;
import es.amadornes.techquake.api.electricity.IEnergyConductor;
import es.amadornes.techquake.api.electricity.Unit;
import es.amadornes.techquake.api.loc.Vector3;
import es.amadornes.techquake.block.BlockManager;
import es.amadornes.techquake.lib.InventoryHelper;
import es.amadornes.techquake.packet.PacketFurnaceStausUpdate;

public class TileEntityElectricFurnace extends TileEntity implements
        ISidedInventory, IEnergyBuffer, IEnergyAcceptor, IEnergyConductor {
    
    private ItemStack[] slots        = new ItemStack[4];        // Two input,
                                                                 // two output
    private int[]       slots_top    = new int[] { 0, 1 };      // Input slots
    private int[]       slots_sides  = new int[] { 0, 1, 2, 3 }; // Input and
                                                                 // output slots
    private int[]       slots_bottom = new int[] { 2, 3 };      // Output slots
                                                                 
    public boolean      isBurning    = false;
    
    public double       progress     = 0;
    public double       lidAngle     = 0;
    
    public int          opened       = 0;
    
    @Override
    public int getSizeInventory() {
    
        return slots.length;
    }
    
    @Override
    public ItemStack getStackInSlot(int slot) {
    
        return slots[slot];
    }
    
    @Override
    public ItemStack decrStackSize(int slot, int amount) {
    
        ItemStack itemstack = getStackInSlot(slot);
        
        if (itemstack != null) {
            if (itemstack.stackSize <= amount) {
                setInventorySlotContents(slot, null);
            } else {
                itemstack = itemstack.splitStack(amount);
                onInventoryChanged();
            }
        }
        return itemstack;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
    
        return slots[i];
    }
    
    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
    
        slots[i] = itemstack;
    }
    
    @Override
    public String getInvName() {
    
        return BlockManager.FURNACE.getUnlocalizedName();
    }
    
    @Override
    public boolean isInvNameLocalized() {
    
        return false;
    }
    
    @Override
    public int getInventoryStackLimit() {
    
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
    
        return player
                .getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
    }
    
    @Override
    public void openChest() {
    
    }
    
    @Override
    public void closeChest() {
        opened--;
    }
    
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
    
        if (slot == 0 || slot == 1) { return canItemBeSmelted(item); }
        return false;
    }
    
    @Override
    public boolean canInsertItem(int slot, ItemStack item, int sideID) {
    
        ForgeDirection side = ForgeDirection.getOrientation(sideID);
        boolean can = false;
        switch (side) {
            case UP:
                for (int i : slots_top)
                    if (i == slot) can = true;
                break;
            case DOWN:
                for (int i : slots_bottom)
                    if (i == slot) can = true;
                break;
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                for (int i : slots_sides)
                    if (i == slot) can = true;
                break;
            default:
                break;
        }
        if (can) { return isItemValidForSlot(slot, item); }
        
        return false;
    }
    
    @Override
    public boolean canExtractItem(int slot, ItemStack item, int sideID) {
    
        if (slot == 2 || slot == 3) {
            ForgeDirection side = ForgeDirection.getOrientation(sideID);
            switch (side) {
                case UP:
                    for (int i : slots_top)
                        if (i == slot) return true;
                    break;
                case DOWN:
                    for (int i : slots_bottom)
                        if (i == slot) return true;
                    break;
                case NORTH:
                case SOUTH:
                case EAST:
                case WEST:
                    for (int i : slots_sides)
                        if (i == slot) return true;
                    break;
                default:
                    break;
            }
        }
        
        return false;
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
    
    @Override
    public int getMaxInput() {
    
        return Unit.LV.getPacketSize();
    }
    
    private int buffer = 0;
    
    @Override
    public int insertEnergy(int amount, boolean doInsert) {
    
        int inserted = Math.min(getMaxStored() - buffer, amount);
        
        if (doInsert) setBuffer(buffer + inserted);
        
        return inserted;
    }
    
    @Override
    public int getMaxStored() {
    
        return 50000;
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
    public int[] getAccessibleSlotsFromSide(int sideID) {
    
        ForgeDirection side = ForgeDirection.getOrientation(sideID);
        switch (side) {
            case UP:
                return slots_top;
            case DOWN:
                return slots_bottom;
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                return slots_sides;
            default:
                break;
        }
        return null;
    }
    
    public NBTTagCompound getTagStatus() {
    
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("isBurning", isBurning);
        tag.setDouble("angle", lidAngle);
        
        NBTTagCompound tile = new NBTTagCompound();
        tile.setInteger("x", xCoord);
        tile.setInteger("y", yCoord);
        tile.setInteger("z", zCoord);
        tile.setInteger("dim", worldObj.provider.dimensionId);
        
        tag.setCompoundTag("tile", tile);
        
        return tag;
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
            
            // Do lid animation
            {
                if (lidAngle <= 135 && opened > 0) {
                    lidAngle += 5;
                    PacketDispatcher.sendPacketToAllInDimension(
                            new PacketFurnaceStausUpdate(this),
                            worldObj.provider.dimensionId);
                }
                if (lidAngle >= 0 && opened <= 0) {
                    lidAngle -= 5;
                    PacketDispatcher.sendPacketToAllInDimension(
                            new PacketFurnaceStausUpdate(this),
                            worldObj.provider.dimensionId);
                }
            }
            
            // Do smelting
            {
                if (buffer > 2) {
                    boolean can0 = (slots[0] != null && canBurn(0));
                    boolean can1 = (slots[1] != null && canBurn(1));
                    
                    if (can0 || can1) {
                        if (!isBurning) {
                            isBurning = true;
                            PacketDispatcher.sendPacketToAllInDimension(
                                    new PacketFurnaceStausUpdate(this),
                                    worldObj.provider.dimensionId);
                        }
                    } else {
                        if (isBurning) {
                            isBurning = false;
                            PacketDispatcher.sendPacketToAllInDimension(
                                    new PacketFurnaceStausUpdate(this),
                                    worldObj.provider.dimensionId);
                        }
                    }
                    
                    if (!can0 && !can1) progress = 0;
                    
                    if (buffer > 0) {
                        int decrease = 0;
                        
                        if (can0 || (!can0 && can1)) decrease += 2;
                        if (can1 || (can0 && !can1)) decrease += 2;
                        
                        setBuffer(buffer - decrease);
                        
                        if (decrease > 1) {
                            double pro = buffer;
                            pro /= getMaxStored();
                            pro *= 5;
                            pro = Math.max(0.05, pro);
                            progress += pro;
                        }
                        
                        if (progress >= 50) {
                            if (can0 && !can1) {
                                burn(0);
                                burn(0);
                            } else if (can1 && !can0) {
                                burn(1);
                                burn(1);
                            } else {
                                burn(0);
                                burn(1);
                            }
                            onInventoryChanged();
                            progress -= 50;
                        }
                    }
                }
            }
        }
    }
    
    private boolean canBurn(int slot) {
    
        return InventoryHelper.canAdd(new ItemStack[] { slots[2], slots[3] },
                FurnaceRecipes.smelting().getSmeltingResult(slots[slot]));
    }
    
    private void burn(int slot) {
    
        if (canBurn(slot)) {
            ItemStack[] out = InventoryHelper.add(new ItemStack[] { slots[2],
                    slots[3] },
                    FurnaceRecipes.smelting().getSmeltingResult(slots[slot])
                            .copy());
            slots[2] = out[0];
            slots[3] = out[1];
            slots[slot] = InventoryHelper.consume(slots[slot], 1);
        }
    }
    
    public static boolean canItemBeSmelted(ItemStack item) {
    
        return FurnaceRecipes.smelting().getSmeltingResult(item) != null;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tag) {
    
        super.writeToNBT(tag);
        
        tag.setInteger("buffer", buffer);
        
        tag.setDouble("progress", progress);
        
        NBTTagList nbttaglist = new NBTTagList();
        
        for (int i = 0; i < this.slots.length; ++i) {
            if (this.slots[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.slots[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        
        tag.setTag("Inventory", nbttaglist);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tag) {
    
        super.readFromNBT(tag);
        
        buffer = tag.getInteger("buffer");
        
        progress = tag.getDouble("progress");
        
        NBTTagList nbttaglist = tag.getTagList("Inventory");
        this.slots = new ItemStack[this.getSizeInventory()];
        
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
                    .tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            
            if (b0 >= 0 && b0 < this.slots.length) {
                this.slots[b0] = ItemStack
                        .loadItemStackFromNBT(nbttagcompound1);
            }
        }
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
    
}
