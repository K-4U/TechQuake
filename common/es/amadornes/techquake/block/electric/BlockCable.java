package es.amadornes.techquake.block.electric;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import codechicken.lib.raytracer.ExtendedMOP;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import es.amadornes.techquake.api.block.electric.BlockElectric;
import es.amadornes.techquake.api.electricity.IElectric;
import es.amadornes.techquake.creativetab.CreativeTabsTechQuake;
import es.amadornes.techquake.lib.ModInfo;
import es.amadornes.techquake.lib.id.RenderIds;
import es.amadornes.techquake.tileentity.electric.TileEntityCable;

/**
 * TechQuake
 * 
 * BlockCable.java
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public class BlockCable extends BlockElectric {
    
    public BlockCable(int id) {
    
        super(id, Material.piston);
        setUnlocalizedName(ModInfo.MOD_ID + ".cable");
        setCreativeTab(CreativeTabsTechQuake.MACHINES);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
    
        return new TileEntityCable();
    }
    
    @Override
    public int getRenderType() {
    
        return RenderIds.CABLE;
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(World w, int x, int y, int z,
            Vec3 start, Vec3 end) {
    
        MovingObjectPosition mop = super.collisionRayTrace(w, x, y, z, start,
                end);
        if (mop != null) return new ExtendedMOP(mop, null,
                start.distanceTo(end));
        return null;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    
        return false;
    }
    
    @Override
    public boolean isBlockSolid(IBlockAccess par1iBlockAccess, int par2,
            int par3, int par4, int par5) {
    
        return false;
    }
    
    public boolean isOpaqueCube() {
    
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y,
            int z) {
    
        AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(x + (0.0625 * 6), y
                + (0.0625 * 6), z + (0.0625 * 6), (x + 1) - (0.0625 * 6),
                (y + 1) - (0.0625 * 6), (z + 1) - (0.0625 * 6));
        TileEntity te = w.getBlockTileEntity(x, y, z);
        if (te != null && te instanceof IElectric) {
            IElectric e = (IElectric) te;
            boolean renderUp = e.canConnectOnSide(ForgeDirection.UP);
            boolean renderDown = e.canConnectOnSide(ForgeDirection.DOWN);
            boolean renderNorth = e.canConnectOnSide(ForgeDirection.NORTH);
            boolean renderEast = e.canConnectOnSide(ForgeDirection.EAST);
            boolean renderSouth = e.canConnectOnSide(ForgeDirection.SOUTH);
            boolean renderWest = e.canConnectOnSide(ForgeDirection.WEST);
            if (renderUp) bb.maxY = y + 1;
            if (renderDown) bb.minY = y;
            if (renderSouth) bb.maxZ = z + 1;
            if (renderNorth) bb.minZ = z;
            if (renderEast) bb.maxX = x + 1;
            if (renderWest) bb.minX = x;
        }
        return bb;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y,
            int z) {
    
        AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(x + (0.0625 * 6), y
                + (0.0625 * 6), z + (0.0625 * 6), (x + 1) - (0.0625 * 6),
                (y + 1) - (0.0625 * 6), (z + 1) - (0.0625 * 6));
        TileEntity te = w.getBlockTileEntity(x, y, z);
        if (te != null && te instanceof IElectric) {
            IElectric e = (IElectric) te;
            boolean renderUp = e.canConnectOnSide(ForgeDirection.UP);
            boolean renderDown = e.canConnectOnSide(ForgeDirection.DOWN);
            boolean renderNorth = e.canConnectOnSide(ForgeDirection.NORTH);
            boolean renderEast = e.canConnectOnSide(ForgeDirection.EAST);
            boolean renderSouth = e.canConnectOnSide(ForgeDirection.SOUTH);
            boolean renderWest = e.canConnectOnSide(ForgeDirection.WEST);
            if (renderUp) bb.maxY = y + 1;
            if (renderDown) bb.minY = y;
            if (renderSouth) bb.maxZ = z + 1;
            if (renderNorth) bb.minZ = z;
            if (renderEast) bb.maxX = x + 1;
            if (renderWest) bb.minX = x;
        }
        return bb;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess w, int x, int y, int z) {
    
        AxisAlignedBB bb = AxisAlignedBB.getBoundingBox((0.0625 * 6),
                (0.0625 * 6), (0.0625 * 6), (1) - (0.0625 * 6),
                (1) - (0.0625 * 6), (1) - (0.0625 * 6));
        TileEntity te = w.getBlockTileEntity(x, y, z);
        if (te != null && te instanceof IElectric) {
            IElectric e = (IElectric) te;
            boolean renderUp = e.canConnectOnSide(ForgeDirection.UP);
            boolean renderDown = e.canConnectOnSide(ForgeDirection.DOWN);
            boolean renderNorth = e.canConnectOnSide(ForgeDirection.NORTH);
            boolean renderEast = e.canConnectOnSide(ForgeDirection.EAST);
            boolean renderSouth = e.canConnectOnSide(ForgeDirection.SOUTH);
            boolean renderWest = e.canConnectOnSide(ForgeDirection.WEST);
            if (renderUp) bb.maxY = 1;
            if (renderDown) bb.minY = 0;
            if (renderSouth) bb.maxZ = 1;
            if (renderNorth) bb.minZ = 0;
            if (renderEast) bb.maxX = 1;
            if (renderWest) bb.minX = 0;
        }
        setBlockBounds((float) bb.minX, (float) bb.minY, (float) bb.minZ,
                (float) bb.maxX, (float) bb.maxY, (float) bb.maxZ);
    }
}
