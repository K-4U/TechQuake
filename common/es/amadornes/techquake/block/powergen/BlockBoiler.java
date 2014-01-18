package es.amadornes.techquake.block.powergen;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import es.amadornes.techquake.TechQuake;
import es.amadornes.techquake.creativetab.CreativeTabsTechQuake;
import es.amadornes.techquake.lib.ModInfo;
import es.amadornes.techquake.lib.id.RenderIds;
import es.amadornes.techquake.tileentity.powergen.TileEntityBoiler;

public class BlockBoiler extends BlockContainer {
    
    public BlockBoiler(int id) {
    
        super(id, Material.rock);
        setUnlocalizedName(ModInfo.MOD_ID + ".boiler");
        setCreativeTab(CreativeTabsTechQuake.MACHINES);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world) {
    
        return new TileEntityBoiler();
    }
    
    @Override
    public int getRenderType() {
    
        return RenderIds.BOILER;
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
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister i) {
    
        blockIcon = i.registerIcon(ModInfo.MOD_ID + ":boiler.png");
    }
    
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3,
            int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
            float par8, float par9) {
    
        if (!par1World.isRemote) {
            FMLNetworkHandler.openGui(par5EntityPlayer, TechQuake.instance, 0,
                    par1World, par2, par3, par4);
        }
        return true;
    }
    
}
