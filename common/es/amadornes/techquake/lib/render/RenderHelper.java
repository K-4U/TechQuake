package es.amadornes.techquake.lib.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

public class RenderHelper {
    
    public static RenderBlocks renderBlocks = new RenderBlocks();
    
    public static void renderCube(double x1, double y1, double z1, double x2,
            double y2, double z2, Block block, Icon overrideTexture) {
    
        renderCube(x1, y1, z1, x2, y2, z2, block, overrideTexture, 0);
    }
    
    public static void renderCube(double x1, double y1, double z1, double x2,
            double y2, double z2, Block block, Icon overrideTexture, int meta) {
    
        GL11.glPushMatrix();
        
        Minecraft.getMinecraft().renderEngine
                .bindTexture(TextureMap.locationBlocksTexture);
        
        Tessellator t = Tessellator.instance;
        
        GL11.glColor4f(1, 1, 1, 1);
        
        renderBlocks.setRenderBounds(x1, y1, z1, x2, y2, z2);
        
        t.startDrawingQuads();
        
        Icon useTexture = overrideTexture != null ? overrideTexture : block
                .getIcon(0, meta);
        t.setNormal(0.0F, -1.0F, 0.0F);
        renderBlocks.renderFaceYNeg(block, 0, 0, 0, useTexture);
        
        useTexture = overrideTexture != null ? overrideTexture : block.getIcon(
                1, meta);
        t.setNormal(0.0F, 1.0F, 0.0F);
        renderBlocks.renderFaceYPos(block, 0, 0, 0, useTexture);
        
        useTexture = overrideTexture != null ? overrideTexture : block.getIcon(
                2, meta);
        t.setNormal(0.0F, 0.0F, -1.0F);
        renderBlocks.renderFaceZNeg(block, 0, 0, 0, useTexture);
        
        useTexture = overrideTexture != null ? overrideTexture : block.getIcon(
                3, meta);
        t.setNormal(0.0F, 0.0F, 1.0F);
        renderBlocks.renderFaceZPos(block, 0, 0, 0, useTexture);
        
        useTexture = overrideTexture != null ? overrideTexture : block.getIcon(
                4, meta);
        t.setNormal(-1.0F, 0.0F, 0.0F);
        renderBlocks.renderFaceXNeg(block, 0, 0, 0, useTexture);
        
        useTexture = overrideTexture != null ? overrideTexture : block.getIcon(
                5, meta);
        t.setNormal(1.0F, 0.0F, 0.0F);
        renderBlocks.renderFaceXPos(block, 0, 0, 0, useTexture);
        t.draw();
        
        GL11.glPopMatrix();
    }
    
    public static void renderWorldCube(RenderBlocks renderer, double x1,
            double y1, double z1, double x2, double y2, double z2, Block block,
            Icon overrideTexture) {
    
        renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
        
        Icon useTexture = overrideTexture != null ? overrideTexture : block
                .getBlockTextureFromSide(0);
        renderer.renderFaceYNeg(block, 0, 0, 0, useTexture);
        
        useTexture = overrideTexture != null ? overrideTexture : block
                .getBlockTextureFromSide(1);
        renderer.renderFaceYPos(block, 0, 0, 0, useTexture);
        
        useTexture = overrideTexture != null ? overrideTexture : block
                .getBlockTextureFromSide(2);
        renderer.renderFaceZNeg(block, 0, 0, 0, useTexture);
        
        useTexture = overrideTexture != null ? overrideTexture : block
                .getBlockTextureFromSide(3);
        renderer.renderFaceZPos(block, 0, 0, 0, useTexture);
        
        useTexture = overrideTexture != null ? overrideTexture : block
                .getBlockTextureFromSide(4);
        renderer.renderFaceXNeg(block, 0, 0, 0, useTexture);
        
        useTexture = overrideTexture != null ? overrideTexture : block
                .getBlockTextureFromSide(5);
        renderer.renderFaceXPos(block, 0, 0, 0, useTexture);
    }
    
    public static void renderLiquid(double x, double y, double z,
            FluidStack stack, TileEntitySpecialRenderer renderer, int maxAmt,
            float cornerDist) {
    
        if (stack != null && stack.amount > 0) {
            float height = stack.amount;
            height /= maxAmt;
            if (height > 1F) height = 1F;
            
            GL11.glPushMatrix();
            GL11.glDisable(2896);
            try {
                Fluid fluid = stack.getFluid();
                
                // Get fluid UVs
                Icon texture = fluid.getStillIcon();
                
                // Bind texture
                Minecraft.getMinecraft().renderEngine
                        .bindTexture(getFluidSheet(fluid));
                
                // Declare the tessellator
                Tessellator t = Tessellator.instance;
                
                // Texture scale variables
                double uMin = texture.getInterpolatedU(0.0);
                double uMax = texture.getInterpolatedU(texture.getIconWidth());
                double vMin = texture.getInterpolatedV(0.0);
                double vMax = texture.getInterpolatedV(texture.getIconHeight());
                double vHeight = vMax - vMin;
                
                // Render faces
                {
                    // North
                    {
                        t.startDrawingQuads();
                        t.addVertexWithUV(0.5, -0.5, -0.5, uMax, vMin);
                        t.addVertexWithUV(-0.5, -0.5, -0.5, uMin, vMin);
                        t.addVertexWithUV(-0.5, -0.5 + height + cornerDist,
                                -0.5, uMin, vMin + (vHeight * height));
                        t.addVertexWithUV(0.5, -0.5 + height - cornerDist,
                                -0.5, uMax, vMin + (vHeight * height));
                        t.draw();
                    }
                    // East
                    {
                        t.startDrawingQuads();
                        t.addVertexWithUV(0.5, -0.5, -0.5, uMin, vMin);
                        t.addVertexWithUV(0.5, -0.5 + height - cornerDist,
                                -0.5, uMin, vMin + (vHeight * height));
                        t.addVertexWithUV(0.5, -0.5 + height + cornerDist, 0.5,
                                uMax, vMin + (vHeight * height));
                        t.addVertexWithUV(0.5, -0.5, 0.5, uMax, vMin);
                        t.draw();
                    }
                    // South
                    {
                        t.startDrawingQuads();
                        t.addVertexWithUV(0.5, -0.5, 0.5, uMin, vMin);
                        t.addVertexWithUV(0.5, -0.5 + height + cornerDist, 0.5,
                                uMin, vMin + (vHeight * height));
                        t.addVertexWithUV(-0.5, -0.5 + height - cornerDist,
                                0.5, uMax, vMin + (vHeight * height));
                        t.addVertexWithUV(-0.5, -0.5, 0.5, uMax, vMin);
                        t.draw();
                    }
                    // West
                    {
                        t.startDrawingQuads();
                        t.addVertexWithUV(-0.5, -0.5, 0.5, uMin, vMin);
                        t.addVertexWithUV(-0.5, -0.5 + height - cornerDist,
                                0.5, uMin, vMin + (vHeight * height));
                        t.addVertexWithUV(-0.5, -0.5 + height + cornerDist,
                                -0.5, uMax, vMin + (vHeight * height));
                        t.addVertexWithUV(-0.5, -0.5, -0.5, uMax, vMin);
                        t.draw();
                    }
                    // Top
                    {
                        t.startDrawingQuads();
                        t.addVertexWithUV(0.5, -0.5 + height + cornerDist
                                - 0.001, 0.5, uMax, vMin);
                        t.addVertexWithUV(0.5, -0.5 + height - cornerDist
                                - 0.001, -0.5, uMin, vMin);
                        t.addVertexWithUV(-0.5, -0.5 + height + cornerDist
                                - 0.001, -0.5, uMin, vMax);
                        t.addVertexWithUV(-0.5, -0.5 + height - cornerDist
                                - 0.001, 0.5, uMax, vMax);
                        t.draw();
                    }
                    // Bottom
                    {
                        t.startDrawingQuads();
                        t.addVertexWithUV(0.5, -0.5, -0.5, uMax, vMin);
                        t.addVertexWithUV(0.5, -0.5, 0.5, uMin, vMin);
                        t.addVertexWithUV(-0.5, -0.5, 0.5, uMin, vMax);
                        t.addVertexWithUV(-0.5, -0.5, -0.5, uMax, vMax);
                        t.draw();
                    }
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            GL11.glEnable(2896);
            GL11.glPopMatrix();
        }
    }
    
    public static ResourceLocation getFluidSheet(FluidStack liquid) {
    
        if (liquid == null) return TextureMap.locationBlocksTexture;
        return getFluidSheet(liquid.getFluid());
    }
    
    /**
     * @param liquid
     */
    public static ResourceLocation getFluidSheet(Fluid liquid) {
    
        return TextureMap.locationBlocksTexture;
    }
    
    public static void renderBeaconBase(World w) {
    
        ExtendedRenderBlocks rb = new ExtendedRenderBlocks(w);
        GL11.glPushMatrix();
        Tessellator t = Tessellator.instance;
        
        double x = 160;
        double y = 66;
        double z = 246;
        
        GL11.glColor4f(1, 1, 1, 1);
        
        t.startDrawingQuads();
        rb.renderCustomBeaconBase(Block.glass, (int) x, (int) y, (int) z);
        t.draw();
        
        GL11.glPopMatrix();
    }
    
}
