package es.amadornes.techquake.multipart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.ForgeDirection;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.vec.Cuboid6;
import codechicken.microblock.IHollowConnect;
import codechicken.multipart.minecraft.McMetaPart;
import es.amadornes.techquake.api.electricity.IElectric;
import es.amadornes.techquake.block.BlockManager;
import es.amadornes.techquake.tileentity.electric.TileEntityCable;

public class MultiPartCable extends McMetaPart implements IHollowConnect {
    
    @Override
    public Cuboid6 getBounds() {
    
        return new Cuboid6(AxisAlignedBB.getBoundingBox(0.0625 * 6, 0.0625 * 6,
                0.0625 * 6, 1 - (0.0625 * 6), 1 - (0.0625 * 6),
                1 - (0.0625 * 6)));
    }
    
    @Override
    public Block getBlock() {
    
        return BlockManager.CABLE;
    }
    
    @Override
    public String getType() {
    
        return BlockManager.CABLE.getUnlocalizedName();
    }
    
    @Override
    public TileEntity getTile() {
    
        TileEntity te = world().getBlockTileEntity(x(), y(), z());
        
        return te != null ? te : new TileEntityCable();
    }
    
    @Override
    public int getHollowSize() {
    
        return 5;
    }
    
    public Iterable<Cuboid6> getCollisionBoxes() {
    
        ArrayList<Cuboid6> t2 = new ArrayList<Cuboid6>();
        
        AxisAlignedBB bb = AxisAlignedBB.getBoundingBox((0.0625 * 6),
                (0.0625 * 6), (0.0625 * 6), (1) - (0.0625 * 6),
                (1) - (0.0625 * 6), (1) - (0.0625 * 6));
        
        TileEntity te = world().getBlockTileEntity(x(), y(), z());
        
        if (te != null && te instanceof IElectric) {
            IElectric e = (IElectric) te;
            boolean renderUp = e.canConnectOnSide(ForgeDirection.UP);
            boolean renderDown = e.canConnectOnSide(ForgeDirection.DOWN);
            boolean renderNorth = e.canConnectOnSide(ForgeDirection.NORTH);
            boolean renderEast = e.canConnectOnSide(ForgeDirection.EAST);
            boolean renderSouth = e.canConnectOnSide(ForgeDirection.SOUTH);
            boolean renderWest = e.canConnectOnSide(ForgeDirection.WEST);
            
            if (renderUp) {
                AxisAlignedBB aabb = bb.copy();
                aabb.maxY = 1;
                t2.add(new Cuboid6(aabb));
            }
            if (renderDown) {
                AxisAlignedBB aabb = bb.copy();
                aabb.minY = 0;
                t2.add(new Cuboid6(aabb));
            }
            if (renderSouth) {
                AxisAlignedBB aabb = bb.copy();
                aabb.maxZ = 1;
                t2.add(new Cuboid6(aabb));
            }
            if (renderNorth) {
                AxisAlignedBB aabb = bb.copy();
                aabb.minZ = 0;
                t2.add(new Cuboid6(aabb));
            }
            if (renderEast) {
                AxisAlignedBB aabb = bb.copy();
                aabb.maxX = 1;
                t2.add(new Cuboid6(aabb));
            }
            if (renderWest) {
                AxisAlignedBB aabb = bb.copy();
                aabb.minX = 0;
                t2.add(new Cuboid6(aabb));
            }
        }
        
        return t2;
    }
    
    public Iterable<ItemStack> getDrops() {
    
        return Arrays
                .asList(new ItemStack[] { new ItemStack(getBlock(), 1, 0) });
    }
    
    public ItemStack pickItem(MovingObjectPosition hit) {
    
        return new ItemStack(getBlock(), 1, 0);
    }
    
    public Iterable<Cuboid6> getOcclusionBoxes() {
    
        return getCollisionBoxes();
    }
    
    @Override
    public Iterable<IndexedCuboid6> getSubParts() {
        
        List<IndexedCuboid6> parts = new ArrayList<IndexedCuboid6>();
        
        for(Cuboid6 c : getCollisionBoxes()){
            parts.add((IndexedCuboid6) c);
        }
    
        return parts;
    }
    
}
