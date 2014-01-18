package es.amadornes.techquake.block.fluid;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidFinite;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import es.amadornes.techquake.fluid.FluidManager;
import es.amadornes.techquake.lib.ModInfo;
import es.amadornes.techquake.lib.Materials;

public class BlockSteam extends BlockFluidFinite {
    
    public static Icon icon_still;
    public static Icon icon_flowing;
    
    public BlockSteam(int id) {
        super(id, FluidManager.STEAM, Materials.STEAM);
        setUnlocalizedName(ModInfo.MOD_ID + ".steam");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister reg) {
    
        icon_still = reg.registerIcon(ModInfo.MOD_ID + ":steam/steam_still");
        icon_flowing = reg
                .registerIcon(ModInfo.MOD_ID + ":steam/steam_flowing");
        FluidManager.STEAM.setIcons(icon_still, icon_flowing);
        this.blockIcon = icon_still;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess par1iBlockAccess, int par2,
            int par3, int par4) {
    
        return 0xCCCCCC;
    }
    
    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
            int par4, Entity entity) {
    
        if (entity instanceof EntityLiving) {
            EntityLiving l = (EntityLiving) entity;
            l.addPotionEffect(new PotionEffect(Potion.blindness.id, 20, 9));
            l.addPotionEffect(new PotionEffect(Potion.poison.id, 20, 14));
        } else if (entity instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) entity;
            p.addPotionEffect(new PotionEffect(Potion.blindness.id, 20, 9));
            p.addPotionEffect(new PotionEffect(Potion.poison.id, 20, 14));
        }
    }
    
}
