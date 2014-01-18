package es.amadornes.techquake.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import es.amadornes.techquake.lib.render.RenderHelper;
import es.amadornes.techquake.model.ModelBoiler;
import es.amadornes.techquake.tileentity.powergen.TileEntityBoiler;

public class RenderBoiler extends TileEntitySpecialRenderer implements
        IItemRenderer {
    
    ModelBoiler      model = new ModelBoiler();
    ResourceLocation texture = new ResourceLocation("techquake:textures/blocks/powergen/boiler.png");
    
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y,
            double z, float f) {
    
        boolean fancy = Minecraft.isFancyGraphicsEnabled();
        
        GL11.glPushMatrix();
        
        GL11.glScalef(0.999F, 0.999F, 0.999F);
        
        TileEntityBoiler te = (TileEntityBoiler) tile;
        if (fancy) {
            GL11.glDisable(GL11.GL_CULL_FACE);
            // GL11.glEnable(GL11.GL_BLEND);
        }
        
        GL11.glPushMatrix();
        GL11.glTranslatef((float) (x + 0.5), (float) (y + 1.5),
                (float) (z + 0.5));
        GL11.glRotatef(180, 1, 0, 0);
        bindTexture(texture);
        GL11.glPushMatrix();
        model.render();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef((float) (x + 0.5), (float) (y + 1.5),
                (float) (z + 0.5));
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glPushMatrix();
        float rest = te.temperature;
        rest /= 1000F;
        rest = 1F - rest;
        rest *= 0.8F;
        rest += 0.1F;
        rest += te.temperature / 100F;
        GL11.glColor4f(1F, rest, rest, 1F);
        model.renderTab();
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        GL11.glPushMatrix();
        float waterStart = 0.425F;
        
        GL11.glTranslated(x + 0.5, y + 0.5 + waterStart, z + 0.5);
        GL11.glScalef(0.999F, 0.999F, 0.999F);
        GL11.glTranslatef(0, 0, 0);
        
        float max = FluidContainerRegistry.BUCKET_VOLUME * 32;
        float amt = te.getWater() * 2;
        max /= 16;
        amt /= 16;
        float cornerDist = 1;
        cornerDist /= 16;
        cornerDist *= (te.getCornerHeight() - 16);
        cornerDist *= 0.025F;
        
        if (amt > 0) {
            GL11.glTranslated(0, -0.3, 0);
            GL11.glScaled(0.999, 0.5, 0.999);
            RenderHelper.renderLiquid(te.xCoord, te.yCoord, te.zCoord,
                    new FluidStack(FluidRegistry.WATER, (int) amt), this,
                    (int) max, cornerDist);
        }
        
        GL11.glPopMatrix();
        
        GL11.glDisable(GL11.GL_BLEND);
        
        GL11.glPopMatrix();
    }
    
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
    
        return true;
    }
    
    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
            ItemRendererHelper helper) {
    
        return true;
    }
    
    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
    
        switch (type) {
            case ENTITY:
                renderEntity();
                break;
            case EQUIPPED:
                renderEquipped();
                break;
            case EQUIPPED_FIRST_PERSON:
                renderEquippedFirstPerson();
                break;
            case INVENTORY:
                renderInventory();
                break;
            default:
                break;
        }
    }
    
    private void renderEntity() {
    
        GL11.glPushMatrix();
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0F, -1F, 0F);
        
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        GL11.glPushMatrix();
        model.renderTab();
        model.render();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
    
    private void renderEquipped() {
    
        GL11.glPushMatrix();
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0F, -1F, 0F);
        
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        GL11.glPushMatrix();
        model.renderTab();
        model.render();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
    
    private void renderEquippedFirstPerson() {
    
        GL11.glPushMatrix();
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0F, -1F, 0F);
        
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        GL11.glPushMatrix();
        model.renderTab();
        model.render();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
    
    private void renderInventory() {
    
        GL11.glPushMatrix();
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0F, -1F, 0F);
        
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        GL11.glPushMatrix();
        model.renderTab();
        model.render();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
    
}
