package es.amadornes.techquake.proxy;

import java.io.File;

import net.minecraftforge.common.Configuration;
import es.amadornes.techquake.lib.ModInfo;
import es.amadornes.techquake.lib.id.BlockIds;

/**
 * TechQuake
 * 
 * CommonProxy.java
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public class CommonProxy {
    
    Configuration config;
    
    public void loadConfig() {
    
        config = new Configuration(new File("config/" + ModInfo.MOD_ID + ".cfg"));
        config.load();
        
        // BlockIds.UNLOCALIZED_NAME = config.get("blocks", "UNLOCALIZED_NAME", BlockIds.UNLOCALIZED_NAME_DEFAULTID).getInt();
        // ItemIds.UNLOCALIZED_NAME = config.get("items", "UNLOCALIZED_NAME", ItemIds.UNLOCALIZED_NAME_DEFAULTID).getInt();

        BlockIds.CABLE = config.get("blocks", "cable", BlockIds.CABLE).getInt();
        
        BlockIds.BOILER = config.get("blocks", "boiler", BlockIds.BOILER).getInt();
        
        
        BlockIds.STEAM = config.get("fluids", "steam", BlockIds.STEAM).getInt();
        
        config.save();
        
    }
    
    public void registerRenders(){}
    
}
