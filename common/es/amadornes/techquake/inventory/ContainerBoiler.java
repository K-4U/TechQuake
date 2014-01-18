package es.amadornes.techquake.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import es.amadornes.techquake.inventory.slot.SlotFuel;
import es.amadornes.techquake.tileentity.powergen.TileEntityBoiler;

public class ContainerBoiler extends Container {
    
    public TileEntityBoiler te;
    
    public ContainerBoiler(InventoryPlayer inv, TileEntityBoiler tile) {
    
        te = tile;
        
        this.addSlotToContainer(new SlotFuel(te, 0, 70, 60));
        this.addSlotToContainer(new SlotFuel(te, 1, 88, 60));
        
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
    
}
