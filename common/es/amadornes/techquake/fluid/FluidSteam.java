package es.amadornes.techquake.fluid;

import net.minecraftforge.fluids.Fluid;

public class FluidSteam extends Fluid {
	public FluidSteam() {
		super("steam");
		setGaseous(true);
		setDensity(-5000);
		setViscosity(1000);
	}
}