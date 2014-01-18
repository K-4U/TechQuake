package es.amadornes.techquake.api.block.electric;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import es.amadornes.techquake.api.block.TQBlockContainer;
import es.amadornes.techquake.api.electricity.EnergyNetwork;
import es.amadornes.techquake.api.electricity.IElectric;

/**
 * TechQuake
 * 
 * BlockElectric.java
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public abstract class BlockElectric extends TQBlockContainer {

    public BlockElectric(int id, Material mat){
        super(id, mat);
    }
    
    @Override
    public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
        IElectric e = (IElectric) world.getBlockTileEntity(x, y, z);
        EnergyNetwork net = e.getEnergyNetwork();
        if(net != null)
            net.remove(e);
    }
    
}
