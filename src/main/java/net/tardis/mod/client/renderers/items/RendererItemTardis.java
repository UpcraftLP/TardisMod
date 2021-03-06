package net.tardis.mod.client.renderers.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.tardis.mod.client.models.exteriors.ModelLeftDoor01;
import net.tardis.mod.client.models.exteriors.ModelRightDoor01;
import net.tardis.mod.client.models.exteriors.ModelTardis01;
import net.tardis.mod.client.renderers.tiles.RenderTileDoor;

public class RendererItemTardis extends TileEntityItemStackRenderer {
	
	Minecraft mc;
	ModelTardis01 model = new ModelTardis01();
	ModelRightDoor01 door_r = new ModelRightDoor01();
	ModelLeftDoor01 door_l = new ModelLeftDoor01();
	
	public RendererItemTardis() {
		mc = Minecraft.getMinecraft();
	}
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GlStateManager.pushMatrix();
		mc.getTextureManager().bindTexture(RenderTileDoor.TEXTURE);
		double scale = 0.35D;
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.translate(1.5, 2, 0);
		GlStateManager.rotate(180, 1, 0, 0);
		GlStateManager.rotate(23.5F, 1, 0, 0);
		GlStateManager.rotate(45, 0, 1, 0);
		model.setLight(false);
		model.render(null, 0, 0, 0, 0, 0, 0.0625F);
		door_r.render(null, 0, 0, 0, 0, 0, 0.0625F);
		door_l.render(null, 0, 0, 0, 0, 0, 0.0625F);
		GlStateManager.popMatrix();
	}
}
