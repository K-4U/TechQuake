package es.amadornes.techquake.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import es.amadornes.techquake.inventory.ContainerElectricFurnace;
import es.amadornes.techquake.lib.ModInfo;
import es.amadornes.techquake.tileentity.electric.machine.TileEntityElectricFurnace;

public class GuiElectricFurnace extends GuiContainer {
    
    ResourceLocation          bg = new ResourceLocation(ModInfo.MOD_ID
                                         + ":textures/gui/electric_furnace.png");
    
    TileEntityElectricFurnace te;
    
    int e = 0;
    
    public GuiElectricFurnace(ContainerElectricFurnace par1Container,
            TileEntityElectricFurnace te) {
    
        super(par1Container);
        this.te = te;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int mx, int my) {
    
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(bg);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        double energy = te.getStored() * 50;
        energy /= te.getMaxStored();
        e = (int) Math.ceil(energy);
        
        this.drawTexturedModalRect(k + 8, l + 8 + (50 - e), 176, 16, 16, e);
        
        double progress = te.progress * 22;
        progress /= 50;
        
        this.drawTexturedModalRect(k + 77, l + 35, 176, 0, (int) progress, 16);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mx, int my) {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
    
        String title = "Electric Furnace";
        this.fontRenderer.drawString(title,
                this.xSize / 2 - this.fontRenderer.getStringWidth(title) / 2,
                6, 4210752);
        
        if(mx >= (k + 8) && mx < (k + 8 + 16)){
            if(my >= (l + 8 + (50 - e)) && my < (l + 8 + 50)){
                List<String> tip = new ArrayList<String>();
                
                tip.add("Buffer: " + te.getStored());
                
                this.drawHoveringText(tip, mx - k, my - l, Minecraft.getMinecraft().fontRenderer);
                tip.clear();
            }
        }
    }
    
    @Override
    protected void mouseClicked(int mx, int my, int btn) {
    
        super.mouseClicked(mx, my, btn);
    }
    
}
