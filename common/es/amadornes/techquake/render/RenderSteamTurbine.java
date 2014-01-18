package es.amadornes.techquake.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import es.amadornes.techquake.fluid.FluidManager;
import es.amadornes.techquake.lib.ModInfo;
import es.amadornes.techquake.lib.render.RenderHelper;
import es.amadornes.techquake.tileentity.powergen.TileEntitySteamTurbine;

public class RenderSteamTurbine extends TileEntitySpecialRenderer implements IItemRenderer {
	
	private IModelCustom model = AdvancedModelLoader.loadModel("/assets/" + ModInfo.MOD_ID + "/models/steam_turbine.obj");
	private static ResourceLocation texture = new ResourceLocation(ModInfo.MOD_ID + ":textures/blocks/texture/iron.png");

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
			switch (type) {
	        case ENTITY: {
	            if(item.getItemFrame() == null){
	                render(-0.5F, -0.25F, 0.5F, 1.0F, 40.0F, 0.0F, 0.0F);
	            }else{
	                render(0F, 0.09F, 0.7F, 1.0F, 40.0F, 0.0F, -90.0F);
	            }
	            break;
	        }
	        case EQUIPPED: {
	            render(-0.75F, 0.5F, -0.5F, 4.0F, 0.0F, 160.0F, 3.0F);
	            break;
	        }
	        case EQUIPPED_FIRST_PERSON: {
	            render(-5F, 0F, -5F, 4F, 0F, 0F, 0F);
	            break;
	        }
	        case INVENTORY: {
	            render(0F, 0F, 0F, 3.5F, -45F, -45F, 0);
	            break;
	        }
	        default:
	        	break;
			}
		GL11.glPopMatrix();
	}
	
	public void render(float x, float y, float z, float scale, float rx, float ry, float rz){
		GL11.glPushMatrix();
			
			scale /= 3;

	        GL11.glRotatef(rx, 1, 0, 0);
	        GL11.glRotatef(ry, 0, 1, 0);
	        GL11.glRotatef(rz, 0, 0, 1);
	        GL11.glTranslatef(x, y, z);
	        GL11.glScalef(scale, scale, scale);
	        
	        float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
	        rotationAngle *= 8;
	        
	        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
	        GL11.glPushMatrix();
	        	model.renderAllExcept(new String[]{"Blade_Plane"});
	        	GL11.glPushMatrix();
	        		GL11.glRotatef(rotationAngle, 1, 0, 0);
		        	model.renderOnly(new String[]{"Blade_Plane"});
		        	GL11.glRotatef(90F, 1F, 0F, 0F);
		        	model.renderOnly(new String[]{"Blade_Plane"});
		        	GL11.glRotatef(90F, 1F, 0F, 0F);
		        	model.renderOnly(new String[]{"Blade_Plane"});
		        	GL11.glRotatef(90F, 1F, 0F, 0F);
		        	model.renderOnly(new String[]{"Blade_Plane"});
		        GL11.glPopMatrix();
	        GL11.glPopMatrix();
		
        GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		TileEntitySteamTurbine te = (TileEntitySteamTurbine) tile;
		
		int meta = tile.getBlockMetadata()%6;
		float rx = 0F;
		float ry = 0F;
		float rz = 0F;
		float tx = 0F;
		float ty = 0F;
		float tz = 0F;
		
		switch(meta){
		case 0:
			rz = -90F;
			tx = 0.5F;
			ty = 0.25F;
			break;
		case 1:
			rz = 90F;
			tx = 0.5F;
			ty = -0.25F;
			break;
		case 2:
			ry = 90F;
			tx = 0.5F;
			tz = 0.25F;
			break;
		case 3:
			ry = -90F;
			tz = -0.25F;
			tx = 0.5F;
			break;
		case 4:
			ry = 180F;
			tx = 0.75F;
			break;
		case 5:
			tx = 0.25F;
			//DEFAULT
			break;
		}
		
		GL11.glPushMatrix();
			boolean was = GL11.glIsEnabled(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_CULL_FACE);
			switch(meta){
			case 0:
				RenderHelper.renderCube(x, y, z, x+1, y+0.75, z+1, Block.glass, null);
				RenderHelper.renderCube(x, y + 0.75, z, x+1, y+1, z+1, Block.blockIron, null);
				break;
			case 1:
				RenderHelper.renderCube(x, y + 0.25, z, x+1, y+1, z+1, Block.glass, null);
				RenderHelper.renderCube(x, y, z, x+1, y+ 0.25, z+1, Block.blockIron, null);
				break;
			case 2:
				RenderHelper.renderCube(x, y, z, x+1, y+1, z+0.75, Block.glass, null);
				RenderHelper.renderCube(x, y, z+0.75, x+1, y+1, z+1, Block.blockIron, null);
				break;
			case 3:
				RenderHelper.renderCube(x, y, z+0.25, x+1, y+1, z+1, Block.glass, null);
				RenderHelper.renderCube(x, y, z, x+1, y+1, z+0.25, Block.blockIron, null);
				break;
			case 4:
				RenderHelper.renderCube(x, y, z, x+0.75, y+1, z+1, Block.glass, null);
				RenderHelper.renderCube(x+0.75, y, z, x+1, y+1, z+1, Block.blockIron, null);
				break;
			case 5:
				RenderHelper.renderCube(x+0.25, y, z, x+1, y+1, z+1, Block.glass, null);
				RenderHelper.renderCube(x, y, z, x+0.25, y+1, z+1, Block.blockIron, null);
				break;
			}
			if(!was)
				GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
	        GL11.glTranslated(x, y, z);
	        
	        was = GL11.glIsEnabled(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_CULL_FACE);
	        
	        GL11.glTranslatef(0.0F, 0.5F, 0.5F);
	        GL11.glTranslatef(tx, ty, tz);
	        
			GL11.glRotatef(rx, 1, 0, 0);
			GL11.glRotatef(ry, 0, 1, 0);
			GL11.glRotatef(rz, 0, 0, 1);
			
			GL11.glPushMatrix();
			GL11.glPopMatrix();
	        
	        float rotationAngle = te.getRotation()%360;
	        
	        GL11.glPushMatrix();
	        	FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        		GL11.glRotatef(rotationAngle, 1F, 0F, 0F);
	        	model.renderAll();
	        	GL11.glRotatef(90F, 1F, 0F, 0F);
	        	model.renderOnly(new String[]{"Blade_Plane"});
	        	GL11.glRotatef(90F, 1F, 0F, 0F);
	        	model.renderOnly(new String[]{"Blade_Plane"});
	        	GL11.glRotatef(90F, 1F, 0F, 0F);
	        	model.renderOnly(new String[]{"Blade_Plane"});
	        GL11.glPopMatrix();
	        
			if(was)
				GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
        

		GL11.glPushMatrix();
	        GL11.glTranslated(x, y, z);
	        
	        {
	        	GL11.glPushMatrix();
	        		
	        		GL11.glTranslated(0.5, 0.5, 0.5);
	        		GL11.glScaled(0.999, 0.999, 0.999);
	        		GL11.glEnable(GL11.GL_BLEND);
	    			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	    			float amt = te.getAmount();
	    			GL11.glColor4f(1F, 1F, 1F, amt);
	    			RenderHelper.renderLiquid(0, 0, 0, new FluidStack(FluidManager.STEAM, 1000), this, 1000, 0);
	    			GL11.glDisable(GL11.GL_BLEND);
	        		
	        	GL11.glPopMatrix();
	        }
	    GL11.glPopMatrix();
	}

}
