package es.amadornes.techquake.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGenerator extends ModelBase
{
  //fields
    ModelRenderer body;
    ModelRenderer right;
    ModelRenderer left;
    ModelRenderer top;
  
  public ModelGenerator()
  {
    textureWidth = 256;
    textureHeight = 256;
    
      body = new ModelRenderer(this, 33, 0);
      body.addBox(0F, 0F, 0F, 16, 16, 16);
      body.setRotationPoint(-8F, 8F, -8F);
      body.setTextureSize(256, 256);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      right = new ModelRenderer(this, 0, 0);
      right.addBox(0F, 0F, 0F, 12, 12, 2);
      right.setRotationPoint(-6F, 10F, -10F);
      right.setTextureSize(256, 256);
      right.mirror = true;
      setRotation(right, 0F, 0F, 0F);
      left = new ModelRenderer(this, 0, 0);
      left.addBox(0F, 0F, 0F, 12, 12, 2);
      left.setRotationPoint(6F, 10F, 10F);
      left.setTextureSize(256, 256);
      left.mirror = true;
      setRotation(left, 0F, 3.141593F, 0F);
      top = new ModelRenderer(this, 0, 0);
      top.addBox(0F, 0F, 0F, 12, 12, 2);
      top.setRotationPoint(-6F, 6F, 6F);
      top.setTextureSize(256, 256);
      top.mirror = true;
      setRotation(top, -1.570796F, 0F, 0F);
  }
  
  public void render()
  {
	  float f5 = 0.0625F;
	  body.render(f5);
  }
  
  public void render(boolean left, boolean right, boolean top){
	  render();
	  float f5 = 0.0625F;
	  if(left)this.left.render(f5);
	  if(right)this.right.render(f5);
	  if(top)this.top.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  @Override
public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity e)
  {
  	super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
  }

}