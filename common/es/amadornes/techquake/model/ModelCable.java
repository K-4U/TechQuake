package es.amadornes.techquake.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCable extends ModelBase {
	// fields
	ModelRenderer cableWest;
	ModelRenderer cableEast;
	ModelRenderer cableSouth;
	ModelRenderer cableNorth;
	ModelRenderer cableUp;
	ModelRenderer cableDown;
	ModelRenderer cablebase;

	public ModelCable() {
		textureWidth = 32;
		textureHeight = 64;

		cableWest = new ModelRenderer(this, 0, 8);
		cableWest.addBox(0F, 0F, 0F, 6, 4, 4);
		cableWest.setRotationPoint(-8F, 6F, -2F);
		cableWest.setTextureSize(64, 32);
		cableWest.mirror = true;
		setRotation(cableWest, 0F, 0F, 0F);
		cableEast = new ModelRenderer(this, 0, 8);
		cableEast.addBox(0F, 0F, 0F, 6, 4, 4);
		cableEast.setRotationPoint(2F, 6F, -2F);
		cableEast.setTextureSize(64, 32);
		cableEast.mirror = true;
		setRotation(cableEast, 0F, 0F, 0F);
		cableSouth = new ModelRenderer(this, 0, 16);
		cableSouth.addBox(0F, 0F, 0F, 4, 4, 6);
		cableSouth.setRotationPoint(-2F, 6F, -8F);
		cableSouth.setTextureSize(64, 32);
		cableSouth.mirror = true;
		setRotation(cableSouth, 0F, 0F, 0F);
		cableNorth = new ModelRenderer(this, 0, 16);
		cableNorth.addBox(0F, 0F, 0F, 4, 4, 6);
		cableNorth.setRotationPoint(-2F, 6F, 2F);
		cableNorth.setTextureSize(64, 32);
		cableNorth.mirror = true;
		setRotation(cableNorth, 0F, 0F, 0F);
		cableUp = new ModelRenderer(this, 0, 26);
		cableUp.addBox(0F, 0F, 0F, 4, 6, 4);
		cableUp.setRotationPoint(-2F, 0F, -2F);
		cableUp.setTextureSize(64, 32);
		cableUp.mirror = true;
		setRotation(cableUp, 0F, 0F, 0F);
		cableDown = new ModelRenderer(this, 0, 26);
		cableDown.addBox(0F, 0F, 0F, 4, 6, 4);
		cableDown.setRotationPoint(-2F, 10F, -2F);
		cableDown.setTextureSize(64, 32);
		cableDown.mirror = true;
		setRotation(cableDown, 0F, 0F, 0F);
		cablebase = new ModelRenderer(this, 0, 0);
		cablebase.addBox(0F, 0F, 0F, 4, 4, 4);
		cablebase.setRotationPoint(-2F, 6F, -2F);
		cablebase.setTextureSize(64, 32);
		cablebase.mirror = true;
		setRotation(cablebase, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		cableWest.render(f5);
		cableEast.render(f5);
		cableSouth.render(f5);
		cableNorth.render(f5);
		cableUp.render(f5);
		cableDown.render(f5);
		cablebase.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void renderModel(boolean renderUp, boolean renderDown, boolean renderSouth, boolean renderNorth, boolean renderEast, boolean renderWest) {
		float f5 = 0.0625F;
	    cablebase.render(f5);
		if (renderSouth)
			cableSouth.render(f5);
		if (renderNorth)
			cableNorth.render(f5);
		if (renderEast)
			cableEast.render(f5);
		if (renderWest)
			cableWest.render(f5);
		if (renderUp)
			cableUp.render(f5);
		if (renderDown)
			cableDown.render(f5);
	}

	public void renderItem() {
	    renderModel(false, false, true, true, false, false);
	}

}
