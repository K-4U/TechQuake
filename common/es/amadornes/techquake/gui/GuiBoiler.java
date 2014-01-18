package es.amadornes.techquake.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import es.amadornes.techquake.fluid.FluidManager;
import es.amadornes.techquake.inventory.ContainerBoiler;
import es.amadornes.techquake.lib.ModInfo;
import es.amadornes.techquake.tileentity.powergen.TileEntityBoiler;

public class GuiBoiler extends GuiContainer{
	
	ResourceLocation bg = new ResourceLocation(ModInfo.MOD_ID + ":textures/gui/boiler.png");
	
	TileEntityBoiler boiler;
	
	public GuiBoiler(ContainerBoiler par1Container) {
		super(par1Container);
		boiler = par1Container.te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mx, int my) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(bg);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        float leftFuel = boiler.leftTotalBurnTime - boiler.leftBurnTime;
        leftFuel /= boiler.leftTotalBurnTime;
        leftFuel *= 120;
        int lf = (int) leftFuel;
        lf /= 10;
        
        float rightFuel = boiler.rightTotalBurnTime - boiler.rightBurnTime;
        rightFuel /= boiler.rightTotalBurnTime;
        rightFuel *= 120;
        int rf = (int) rightFuel;
        rf /= 10;
        
        if(boiler.leftTotalBurnTime > 0)
        	this.drawTexturedModalRect(k + 70, l + 45 + ((int)(13 - lf)), 176, 0 + ((int)(13 - lf)), 14, (int)lf + 1);
        if(boiler.rightTotalBurnTime > 0)
        	this.drawTexturedModalRect(k + 91, l + 45 + ((int)(13 - rf)), 176, 0 + ((int)(13 - rf)), 14, (int)rf + 1);
        
        {
        	float scaledW = boiler.getWater();
            scaledW /= FluidContainerRegistry.BUCKET_VOLUME * 16;
            scaledW *= 60;
            
            if(scaledW > 0){
            	displayGauge(k, l, 15, 11, (int)scaledW, new FluidStack(FluidRegistry.WATER, 1000));
            }
        }
        
        {
        	float scaledS = boiler.getSteam();
            scaledS /= FluidContainerRegistry.BUCKET_VOLUME * 16;
            scaledS *= 60;
            
            if(scaledS > 0){
            	displayGauge(k, l, 15, 148, (int)scaledS, new FluidStack(FluidManager.STEAM, 1000));
            }
        }
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String title = "Boiler";
		String subtitle = ((int)boiler.temperature) + "ºC";
        this.fontRenderer.drawString(title, this.xSize / 2 - this.fontRenderer.getStringWidth(title) / 2, 6, 4210752);
        float subtitleScale = 0.7F;
        GL11.glScalef(subtitleScale, subtitleScale, subtitleScale);
        this.fontRenderer.drawString(subtitle, 3 + (int)((this.xSize / 2 - this.fontRenderer.getStringWidth(subtitle) / 2) * (1/subtitleScale)), (int)((6 + 10) * (1/subtitleScale)), 4210752);
        
        GL11.glScalef(1F/subtitleScale, 1F/subtitleScale, 1F/subtitleScale);
        
        //11, 13
        //27, 72
	}
	
	@Override
	protected void mouseClicked(int mx, int my, int btn) {
		super.mouseClicked(mx, my, btn);
	}
	
	//UTILS
	
	private void displayGauge(int j, int k, int line, int col, int squaled, FluidStack liquid) {
		if (liquid == null) {
			return;
		}
		int start = 0;

		Icon liquidIcon = null;
		Fluid fluid = liquid.getFluid();
		if (fluid != null && fluid.getStillIcon() != null) {
			liquidIcon = fluid.getStillIcon();
		}
		
		mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);

		if (liquidIcon != null) {
			while (true) {
				int x;

				if (squaled > 16) {
					x = 16;
					squaled -= 16;
				} else {
					x = squaled;
					squaled = 0;
				}

				drawTexturedModelRectFromIcon(j + col, k + line + 58 - x - start, liquidIcon, 16, 16 - (16 - x));
				start = start + 16;

				if (x == 0 || squaled == 0) {
					break;
				}
			}
		}

		//mc.renderEngine.bindTexture(TEXTURE);
		//drawTexturedModalRect(j + col, k + line, 176, 0, 16, 60);
	}

}
