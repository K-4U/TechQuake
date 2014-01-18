package es.amadornes.techquake.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import es.amadornes.techquake.lib.id.BlockIds;
import es.amadornes.techquake.lib.id.RenderIds;
import es.amadornes.techquake.render.RenderBoiler;
import es.amadornes.techquake.render.RenderCable;
import es.amadornes.techquake.render.RenderSteamTurbine;
import es.amadornes.techquake.tileentity.electric.TileEntityCable;
import es.amadornes.techquake.tileentity.powergen.TileEntityBoiler;
import es.amadornes.techquake.tileentity.powergen.TileEntitySteamTurbine;

/**
 * TechQuake
 * 
 * ClientProxy.java
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public class ClientProxy extends CommonProxy {
    
    @Override
    public void registerRenders() {
        RenderIds.CABLE = RenderingRegistry.getNextAvailableRenderId();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCable.class, new RenderCable());
        MinecraftForgeClient.registerItemRenderer(BlockIds.CABLE, new RenderCable());

        
        
        RenderIds.BOILER = RenderingRegistry.getNextAvailableRenderId();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBoiler.class, new RenderBoiler());
        MinecraftForgeClient.registerItemRenderer(BlockIds.BOILER, new RenderBoiler());

        RenderIds.STEAM_TURBINE = RenderingRegistry.getNextAvailableRenderId();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySteamTurbine.class, new RenderSteamTurbine());
        MinecraftForgeClient.registerItemRenderer(BlockIds.STEAM_TURBINE, new RenderSteamTurbine());
    }
    
}
