package es.amadornes.techquake.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTerminal extends ModelBase {
	// fields
	ModelRenderer Base;
	ModelRenderer Support1;
	ModelRenderer Button;
	ModelRenderer Windows;
	ModelRenderer Box1;
	ModelRenderer Box2;
	ModelRenderer Box3;
	ModelRenderer Box4;
	ModelRenderer Box5;

	public ModelTerminal() {
		textureWidth = 64;
		textureHeight = 128;

		Base = new ModelRenderer(this, 0, 70);
		Base.addBox(0F, 0F, 0F, 14, 1, 9);
		Base.setRotationPoint(-7F, 23F, -1.8F);
		Base.setTextureSize(64, 128);
		Base.mirror = true;
		setRotation(Base, 0F, 0F, 0F);
		Support1 = new ModelRenderer(this, 23, 23);
		Support1.addBox(0F, 0F, 0F, 14, 2, 1);
		Support1.setRotationPoint(-7F, 21F, -0.3F);
		Support1.setTextureSize(64, 128);
		Support1.mirror = true;
		setRotation(Support1, -1.189716F, 0F, 0F);
		Button = new ModelRenderer(this, 23, 17);
		Button.addBox(0F, 0F, 0F, 14, 2, 1);
		Button.setRotationPoint(-7F, 22F, -2F);
		Button.setTextureSize(64, 128);
		Button.mirror = true;
		setRotation(Button, 0.2230717F, 0F, 0F);
		Windows = new ModelRenderer(this, 22, 33);
		Windows.addBox(0F, 0F, 0F, 12, 9, 1);
		Windows.setRotationPoint(-6F, 13F, 0F);
		Windows.setTextureSize(64, 128);
		Windows.mirror = true;
		setRotation(Windows, 0F, 0F, 0F);
		Box1 = new ModelRenderer(this, 0, 23);
		Box1.addBox(0F, 0F, 0F, 1, 11, 9);
		Box1.setRotationPoint(-7F, 12F, -1F);
		Box1.setTextureSize(64, 128);
		Box1.mirror = true;
		setRotation(Box1, -0.0371786F, 0F, 0F);
		Box2 = new ModelRenderer(this, 0, 2);
		Box2.addBox(0F, 0F, 0F, 1, 11, 9);
		Box2.setRotationPoint(6F, 12F, -1F);
		Box2.setTextureSize(64, 128);
		Box2.mirror = true;
		setRotation(Box2, -0.0371786F, 0F, 0F);
		Box3 = new ModelRenderer(this, 23, 28);
		Box3.addBox(0F, 0F, 0F, 14, 2, 1);
		Box3.setRotationPoint(-7F, 12F, 1F);
		Box3.setTextureSize(64, 128);
		Box3.mirror = true;
		setRotation(Box3, -1.59868F, 0F, 0F);
		Box4 = new ModelRenderer(this, 0, 55);
		Box4.addBox(0F, 0F, 0F, 14, 11, 1);
		Box4.setRotationPoint(-7F, 13F, 7F);
		Box4.setTextureSize(64, 128);
		Box4.mirror = true;
		setRotation(Box4, -0.0371755F, 0F, 0F);
		Box5 = new ModelRenderer(this, 0, 45);
		Box5.addBox(0F, 0F, 0F, 14, 1, 8);
		Box5.setRotationPoint(-7F, 12F, 0F);
		Box5.setTextureSize(64, 128);
		Box5.mirror = true;
		setRotation(Box5, -0.0371755F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5,entity);
		Base.render(f5);
		Support1.render(f5);
		Button.render(f5);
		Windows.render(f5);
		Box1.render(f5);
		Box2.render(f5);
		Box3.render(f5);
		Box4.render(f5);
		Box5.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void render() {
		float f5 = 0.0625F;
		Base.render(f5);
		Support1.render(f5);
		Button.render(f5);
		Windows.render(f5);
		Box1.render(f5);
		Box2.render(f5);
		Box3.render(f5);
		Box4.render(f5);
		Box5.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity e) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	}

}