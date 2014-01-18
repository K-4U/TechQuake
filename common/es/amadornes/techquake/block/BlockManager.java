package es.amadornes.techquake.block;

import net.minecraft.block.Block;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import es.amadornes.techquake.block.electric.BlockCable;
import es.amadornes.techquake.block.electric.BlockElectricFurnace;
import es.amadornes.techquake.block.fluid.BlockSteam;
import es.amadornes.techquake.block.powergen.BlockBoiler;
import es.amadornes.techquake.block.powergen.BlockSteamTurbine;
import es.amadornes.techquake.fluid.FluidManager;
import es.amadornes.techquake.fluid.FluidSteam;
import es.amadornes.techquake.lib.id.BlockIds;
import es.amadornes.techquake.multipart.ItemBlockMultiPartCable;
import es.amadornes.techquake.multipart.MultiPartCable;
import es.amadornes.techquake.multipart.RegisterBlockPart;
import es.amadornes.techquake.tileentity.electric.TileEntityCable;
import es.amadornes.techquake.tileentity.electric.machine.TileEntityElectricFurnace;
import es.amadornes.techquake.tileentity.powergen.TileEntityBoiler;
import es.amadornes.techquake.tileentity.powergen.TileEntitySteamTurbine;

/**
 * TechQuake
 * 
 * BlockManager.java
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public class BlockManager {
    
    // public static Block UNLOCALIZED_NAME;
    public static Block CABLE;
    
    public static Block BOILER;
    public static Block STEAM_TURBINE;
    
    public static Block FURNACE;
    
    public static Block STEAM;
    
    public static void init() {
    
        // UNLOCALIZED_NAME = new BlockClass(BlockIds.UNLOCALIZED_NAME);
        // GameRegistry.registerBlock(UNLOCALIZED_NAME,
        // BLOCK_UNLOCALIZED_NAME.getUnlocalizedName());
        // IF NEEDED - GameRegistry.registerTileEntity(TileEntity.class,
        // BLOCK_UNLOCALIZED_NAME.getUnlocalizedName());
        
        CABLE = new BlockCable(BlockIds.CABLE);
        if (Loader.isModLoaded("ForgeMultipart")) {
            GameRegistry.registerBlock(CABLE, ItemBlockMultiPartCable.class,
                    CABLE.getUnlocalizedName());
        } else {
            GameRegistry.registerBlock(CABLE, CABLE.getUnlocalizedName());
        }
        GameRegistry.registerTileEntity(TileEntityCable.class,
                CABLE.getUnlocalizedName());
        
        FURNACE = new BlockElectricFurnace(BlockIds.FURNACE);
        GameRegistry.registerBlock(FURNACE, FURNACE.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileEntityElectricFurnace.class,
                FURNACE.getUnlocalizedName());
        
        BOILER = new BlockBoiler(BlockIds.BOILER);
        GameRegistry.registerBlock(BOILER, BOILER.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileEntityBoiler.class,
                BOILER.getUnlocalizedName());
        
        STEAM_TURBINE = new BlockSteamTurbine(BlockIds.STEAM_TURBINE);
        GameRegistry.registerBlock(STEAM_TURBINE,
                STEAM_TURBINE.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileEntitySteamTurbine.class,
                STEAM_TURBINE.getUnlocalizedName());
        
        if (FluidManager.STEAM instanceof FluidSteam) {
            STEAM = new BlockSteam(BlockIds.STEAM);
            GameRegistry.registerBlock(STEAM, STEAM.getUnlocalizedName());
        }
        
    }
    
    public static void registerMultiParts() {
    
        new RegisterBlockPart(CABLE, MultiPartCable.class).init();
        //MultipartGenerator.registerTrait(CABLE.getUnlocalizedName(), new TileEntityCable().getClass().getName(), new TileEntityCable().getClass().getName());
    }
    
}
