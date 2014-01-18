package es.amadornes.techquake.inventory.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import es.amadornes.techquake.tileentity.electric.machine.TileEntityElectricFurnace;

public class SlotSmeltable extends Slot {
    
    public SlotSmeltable(IInventory par1iInventory, int par2, int par3, int par4) {
    
        super(par1iInventory, par2, par3, par4);
    }
    
    @Override
    public boolean isItemValid(ItemStack item) {
    
        return TileEntityElectricFurnace.canItemBeSmelted(item);
    }
    
    @Override
    public boolean canTakeStack(EntityPlayer player) {
    
        return true;
    }
    
}
