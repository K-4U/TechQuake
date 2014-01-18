package es.amadornes.techquake;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import es.amadornes.techquake.block.BlockManager;
import es.amadornes.techquake.fluid.FluidManager;
import es.amadornes.techquake.gui.GuiHandler;
import es.amadornes.techquake.item.ItemManager;
import es.amadornes.techquake.lib.LocalizationHandler;
import es.amadornes.techquake.lib.ModInfo;
import es.amadornes.techquake.packet.PacketHandler;
import es.amadornes.techquake.proxy.CommonProxy;

/**
 * TechQuake
 * 
 * TechQuake.java
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 */

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.NAME, version = ModInfo.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { ModInfo.CHANNEL_NAME }, packetHandler = PacketHandler.class)
public class TechQuake {
    
    @Instance(ModInfo.MOD_ID)
    public static TechQuake   instance;
    
    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.SERVER_PROXY)
    public static CommonProxy proxy;
    
    public static boolean enableCompatFMP = false;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    
        /* Load Mod config */
        proxy.loadConfig();
        
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        
        /* Register fluids */
        FluidManager.init();
        
        /* Register blocks */
        BlockManager.init();
        
        /* Register multiparts */
        if(enableCompatFMP)
            if(Loader.isModLoaded("ForgeMultipart"))
                BlockManager.registerMultiParts();
        
        /* Register items */
        ItemManager.init();
        
        /* Register localizations */
        LocalizationHandler.loadLanguages();
        
        /* Register renders */
        proxy.registerRenders();
        
        /* If on a client, register GUI handler */
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
        
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
    
    }
    
}
