package es.amadornes.techquake.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import es.amadornes.techquake.model.ModelFurnace;
import es.amadornes.techquake.tileentity.electric.machine.TileEntityElectricFurnace;

public class RenderFurnace extends TileEntitySpecialRenderer implements
        IItemRenderer {
    
    ModelFurnace                            model   = new ModelFurnace();
    
    private static final ResourceLocation texture = new ResourceLocation(
                                                          "techquake:textures/blocks/machine/furnace/furnace_model.png");
    
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y,
            double z, float unused) {
    
        TileEntityElectricFurnace te = (TileEntityElectricFurnace) tile;
        
        GL11.glPushMatrix();
            GL11.glTranslatef((float) (x + 0.5), (float) (y + 1.0),
                    (float) (z + 0.5));
            
            GL11.glTranslated(0, 0.5, 0);
            
            GL11.glRotatef(180, 1, 0, 0);
            
            /*int md = te.getBlockMetadata();
            while(md > 0){
                GL11.glRotated(90, 0, 1, 0);
                md--;
            }*/
            
            this.bindTexture(texture);
            
            model.render(-te.lidAngle);
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
                render(0.0F, -0.45F, 0.0F, 1.0F, 0, 0, 0);
                return;
            }
            case EQUIPPED: {
                render(0.0F, 0.5F, 0.5F, 1.0F, 0, 0, 0);
                return;
            }
            case INVENTORY: {
                render(0.0F, -0.5F, 0.0F, 1.0F, 0, 0, 0);
                return;
            }
            case EQUIPPED_FIRST_PERSON: {
                render(0.0F, 0.5F, 0.5F, 1.0F, 0, 0, 0);
                return;
            }
            default:
                return;
        }
    }
    
    public void render(double x, double y, double z, double scale, double rx,
            double ry, double rz) {
    
        GL11.glPushMatrix();
        {
            
            GL11.glScaled(scale, scale, scale);
            GL11.glTranslated(x, y, z);
            
            GL11.glTranslated(0, 1.5, 0);
            
            GL11.glRotated(rx, 1, 0, 0);
            GL11.glRotated(ry, 0, 1, 0);
            GL11.glRotated(rz, 0, 0, 1);
            
            GL11.glRotated(180, 1, 0, 1);
            
            FMLClientHandler.instance().getClient().renderEngine
                    .bindTexture(texture);
            
            model.render(0);
            
        }
        GL11.glPopMatrix();
    }
    
}
