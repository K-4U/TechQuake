package es.amadornes.techquake.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBoiler extends ModelBase {
	// fields
	ModelRenderer Cube2;
	ModelRenderer Cube1;
	ModelRenderer Cube;

	public ModelBoiler() {
		textureWidth = 64;
		textureHeight = 32;

		Cube2 = new ModelRenderer(this, -16, 0);
		Cube2.addBox(0F, 1F, 0F, 16, 0, 16);
		Cube2.setRotationPoint(-8F, 8F, -8F);
		Cube2.setTextureSize(64, 32);
		Cube2.mirror = true;
		setRotation(Cube2, 0F, 0F, 0F);
		Cube1 = new ModelRenderer(this, 32, 0);
		Cube1.addBox(0F, 0F, 0F, 16, 0, 16);
		Cube1.setRotationPoint(-8F, 17F, -8F);
		Cube1.setTextureSize(64, 32);
		Cube1.mirror = true;
		setRotation(Cube1, 0F, 0F, 0F);
		Cube = new ModelRenderer(this, 0, 0);
		Cube.addBox(0F, 0F, 0F, 16, 16, 16);
		Cube.setRotationPoint(-8F, 8F, -8F);
		Cube.setTextureSize(64, 32);
		Cube.mirror = true;
		setRotation(Cube, 0F, 0F, 0F);
	}

	public void render() {
		float f5 = 0.0625F;
		Cube2.render(f5);
		Cube.render(f5);
	}
	
	public void renderTab(){
		float f5 = 0.0625F;
		Cube1.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
