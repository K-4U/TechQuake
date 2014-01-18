package es.amadornes.techquake.block.powergen;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import es.amadornes.techquake.api.block.electric.BlockElectric;
import es.amadornes.techquake.creativetab.CreativeTabsTechQuake;
import es.amadornes.techquake.lib.ModInfo;
import es.amadornes.techquake.lib.id.RenderIds;
import es.amadornes.techquake.tileentity.powergen.TileEntitySteamTurbine;

public class BlockSteamTurbine extends BlockElectric {
    
    public BlockSteamTurbine(int id) {
    
        super(id, Material.rock);
        setUnlocalizedName(ModInfo.MOD_ID + ".steamturbine");
        setCreativeTab(CreativeTabsTechQuake.MACHINES);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world) {
    
        return new TileEntitySteamTurbine();
    }
    
    @Override
    public int getRenderType() {
    
        return RenderIds.STEAM_TURBINE;
    }
    
    public boolean renderAsNormalBlock() {
    
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
    
        return false;
    }
    
    @Override
    public boolean isBlockSolid(IBlockAccess par1iBlockAccess, int par2,
            int par3, int par4, int par5) {
    
        return true;
    }
    
    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, World world, int x,
            int y, int z) {
    
        return false;
    }
    
    @Override
    public int onBlockPlaced(World w, int x, int y, int z, int side,
            float hitX, float hitY, float hitZ, int meta) {
    
        return side;
    }
    
}
