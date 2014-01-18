package es.amadornes.techquake.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import es.amadornes.techquake.inventory.ContainerBoiler;
import es.amadornes.techquake.inventory.ContainerElectricFurnace;
import es.amadornes.techquake.tileentity.electric.machine.TileEntityElectricFurnace;
import es.amadornes.techquake.tileentity.powergen.TileEntityBoiler;

public class GuiHandler implements IGuiHandler {
    
    // returns an instance of the Container you made earlier
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world,
            int x, int y, int z) {
    
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (id == 0) {// Boiler gui
            if (tileEntity != null && tileEntity instanceof TileEntityBoiler) { return new ContainerBoiler(
                    player.inventory, (TileEntityBoiler) tileEntity); }
        }
        if (id == 1) {// Electric furnace gui
            if (tileEntity != null
                    && tileEntity instanceof TileEntityElectricFurnace) { return new ContainerElectricFurnace(
                    player.inventory, (TileEntityElectricFurnace) tileEntity); }
        }
        return null;
    }
    
    // returns an instance of the Gui you made earlier
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world,
            int x, int y, int z) {
    
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (id == 0) {
            if (tileEntity != null && tileEntity instanceof TileEntityBoiler) { return new GuiBoiler(
                    new ContainerBoiler(player.inventory,
                            (TileEntityBoiler) tileEntity)); }
        }
        if (id == 1) {
            if (tileEntity != null
                    && tileEntity instanceof TileEntityElectricFurnace) { return new GuiElectricFurnace(
                    new ContainerElectricFurnace(player.inventory,
                            (TileEntityElectricFurnace) tileEntity), (TileEntityElectricFurnace) tileEntity); }
        }
        return null;
        
    }
}
