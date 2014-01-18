package es.amadornes.techquake.multipart;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.TMultiPart;


public class ItemBlockMultiPartCable extends ItemBlockMultiPart {

    public ItemBlockMultiPartCable(int id) {
    
        super(id);
    }
    
    public TMultiPart createMultiPart(World world, BlockCoord pos, ItemStack item, int side)
    {
      return new MultiPartCable();
    }
    
}
