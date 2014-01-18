package es.amadornes.techquake.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFurnace extends ModelBase {
    
    // fields
    ModelRenderer platform1;
    ModelRenderer platform2;
    ModelRenderer platform3;
    ModelRenderer platform4;
    ModelRenderer body1;
    ModelRenderer body2;
    ModelRenderer body3;
    ModelRenderer body4;
    ModelRenderer body5;
    ModelRenderer screen;
    ModelRenderer lid;
    ModelRenderer chimney;
    ModelRenderer body6;
    ModelRenderer chimney2;
    
    public ModelFurnace() {
    
        textureWidth = 128;
        textureHeight = 128;
        
        platform1 = new ModelRenderer(this, 0, 0);
        platform1.addBox(0F, 0F, 0F, 3, 1, 2);
        platform1.setRotationPoint(-6F, 23F, -5F);
        platform1.setTextureSize(128, 128);
        platform1.mirror = true;
        setRotation(platform1, 0F, 0F, 0F);
        platform2 = new ModelRenderer(this, 0, 0);
        platform2.addBox(0F, 0F, 0F, 3, 1, 2);
        platform2.setRotationPoint(3F, 23F, -5F);
        platform2.setTextureSize(128, 128);
        platform2.mirror = true;
        setRotation(platform2, 0F, 0F, 0F);
        platform3 = new ModelRenderer(this, 0, 0);
        platform3.addBox(0F, 0F, 0F, 3, 1, 2);
        platform3.setRotationPoint(-6F, 23F, 5F);
        platform3.setTextureSize(128, 128);
        platform3.mirror = true;
        setRotation(platform3, 0F, 0F, 0F);
        platform4 = new ModelRenderer(this, 0, 0);
        platform4.addBox(0F, 0F, 0F, 3, 1, 2);
        platform4.setRotationPoint(3F, 23F, 5F);
        platform4.setTextureSize(128, 128);
        platform4.mirror = true;
        setRotation(platform4, 0F, 0F, 0F);
        body1 = new ModelRenderer(this, 70, 16);
        body1.addBox(0F, 0F, 0F, 1, 10, 14);
        body1.setRotationPoint(-7F, 12F, -6F);
        body1.setTextureSize(128, 128);
        body1.mirror = true;
        setRotation(body1, 0F, 0F, 0F);
        body2 = new ModelRenderer(this, 57, 0);
        body2.addBox(0F, 0F, 0F, 14, 1, 14);
        body2.setRotationPoint(-7F, 22F, -6F);
        body2.setTextureSize(128, 128);
        body2.mirror = true;
        setRotation(body2, 0F, 0F, 0F);
        body3 = new ModelRenderer(this, 38, 16);
        body3.addBox(0F, 0F, 0F, 1, 10, 14);
        body3.setRotationPoint(6F, 12F, -6F);
        body3.setTextureSize(128, 128);
        body3.mirror = true;
        setRotation(body3, 0F, 0F, 0F);
        body4 = new ModelRenderer(this, 0, 18);
        body4.addBox(0F, 0F, 0F, 14, 10, 1);
        body4.setRotationPoint(-7F, 12F, 7F);
        body4.setTextureSize(128, 128);
        body4.mirror = true;
        setRotation(body4, 0F, 0F, 0F);
        body5 = new ModelRenderer(this, 0, 43);
        body5.addBox(0F, 0F, 0F, 14, 1, 14);
        body5.setRotationPoint(-7F, 12F, -6F);
        body5.setTextureSize(128, 128);
        body5.mirror = true;
        setRotation(body5, 0F, 0F, 0F);
        screen = new ModelRenderer(this, 0, 31);
        screen.addBox(0F, 0F, 0F, 10, 1, 4);
        screen.setRotationPoint(-5F, 12F, -4F);
        screen.setTextureSize(128, 128);
        screen.mirror = true;
        setRotation(screen, 0.2230717F, 0F, 0F);
        lid = new ModelRenderer(this, 0, 5);
        lid.addBox(0F, 0F, 0F, 12, 9, 1);
        lid.setRotationPoint(0, 0, 0);
        lid.setTextureSize(128, 128);
        lid.mirror = true;
        setRotation(lid, 0, 0F, 0F);
        chimney = new ModelRenderer(this, 32, 4);
        chimney.addBox(0F, 0F, 0F, 2, 6, 2);
        chimney.setRotationPoint(4F, 6F, 3F);
        chimney.setTextureSize(128, 128);
        chimney.mirror = true;
        setRotation(chimney, 0F, 0F, 0F);
        body6 = new ModelRenderer(this, 56, 43);
        body6.addBox(0F, 0F, 0F, 12, 0, 12);
        body6.setRotationPoint(-6F, 20F, -5F);
        body6.setTextureSize(128, 128);
        body6.mirror = true;
        setRotation(body6, 0F, 0F, 0F);
        chimney2 = new ModelRenderer(this, 32, 4);
        chimney2.addBox(0F, 0F, 0F, 1, 3, 1);
        chimney2.setRotationPoint(5F, 3F, 4F);
        chimney2.setTextureSize(128, 128);
        chimney2.mirror = true;
        setRotation(chimney2, 0F, 0F, 0F);
    }
    
    public void render(Entity entity, float f, float f1, float f2, float f3,
            float f4, float f5) {
    
        super.render(entity, f, f1, f2, f3, f4, f5);
        platform1.render(f5);
        platform2.render(f5);
        platform3.render(f5);
        platform4.render(f5);
        body1.render(f5);
        body2.render(f5);
        body3.render(f5);
        body4.render(f5);
        body5.render(f5);
        screen.render(f5);
        lid.render(f5);
        chimney.render(f5);
        body6.render(f5);
        chimney2.render(f5);
    }
    
    public void render(double lidAngle){
        float f5 = 0.0625F;
        platform1.render(f5);
        platform2.render(f5);
        platform3.render(f5);
        platform4.render(f5);
        body1.render(f5);
        body2.render(f5);
        body3.render(f5);
        body4.render(f5);
        body5.render(f5);
        screen.render(f5);
        chimney.render(f5);
        body6.render(f5);
        chimney2.render(f5);
        GL11.glPushMatrix();
        
        GL11.glTranslated(f5 * -6, f5 * 13, f5 * -6);
        GL11.glRotated(lidAngle, 1, 0, 0);
        lid.render(f5);
        
        GL11.glPopMatrix();
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z) {
    
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    
}
