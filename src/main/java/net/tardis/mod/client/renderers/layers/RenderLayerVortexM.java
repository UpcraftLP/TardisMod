package net.tardis.mod.client.renderers.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.tardis.mod.client.models.clothing.ModelVortexM;
import net.tardis.mod.client.util.ModelUtil;
import net.tardis.mod.common.items.TItems;
import net.tardis.mod.util.helpers.PlayerHelper;

public class RenderLayerVortexM implements LayerRenderer{

	private Minecraft mc;
	private ModelVortexM model = new ModelVortexM();
	private RenderPlayer renderPlayer;
	
	public RenderLayerVortexM(RenderPlayer rp) {
		mc = Minecraft.getMinecraft();
		this.renderPlayer = rp;
	}
	@Override
	public void doRenderLayer(EntityLivingBase entity, float f, float f1,float partialTicks, float f2, float f3, float f4, float f5) {
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			if(player.inventory.hasItemStack(new ItemStack(TItems.vortex_manip))) {
				boolean slim = PlayerHelper.hasSmallArms((AbstractClientPlayer) player);
				GlStateManager.pushMatrix();
				if(!slim) GlStateManager.translate(-0.03125, 0, 0);
                mc.getTextureManager().bindTexture(ModelVortexM.TEXTURE);
				ModelUtil.copyAngle(renderPlayer.getMainModel().bipedRightArm, model.Cuff);
				model.render(entity, f, f1, f2, f3, f4, f5);
				GlStateManager.popMatrix();
			}
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}
