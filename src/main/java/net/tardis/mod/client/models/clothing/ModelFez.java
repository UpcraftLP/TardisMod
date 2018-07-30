package net.tardis.mod.client.models.clothing;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.tardis.mod.client.renderers.layers.IClothing;

public class ModelFez extends ModelBiped implements IClothing {
	// fields
	ModelRenderer Shape1;
	
	ModelBiped biped;
	
	public ModelFez(ModelBiped model) {
		this.biped = model;
		textureWidth = 64;
		textureHeight = 32;
		
		Shape1 = new ModelRenderer(this, 40, 0);
		Shape1.addBox(0F, 0F, 0F, 6, 4, 6);
		Shape1.setRotationPoint(0, 0, 0);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		
		this.bipedHeadwear.isHidden = true;
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}


	@Override
	public void renderHead(EntityLivingBase living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(0,1,0);
		Shape1.offsetY = -0.7F;
		Shape1.offsetX = -0.2F;
		Shape1.offsetZ = -0.2F;
		GlStateManager.popMatrix();
		//this.bipedHead.addChild(Shape1);
	}

	@Override
	public void renderChest(EntityLivingBase living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

	}

	@Override
	public void renderRightArm(EntityLivingBase living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

	}

	@Override
	public void renderLeftArm(EntityLivingBase living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

	}

	@Override
	public void renderRightLeg(EntityLivingBase living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

	}

	@Override
	public void renderLeftLeg(EntityLivingBase living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

	}
}
