package es.amadornes.techquake.block.electric;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import es.amadornes.techquake.TechQuake;
import es.amadornes.techquake.api.block.electric.BlockElectric;
import es.amadornes.techquake.creativetab.CreativeTabsTechQuake;
import es.amadornes.techquake.lib.ModInfo;
import es.amadornes.techquake.lib.id.RenderIds;
import es.amadornes.techquake.tileentity.electric.machine.TileEntityElectricFurnace;

public class BlockElectricFurnace extends BlockElectric {
    
    private final Random   furnaceRand = new Random();
    
    private static boolean keepFurnaceInventory;
    @SideOnly(Side.CLIENT)
    private Icon           furnaceIconTop;
    @SideOnly(Side.CLIENT)
    private Icon           furnaceIconBottom;
    @SideOnly(Side.CLIENT)
    private Icon           furnaceIconOn;
    @SideOnly(Side.CLIENT)
    private Icon           furnaceIconOff;
    
    public BlockElectricFurnace(int id) {
    
        super(id, Material.rock);
        setUnlocalizedName(ModInfo.MOD_ID + ".electricfurnace");
        setCreativeTab(CreativeTabsTechQuake.MACHINES);
    }
    
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
    
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setDefaultDirection(par1World, par2, par3, par4);
    }
    
    private void setDefaultDirection(World par1World, int par2, int par3,
            int par4) {
    
        if (!par1World.isRemote) {
            int l = par1World.getBlockId(par2, par3, par4 - 1);
            int i1 = par1World.getBlockId(par2, par3, par4 + 1);
            int j1 = par1World.getBlockId(par2 - 1, par3, par4);
            int k1 = par1World.getBlockId(par2 + 1, par3, par4);
            byte b0 = 3;
            
            if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1]) {
                b0 = 3;
            }
            
            if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l]) {
                b0 = 2;
            }
            
            if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1]) {
                b0 = 5;
            }
            
            if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1]) {
                b0 = 4;
            }
            
            par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2) {
    
        return par1 == 1 ? this.furnaceIconTop
                : (par1 == 0 ? this.furnaceIconBottom
                        : (par1 != 3 ? this.blockIcon : this.furnaceIconOff));
    }
    
    @Override
    public Icon getBlockTexture(IBlockAccess w, int x, int y, int z, int side) {
    
        TileEntityElectricFurnace te = (TileEntityElectricFurnace) w
                .getBlockTileEntity(x, y, z);
        
        return side == 1 ? this.furnaceIconTop
                : (side == 0 ? this.furnaceIconBottom : (side != w
                        .getBlockMetadata(x, y, z) ? this.blockIcon
                        : (te.isBurning ? furnaceIconOn : furnaceIconOff)));
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
    
        this.blockIcon = par1IconRegister.registerIcon(ModInfo.MOD_ID
                + ":machine/machine_side");
        this.furnaceIconOn = par1IconRegister.registerIcon(ModInfo.MOD_ID
                + ":machine/furnace/furnace_front_on");
        this.furnaceIconOff = par1IconRegister.registerIcon(ModInfo.MOD_ID
                + ":machine/furnace/furnace_front_off");
        this.furnaceIconTop = par1IconRegister.registerIcon(ModInfo.MOD_ID
                + ":machine/machine_top");
        this.furnaceIconBottom = par1IconRegister.registerIcon(ModInfo.MOD_ID
                + ":machine/machine_bottom");
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int par6, float par7, float par8, float par9) {
    
        if (world.isRemote) {
            return true;
        } else {
            TileEntityElectricFurnace TileEntityElectricFurnace = (TileEntityElectricFurnace) world
                    .getBlockTileEntity(x, y, z);
            
            if (TileEntityElectricFurnace != null) {
                FMLNetworkHandler.openGui(player, TechQuake.instance, 1, world,
                        x, y, z);
            }
            
            return true;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z,
            Random par5Random) {
    
        if (((TileEntityElectricFurnace) world.getBlockTileEntity(x, y, z)).isBurning) {
            int l = world.getBlockMetadata(x, y, z);
            float f = (float) x + 0.5F;
            float f1 = (float) y + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
            float f2 = (float) z + 0.5F;
            float f3 = 0.52F;
            float f4 = par5Random.nextFloat() * 0.6F - 0.3F;
            
            if (l == 4) {
                world.spawnParticle("smoke", (double) (f - f3), (double) f1,
                        (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double) (f - f3), (double) f1,
                        (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
            } else if (l == 5) {
                world.spawnParticle("smoke", (double) (f + f3), (double) f1,
                        (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double) (f + f3), (double) f1,
                        (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
            } else if (l == 2) {
                world.spawnParticle("smoke", (double) (f + f4), (double) f1,
                        (double) (f2 - f3), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double) (f + f4), (double) f1,
                        (double) (f2 - f3), 0.0D, 0.0D, 0.0D);
            } else if (l == 3) {
                world.spawnParticle("smoke", (double) (f + f4), (double) f1,
                        (double) (f2 + f3), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double) (f + f4), (double) f1,
                        (double) (f2 + f3), 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    public TileEntity createNewTileEntity(World par1World) {
    
        return new TileEntityElectricFurnace();
    }
    
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
            EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
    
        int l = MathHelper
                .floor_double((double) (par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        
        if (l == 0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
        }
        
        if (l == 1) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
        }
        
        if (l == 2) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
        }
        
        if (l == 3) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
        }
    }
    
    public void breakBlock(World par1World, int par2, int par3, int par4,
            int par5, int par6) {
    
        if (!keepFurnaceInventory) {
            TileEntityElectricFurnace TileEntityElectricFurnace = (TileEntityElectricFurnace) par1World
                    .getBlockTileEntity(par2, par3, par4);
            
            if (TileEntityElectricFurnace != null) {
                for (int j1 = 0; j1 < TileEntityElectricFurnace
                        .getSizeInventory(); ++j1) {
                    ItemStack itemstack = TileEntityElectricFurnace
                            .getStackInSlot(j1);
                    
                    if (itemstack != null) {
                        float f = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        
                        while (itemstack.stackSize > 0) {
                            int k1 = this.furnaceRand.nextInt(21) + 10;
                            
                            if (k1 > itemstack.stackSize) {
                                k1 = itemstack.stackSize;
                            }
                            
                            itemstack.stackSize -= k1;
                            EntityItem entityitem = new EntityItem(par1World,
                                    (double) ((float) par2 + f),
                                    (double) ((float) par3 + f1),
                                    (double) ((float) par4 + f2),
                                    new ItemStack(itemstack.itemID, k1,
                                            itemstack.getItemDamage()));
                            
                            if (itemstack.hasTagCompound()) {
                                entityitem.getEntityItem().setTagCompound(
                                        (NBTTagCompound) itemstack
                                                .getTagCompound().copy());
                            }
                            
                            float f3 = 0.05F;
                            entityitem.motionX = (double) ((float) this.furnaceRand
                                    .nextGaussian() * f3);
                            entityitem.motionY = (double) ((float) this.furnaceRand
                                    .nextGaussian() * f3 + 0.2F);
                            entityitem.motionZ = (double) ((float) this.furnaceRand
                                    .nextGaussian() * f3);
                            par1World.spawnEntityInWorld(entityitem);
                        }
                    }
                }
                
                par1World.func_96440_m(par2, par3, par4, par5);
            }
        }
        
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    public boolean hasComparatorInputOverride() {
    
        return true;
    }
    
    public int getComparatorInputOverride(World par1World, int par2, int par3,
            int par4, int par5) {
    
        return Container.calcRedstoneFromInventory((IInventory) par1World
                .getBlockTileEntity(par2, par3, par4));
    }
    
    @Override
    public int getRenderType() {
    
        return RenderIds.FURNACE;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    
        return false;
    }
    
    public boolean isOpaqueCube() {
    
        return false;
    }
    
    @Override
    public boolean isBlockSolid(IBlockAccess par1iBlockAccess, int par2,
            int par3, int par4, int par5) {
    
        return false;
    }
    
}
