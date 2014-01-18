package es.amadornes.techquake.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import es.amadornes.techquake.inventory.slot.SlotOutput;
import es.amadornes.techquake.inventory.slot.SlotSmeltable;
import es.amadornes.techquake.tileentity.electric.machine.TileEntityElectricFurnace;

public class ContainerElectricFurnace extends Container {
    
    public TileEntityElectricFurnace te;
    
    public ContainerElectricFurnace(InventoryPlayer inv,
            TileEntityElectricFurnace tile) {
    
        te = tile;
        te.opened++;
        
        this.addSlotToContainer(new SlotSmeltable(te, 0, 32, 34));
        this.addSlotToContainer(new SlotSmeltable(te, 1, 54, 34));
        
        this.addSlotToContainer(new SlotOutput(te, 2, 111, 34));
        this.addSlotToContainer(new SlotOutput(te, 3, 134, 34));
        
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inv, j + i * 9 + 9,
                        8 + j * 18, 84 + i * 18));
            }
        }
        
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inv, i, 8 + i * 18, 142));
        }
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player) {
    
        return te.isUseableByPlayer(player);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int i) {
    
        return null;
    }
    
    @Override
    public boolean mergeItemStack(ItemStack stack, int begin, int end,
            boolean backwards) {
    
        int i = backwards ? end - 1 : begin, increment = backwards ? -1 : 1;
        boolean flag = false;
        while (stack.stackSize > 0 && i >= begin && i < end) {
            Slot slot = this.getSlot(i);
            ItemStack slotStack = slot.getStack();
            int slotStacklimit = i < te.getSizeInventory() ? te
                    .getInventoryStackLimit() : 64;
            int totalLimit = slotStacklimit < stack.getMaxStackSize() ? slotStacklimit
                    : stack.getMaxStackSize();
            
            if (slotStack == null) {
                int transfer = totalLimit < stack.stackSize ? totalLimit
                        : stack.stackSize;
                ItemStack stackToPut = stack.copy();
                stackToPut.stackSize = transfer;
                slot.putStack(stackToPut);
                slot.onSlotChanged();
                stack.stackSize -= transfer;
                flag = true;
            } else if (slotStack.itemID == stack.itemID
                    && (!stack.getHasSubtypes() || stack.getItemDamage() == slotStack
                            .getItemDamage())
                    && ItemStack.areItemStackTagsEqual(stack, slotStack)) {
                int maxTransfer = totalLimit - slotStack.stackSize;
                int transfer = maxTransfer > stack.stackSize ? stack.stackSize
                        : maxTransfer;
                slotStack.stackSize += transfer;
                slot.onSlotChanged();
                stack.stackSize -= transfer;
                flag = true;
            }
            
            i += increment;
        }
        
        return flag;
        
    }
    
    private int buffer = 0;
    private int progress = 0;
    
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
    
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.te.getStored());
        par1ICrafting.sendProgressBarUpdate(this, 1, (int) this.te.progress);
    }
    
    public void detectAndSendChanges() {
    
        super.detectAndSendChanges();
        
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);
            
            if (this.buffer != this.te.getStored()) {
                icrafting.sendProgressBarUpdate(this, 0, this.te.getStored());
            }
            
            if (this.progress != (int) this.te.progress) {
                icrafting.sendProgressBarUpdate(this, 1, (int) this.te.progress);
            }
        }
        
        this.buffer = this.te.getStored();
        this.progress = (int) this.te.progress;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
    
        if (par1 == 0) {
            this.te.setBuffer(par2);
        }
    
        if (par1 == 1) {
            this.te.progress = par2;
        }
    }
    
    @Override
    public void setPlayerIsPresent(EntityPlayer par1EntityPlayer, boolean par2) {
        if(par2){
            te.opened++;
        }else{
            te.opened--;
        }
        System.out.println("Player is present: " + par1EntityPlayer.username + " " + par2);
    }
    
}
