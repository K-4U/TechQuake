package es.amadornes.techquake.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import es.amadornes.techquake.api.electricity.IElectric;
import es.amadornes.techquake.model.ModelCable;

public class RenderCable extends TileEntitySpecialRenderer implements
        IItemRenderer {
    
    ModelCable                            model   = new ModelCable();
    
    private static final ResourceLocation texture = new ResourceLocation(
                                                          "techquake:textures/blocks/cable/cable.png");
    
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y,
            double z, float unused) {
    
        IElectric e = (IElectric) tile;
        
        GL11.glPushMatrix();
        {
            GL11.glTranslatef((float) (x + 0.5), (float) (y + 1.0),
                    (float) (z + 0.5));
            GL11.glRotatef(180, 1, 0, 0);
            this.bindTexture(texture);
            boolean renderUp = e.canConnectOnSide(ForgeDirection.UP);
            boolean renderDown = e.canConnectOnSide(ForgeDirection.DOWN);
            boolean renderNorth = e.canConnectOnSide(ForgeDirection.NORTH);
            boolean renderEast = e.canConnectOnSide(ForgeDirection.EAST);
            boolean renderSouth = e.canConnectOnSide(ForgeDirection.SOUTH);
            boolean renderWest = e.canConnectOnSide(ForgeDirection.WEST);
            model.renderModel(renderUp, renderDown, renderSouth, renderNorth,
                    renderEast, renderWest);
            
            int renders = 0;
            if(renderUp)
                renders++;
            if(renderDown)
                renders++;
            if(renderNorth)
                renders++;
            if(renderEast)
                renders++;
            if(renderSouth)
                renders++;
            if(renderWest)
                renders++;
            
            if(renders == 1){
                if(renderUp)
                    this.bindTexture(new ResourceLocation("techquake:textures/blocks/cable/icon3.png"));
                if(renderDown)
                    this.bindTexture(new ResourceLocation("techquake:textures/blocks/cable/icon2.png"));
                if(renderNorth)
                    this.bindTexture(new ResourceLocation("techquake:textures/blocks/cable/icon4.png"));
                if(renderSouth)
                    this.bindTexture(new ResourceLocation("techquake:textures/blocks/cable/icon6.png"));
                if(renderEast)
                    this.bindTexture(new ResourceLocation("techquake:textures/blocks/cable/icon1.png"));
                if(renderWest)
                    this.bindTexture(new ResourceLocation("techquake:textures/blocks/cable/icon5.png"));
                model.renderModel(false, false, false, false, false, false);//FIXME Fix render issues when trying to add the copper texture
            }
        }
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
            case ENTITY: {
                render(0.0F, -0.45F, 0.0F, 1.0F);
                return;
            }
            case EQUIPPED: {
                render(0.0F, 0.5F, 0.5F, 1.0F);
                return;
            }
            case INVENTORY: {
                render(0.0F, -0.5F, 0.0F, 1.0F, true, false);
                return;
            }
            case EQUIPPED_FIRST_PERSON: {
                render(0.0F, 0.5F, 0.5F, 1.0F);
                return;
            }
            default:
                return;
        }
    }
    
    public void render(float x, float y, float z, float scale) {
    
        render(x, y, z, scale, false, false);
    }
    
    public void render(float x, float y, float z, float scale,
            boolean rotate90Deg, boolean isInventory) {
    
        GL11.glPushMatrix();
        {
            
            if (isInventory) GL11.glDisable(GL11.GL_LIGHTING);
            
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(x, y, z);
            if (rotate90Deg) {
                GL11.glRotatef(90F, 0, 1, 0);
            } else {
                GL11.glRotatef(0F, 0, 0, 0);
            }
            
            FMLClientHandler.instance().getClient().renderEngine
                    .bindTexture(texture);
            
            model.renderItem();
            
            if (isInventory) GL11.glEnable(GL11.GL_LIGHTING);
            
        }
        GL11.glPopMatrix();
    }
    
}
