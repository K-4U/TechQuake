package es.amadornes.techquake.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import es.amadornes.techquake.block.BlockManager;
import es.amadornes.techquake.lib.ModInfo;


public class CreativeTabsTechQuake {

    public static final CreativeTabs METALS = new CreativeTabs(ModInfo.MOD_ID + ".metals");
    public static final CreativeTabs MACHINES = new CreativeTabs(ModInfo.MOD_ID + ".machines"){
        public ItemStack getIconItemStack() {
            return new ItemStack(BlockManager.CABLE);
        };
    };
    
}
