package es.amadornes.techquake.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelElectricPost extends ModelBase {
    
    // fields
    ModelRenderer Body;
    ModelRenderer Arm;
    ModelRenderer Cap1;
    ModelRenderer Cap2;
    
    public ModelElectricPost() {
    
        textureWidth = 128;
        textureHeight = 64;
        
        Body = new ModelRenderer(this, 0, 0);
        Body.addBox(0F, 0F, 0F, 4, 58, 4);
        Body.setRotationPoint(-2F, -34F, -2F);
        Body.setTextureSize(128, 64);
        Body.mirror = true;
        setRotation(Body, 0F, 0F, 0F);
        Arm = new ModelRenderer(this, 16, 0);
        Arm.addBox(0F, 0F, 0F, 32, 4, 4);
        Arm.setRotationPoint(-16F, -38F, -2F);
        Arm.setTextureSize(128, 64);
        Arm.mirror = true;
        setRotation(Arm, 0F, 0F, 0F);
        Cap1 = new ModelRenderer(this, 16, 8);
        Cap1.addBox(0F, 0F, 0F, 2, 2, 2);
        Cap1.setRotationPoint(-11F, -40F, -1F);
        Cap1.setTextureSize(128, 64);
        Cap1.mirror = true;
        setRotation(Cap1, 0F, 0F, 0F);
        Cap2 = new ModelRenderer(this, 16, 8);
        Cap2.addBox(0F, 0F, 0F, 2, 2, 2);
        Cap2.setRotationPoint(9F, -40F, -1F);
        Cap2.setTextureSize(128, 64);
        Cap2.mirror = true;
        setRotation(Cap2, 0F, 0F, 0F);
    }
    
    public void render(Entity entity, float f, float f1, float f2, float f3,
            float f4, float f5) {
    
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Body.render(f5);
        Arm.render(f5);
        Cap1.render(f5);
        Cap2.render(f5);
    }
    
    public void render(){
        float f5 = 0.0625F;
        Body.render(f5);
        Arm.render(f5);
        Cap1.render(f5);
        Cap2.render(f5);
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z) {
    
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    
}
