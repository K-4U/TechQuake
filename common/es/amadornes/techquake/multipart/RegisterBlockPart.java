package es.amadornes.techquake.multipart;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;

public class RegisterBlockPart implements MultiPartRegistry.IPartFactory,
        MultiPartRegistry.IPartConverter {
    
    Block                       block = null;
    Class<? extends TMultiPart> part  = null;
    String                      name  = "";
    
    public RegisterBlockPart(Block block, Class<? extends TMultiPart> part) {
    
        this(block, part, block.getUnlocalizedName());
    }
    
    public RegisterBlockPart(Block block, Class<? extends TMultiPart> part,
            String name) {
    
        this.block = block;
        this.part = part;
        this.name = name;
    }
    
    public TMultiPart createPart(String name, boolean client) {
    
        if (name.equals(name)) try {
            return (TMultiPart) this.part.getConstructor(new Class[0])
                    .newInstance(new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    
    public void init() {
    
        if ((this.name == "") || (this.block == null) || (this.part == null)) return;
        MultiPartRegistry.registerConverter(this);
        MultiPartRegistry.registerParts(this, new String[] { this.name });
    }
    
    public boolean canConvert(int blockID) {
    
        return blockID == this.block.blockID;
    }
    
    public TMultiPart convert(World world, BlockCoord pos) {
    
        int id = world.getBlockId(pos.x, pos.y, pos.z);
        if (id == this.block.blockID) {
            try {
                return (TMultiPart) this.part.getConstructor(new Class[0])
                        .newInstance(new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        
        return null;
    }
    
}
