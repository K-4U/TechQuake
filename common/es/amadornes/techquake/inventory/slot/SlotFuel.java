package es.amadornes.techquake.inventory.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotFuel extends Slot {

	public SlotFuel(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack item) {
		return TileEntityFurnace.isItemFuel(item);
	}
    
    @Override
    public boolean canTakeStack(EntityPlayer player) {
    
        return true;
    }

}
