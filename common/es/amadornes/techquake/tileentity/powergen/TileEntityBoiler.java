package es.amadornes.techquake.tileentity.powergen;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
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
import es.amadornes.techquake.api.loc.Vector3;
import es.amadornes.techquake.fluid.FluidManager;
import es.amadornes.techquake.lib.ModInfo;

public class TileEntityBoiler extends TileEntity implements IFluidHandler,
        IInventory {
    
    private FluidTank   water              = new FluidTank(
                                                   FluidContainerRegistry.BUCKET_VOLUME * 16);
    private FluidTank   steam              = new FluidTank(
                                                   FluidContainerRegistry.BUCKET_VOLUME * 16);
    
    private int         tick               = 0;
    private boolean     up                 = true;
    
    public float        leftBurnTime       = 0;
    public float        leftTotalBurnTime  = 0;
    public float        rightBurnTime      = 0;
    public float        rightTotalBurnTime = 0;
    
    public float        temperature        = 20;
    
    private ItemStack[] inventory          = new ItemStack[2];
    
    public int getWater() {
    
        return water.getFluidAmount();
    }
    
    public int getSteam() {
    
        return steam.getFluidAmount();
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tag) {
    
        super.writeToNBT(tag);
        
        NBTTagCompound waterTag = new NBTTagCompound();
        water.writeToNBT(waterTag);
        tag.setTag("water", waterTag);
        
        NBTTagCompound steamTag = new NBTTagCompound();
        steam.writeToNBT(steamTag);
        tag.setTag("steam", steamTag);
        
        tag.setFloat("leftBurnTime", leftBurnTime);
        tag.setFloat("leftTotalBurnTime", leftTotalBurnTime);
        tag.setFloat("rightBurnTime", rightBurnTime);
        tag.setFloat("rightTotalBurnTime", rightTotalBurnTime);
        
        tag.setFloat("temperature", temperature);
        
        NBTTagList nbttaglist = new NBTTagList();
        
        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        
        tag.setTag("Inventory", nbttaglist);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tag) {
    
        super.readFromNBT(tag);
        
        NBTTagCompound waterTag = tag.getCompoundTag("water");
        water.readFromNBT(waterTag);
        
        NBTTagCompound steamTag = tag.getCompoundTag("steam");
        steam.readFromNBT(steamTag);
        
        leftBurnTime = tag.getFloat("leftBurnTime");
        leftTotalBurnTime = tag.getFloat("leftTotalBurnTime");
        rightBurnTime = tag.getFloat("rightBurnTime");
        rightTotalBurnTime = tag.getFloat("rightTotalBurnTime");
        
        temperature = tag.getFloat("temperature");
        
        NBTTagList nbttaglist = tag.getTagList("Inventory");
        this.inventory = new ItemStack[this.getSizeInventory()];
        
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
                    .tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            
            if (b0 >= 0 && b0 < this.inventory.length) {
                this.inventory[b0] = ItemStack
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
    
    private boolean send = false;
    
    @Override
    public void updateEntity() {
    
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            // Temperature calculations
            {
                // Do fuel stuff
                {
                    // Exponential consumption (0-100ºC)
                    float consumption = Math.min(temperature, 99);
                    consumption = 100 - consumption;
                    consumption /= 20;
                    consumption = Math.max(consumption, 1);
                    
                    float augment = 100 - Math.min(temperature, 100);
                    if (augment > 0 && augment < 1) {
                        augment += 1;
                    }
                    augment /= 2000F;
                    augment *= 1.5F;
                    
                    if ((Math.floor(temperature) > 100 && getWater() < 50)
                            || Math.floor(temperature) <= 100) {
                        // Left
                        {
                            if (leftTotalBurnTime == 0) {
                                ItemStack s = getStackInSlot(0);
                                if (s != null) {
                                    if (s.stackSize > 0) {
                                        leftTotalBurnTime = TileEntityFurnace
                                                .getItemBurnTime(s) / 5;
                                        decrStackSize(0, 1);
                                    }
                                }
                            }
                            
                            if (leftTotalBurnTime > 0) {
                                leftBurnTime += consumption;
                                temperature += augment;
                            } else {
                                temperature -= augment / 2;
                            }
                            
                            if (leftBurnTime >= leftTotalBurnTime) {
                                leftBurnTime -= leftTotalBurnTime;
                                leftTotalBurnTime = 0;
                            }
                        }
                        
                        // Right
                        {
                            if (rightTotalBurnTime == 0) {
                                ItemStack s = getStackInSlot(1);
                                if (s != null) {
                                    if (s.stackSize > 0) {
                                        rightTotalBurnTime = TileEntityFurnace
                                                .getItemBurnTime(s) / 5;
                                        decrStackSize(1, 1);
                                    }
                                }
                            }
                            
                            if (rightTotalBurnTime > 0) {
                                rightBurnTime += consumption;
                                temperature += augment;
                            } else {
                                temperature -= augment / 2;
                            }
                            
                            if (rightBurnTime >= rightTotalBurnTime) {
                                rightBurnTime -= rightTotalBurnTime;
                                rightTotalBurnTime = 0;
                            }
                        }
                    } else {
                        if (temperature - augment < 100) {
                            temperature = 100F;
                        } else {
                            temperature -= augment;
                        }
                    }
                }
                
                // Do temperature calculations
                {
                    if (temperature < 20) temperature = 20;
                    if (temperature > 1000) temperature = 1000;
                    if (temperature >= 500) {
                        worldObj.createExplosion((Entity) null, (int) xCoord,
                                (int) yCoord, (int) zCoord, 5.0F, true);
                        worldObj.setBlock(xCoord, yCoord, zCoord, 0);
                        worldObj.markTileEntityForDespawn(this);
                    }
                    
                }
                send = true;
            }
            
            // Steam production
            if (temperature >= 100) {
                if (getWater() > 40) {
                    if (steam.getFluidAmount() + 40 <= steam.getCapacity()) {
                        water.drain(40, true);
                        steam.fill(new FluidStack(FluidManager.STEAM, 40), true);
                        send = true;
                    }
                }
            }
            
            // Auto-output steam
            if (getSteam() > 0) {
                TileEntity up = new Vector3(this)
                        .getRelative(ForgeDirection.UP).getTileEntity();
                if (up != null) {
                    if (up instanceof IFluidHandler) {
                        IFluidHandler h = (IFluidHandler) up;
                        steam.drain(h.fill(
                                ForgeDirection.DOWN,
                                new FluidStack(FluidManager.STEAM, Math.min(
                                        steam.getFluidAmount(), 50)), true),
                                true);
                    }
                } else {
                    if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord)) {
                        if (getSteam() >= FluidContainerRegistry.BUCKET_VOLUME) {
                            worldObj.setBlock(xCoord, yCoord + 1, zCoord,
                                    FluidManager.STEAM.getBlockID());
                            worldObj.setBlockMetadataWithNotify(xCoord,
                                    yCoord + 1, zCoord, 7, 2);
                            steam.drain(FluidContainerRegistry.BUCKET_VOLUME,
                                    true);
                        }
                    }
                }
            }
            
            // Sync with the player
            if (send) {
                PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());
                send = false;
            }
        } else {
            if (up) {
                tick++;
                if (tick > 34) {
                    tick = 34;
                    up = false;
                }
            } else {
                tick--;
                if (tick < -2) {
                    tick = -2;
                    up = true;
                }
            }
        }
    }
    
    public int getCornerHeight() {
    
        int t = tick;
        if (t < 0) t = 0;
        if (t > 32) t = 32;
        return t;
    }
    
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
    
        if (canFill(from, resource.getFluid())) {
            int inserted = resource.amount;
            if (inserted + water.getFluidAmount() > water.getCapacity()) inserted = water
                    .getCapacity() - water.getFluidAmount();
            
            if (inserted > 0) {
                water.fill(new FluidStack(resource.getFluid(), inserted), true);
                send = true;
            }
            return inserted;
        }
        return 0;
    }
    
    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource,
            boolean doDrain) {
    
        if (from == ForgeDirection.UP && steam.getFluidAmount() > 0
                && resource.isFluidEqual(steam.getFluid())) return steam.drain(
                resource.amount, doDrain);
        return null;
    }
    
    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
    
        if (from == ForgeDirection.UP) return steam.drain(maxDrain, doDrain);
        return null;
    }
    
    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
    
        return !(from == ForgeDirection.UP || from == ForgeDirection.DOWN)
                && fluid == FluidRegistry.WATER;
    }
    
    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
    
        return false;
    }
    
    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
    
        return new FluidTankInfo[] { water.getInfo(), steam.getInfo() };
    }
    
    @Override
    public int getSizeInventory() {
    
        return inventory.length;
    }
    
    @Override
    public ItemStack getStackInSlot(int i) {
    
        return inventory[i];
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
    
        return inventory[i];
    }
    
    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
    
        inventory[i] = itemstack;
    }
    
    @Override
    public String getInvName() {
    
        return ModInfo.MOD_ID + ":gui_boiler";
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
    
    }
    
    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
    
        return TileEntityFurnace.isItemFuel(itemstack);
    }
    
}
