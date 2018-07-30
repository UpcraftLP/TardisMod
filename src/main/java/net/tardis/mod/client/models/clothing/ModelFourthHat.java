package net.tardis.mod.client.models.clothing;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.tardis.mod.Tardis;
import net.tardis.mod.client.renderers.layers.IClothing;

public class ModelFourthHat extends ModelBiped implements IClothing
{
  //fields
	public static final ResourceLocation TEXTURE = new ResourceLocation(Tardis.MODID, "textures/clothing/fourth_hat.png");
    ModelRenderer HatBase1;
    ModelRenderer HatBase2;
    ModelRenderer HatBase3;
    ModelRenderer HatBase4;
    ModelRenderer HatBase5;
    ModelRenderer HatBase6;
    ModelRenderer HatMiddle1;
    ModelRenderer HatMiddle2;
    ModelRenderer HatMiddle3;
    ModelRenderer HatMiddle4;
    ModelRenderer HatMiddle5;
    ModelRenderer HatStrap1;
    ModelRenderer HatStrap2;
    ModelRenderer HatBow1;
    ModelRenderer HatBow2;
    ModelRenderer HatTop1;
    ModelRenderer HatTop2;
    ModelRenderer HatTop3;
    ModelRenderer HatTop4;
  
  public ModelFourthHat(){
    textureWidth = 128;
    textureHeight = 128;
    
      HatBase1 = new ModelRenderer(this, 0, 55);
      HatBase1.addBox(0F, 0F, 0F, 14, 1, 8);
      HatBase1.setRotationPoint(-7F, -8F, -4F);
      HatBase1.setTextureSize(128, 128);
      HatBase1.mirror = true;
    HatBase1.offsetY += 0.1F;
      setRotation(HatBase1, 0F, 0F, 0F);

      HatBase2 = new ModelRenderer(this, 0, 39);
      HatBase2.addBox(0F, 0F, 0F, 8, 1, 14);
      HatBase2.setRotationPoint(-4F, -8F, -7F);
      HatBase2.setTextureSize(128, 128);
      HatBase2.mirror = true;
    HatBase2.offsetY += 0.1F;
      setRotation(HatBase2, 0F, 0F, 0F);

      HatBase3 = new ModelRenderer(this, 0, 25);
      HatBase3.addBox(0F, 0F, 0F, 1, 1, 12);
      HatBase3.setRotationPoint(-5F, -8F, -6F);
      HatBase3.setTextureSize(128, 128);
      HatBase3.mirror = true;
    HatBase3.offsetY += 0.1F;
      setRotation(HatBase3, 0F, 0F, 0F);

      HatBase4 = new ModelRenderer(this, 0, 11);
      HatBase4.addBox(0F, 0F, 0F, 1, 1, 12);
      HatBase4.setRotationPoint(4F, -8F, -6F);
      HatBase4.setTextureSize(128, 128);
      HatBase4.mirror = true;
    HatBase4.offsetY += 0.1F;
      setRotation(HatBase4, 0F, 0F, 0F);


    HatBase5 = new ModelRenderer(this, 38, 36);
      HatBase5.addBox(0F, 0F, 0F, 12, 1, 1);
      HatBase5.setRotationPoint(-6F, -8F, -5F);
      HatBase5.setTextureSize(128, 128);
    HatBase5.offsetY += 0.1F;
      HatBase5.mirror = true;
      setRotation(HatBase5, 0F, 0F, 0F);

      HatBase6 = new ModelRenderer(this, 38, 33);
      HatBase6.addBox(0F, 0F, 0F, 12, 1, 1);
      HatBase6.setRotationPoint(-6F, -8F, 4F);
      HatBase6.setTextureSize(128, 128);
      HatBase6.mirror = true;
    HatBase6.offsetY += 0.1F;
      setRotation(HatBase6, 0F, 0F, 0F);


    HatMiddle1 = new ModelRenderer(this, 32, 0);
      HatMiddle1.addBox(0F, 0F, 0F, 8, 4, 8);
      HatMiddle1.setRotationPoint(-4F, -11.5F, -4F);
      HatMiddle1.setTextureSize(128, 128);
      HatMiddle1.mirror = true;
    HatMiddle1.offsetY += 0.1F;
      setRotation(HatMiddle1, 0F, 0F, 0F);

      HatMiddle2 = new ModelRenderer(this, 46, 45);
      HatMiddle2.addBox(0F, 0F, 0F, 1, 1, 8);
      HatMiddle2.setRotationPoint(4F, -8.5F, -4F);
      HatMiddle2.setTextureSize(128, 128);
      HatMiddle2.mirror = true;
    HatMiddle2.offsetY += 0.1F;
      setRotation(HatMiddle2, 0F, 0F, 0.7853982F);

      HatMiddle3 = new ModelRenderer(this, 46, 55);
      HatMiddle3.addBox(0F, 0F, 0F, 1, 1, 8);
      HatMiddle3.setRotationPoint(-4.5F, -8F, -4F);
      HatMiddle3.setTextureSize(128, 128);
      HatMiddle3.mirror = true;
    HatMiddle3.offsetY += 0.1F;
      setRotation(HatMiddle3, 0F, 0F, -0.7853982F);

      HatMiddle4 = new ModelRenderer(this, 46, 42);
      HatMiddle4.addBox(0F, 0F, 0F, 8, 1, 1);
      HatMiddle4.setRotationPoint(-4F, -8F, -4.5F);
      HatMiddle4.setTextureSize(128, 128);
      HatMiddle4.mirror = true;
    HatMiddle4.offsetY += 0.1F;
      setRotation(HatMiddle4, 0.7853982F, 0F, 0F);

      HatMiddle5 = new ModelRenderer(this, 46, 39);
      HatMiddle5.addBox(0F, 0F, 0F, 8, 1, 1);
      HatMiddle5.setRotationPoint(-4F, -8.5F, 4F);
      HatMiddle5.setTextureSize(128, 128);
      HatMiddle5.mirror = true;
    HatMiddle5.offsetY += 0.1F;
      setRotation(HatMiddle5, -0.7853982F, 0F, 0F);

    HatStrap1 = new ModelRenderer(this, 32, 13);
      HatStrap1.addBox(0F, 0F, 0F, 8, 1, 8);
      HatStrap1.setRotationPoint(-4.1F, -10F, -4.1F);
      HatStrap1.setTextureSize(128, 128);
      HatStrap1.mirror = true;
    HatStrap1.offsetY += 0.1F;
      setRotation(HatStrap1, 0F, 0F, 0F);

    HatStrap2 = new ModelRenderer(this, 32, 23);
      HatStrap2.addBox(0F, 0F, 0F, 8, 1, 8);
      HatStrap2.setRotationPoint(-3.9F, -10F, -3.9F);
      HatStrap2.setTextureSize(128, 128);
      HatStrap2.mirror = true;
    HatStrap2.offsetY += 0.1F;
      setRotation(HatStrap2, 0F, 0F, 0F);

    HatBow1 = new ModelRenderer(this, 65, 0);
      HatBow1.addBox(0F, 0F, 0F, 1, 1, 5);
      HatBow1.setRotationPoint(3.3F, -10F, -2.5F);
      HatBow1.setTextureSize(128, 128);
      HatBow1.mirror = true;
    HatBow1.offsetY += 0.1F;
      setRotation(HatBow1, 0F, 0F, 0F);

    HatBow2 = new ModelRenderer(this, 65, 7);
      HatBow2.addBox(0F, 0F, 0F, 1, 1, 2);
      HatBow2.setRotationPoint(3.4F, -10F, -1F);
      HatBow2.setTextureSize(128, 128);
      HatBow2.mirror = true;
    HatBow2.offsetY += 0.1F;
      setRotation(HatBow2, 0F, 0F, 0F);

    HatTop1 = new ModelRenderer(this, 65, 12);
      HatTop1.addBox(0F, 0F, 0F, 2, 1, 8);
      HatTop1.setRotationPoint(-4F, -12.4F, -4F);
      HatTop1.setTextureSize(128, 128);
      HatTop1.mirror = true;
    HatTop1.offsetY += 0.1F;
      setRotation(HatTop1, 0F, 0F, 0F);

    HatTop2 = new ModelRenderer(this, 65, 40);
      HatTop2.addBox(0F, 0F, 0F, 8, 2, 2);
      HatTop2.setRotationPoint(-4F, -12.8F, 2F);
      HatTop2.setTextureSize(128, 128);
      HatTop2.mirror = true;
    HatTop2.offsetY += 0.1F;
      setRotation(HatTop2, 0F, 0F, 0F);

    HatTop3 = new ModelRenderer(this, 65, 30);
      HatTop3.addBox(0F, 0F, 0F, 2, 1, 8);
      HatTop3.setRotationPoint(2F, -12.4F, -4F);
      HatTop3.setTextureSize(128, 128);
      HatTop3.mirror = true;
    HatTop3.offsetY += 0.1F;
      setRotation(HatTop3, 0F, 0F, 0F);

    HatTop4 = new ModelRenderer(this, 65, 22);
      HatTop4.addBox(0F, 0F, 0F, 4, 1, 6);
      HatTop4.setRotationPoint(-2F, -12F, -4F);
      HatTop4.setTextureSize(128, 128);
      HatTop4.mirror = true;
    HatTop4.offsetY += 0.1F;
      setRotation(HatTop4, 0F, 0F, 0F);
  }
  
  @Override
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale)
  {
    GlStateManager.pushMatrix();
    GlStateManager.scale(1.1, 1.1, 1.1);
    if(entity != null && entity.isSneaking()) {
    	GlStateManager.translate(0, 0.2F, 0);
    }
    GlStateManager.rotate(f3, 0, 1, 0);
    GlStateManager.rotate(f4, 1, 0, 0);
    HatBase1.render(scale);
    HatBase2.render(scale);
    HatBase3.render(scale);
    HatBase4.render(scale);
    HatBase5.render(scale);
    HatBase6.render(scale);
    HatMiddle1.render(scale);
    HatMiddle2.render(scale);
    HatMiddle3.render(scale);
    HatMiddle4.render(scale);
    HatMiddle5.render(scale);
    HatStrap1.render(scale);
    HatStrap2.render(scale);
    HatBow1.render(scale);
    HatBow2.render(scale);
    HatTop1.render(scale);
    HatTop2.render(scale);
    HatTop3.render(scale);
    HatTop4.render(scale);
    GlStateManager.popMatrix();
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }


  @Override
  public void renderHead(EntityLivingBase living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      render(living, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
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
