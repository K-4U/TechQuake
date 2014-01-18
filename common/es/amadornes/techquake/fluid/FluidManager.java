package es.amadornes.techquake.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidManager {

	public static Fluid STEAM;
	
	public static void init(){
        
        STEAM = new FluidSteam();
        if(!FluidRegistry.isFluidRegistered("steam")){
            FluidRegistry.registerFluid(STEAM);
        }else{
            STEAM = FluidRegistry.getFluid("steam");
        }
        
    }
	
}
