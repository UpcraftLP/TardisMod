package net.tardis.mod.client.renderers.layers;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.tardis.mod.client.EnumClothes;
import net.tardis.mod.common.items.clothing.IClothItem;
import org.lwjgl.Sys;

public class RenderLayerClothing implements LayerRenderer<EntityPlayer> {

    private RenderPlayer playerRenderer;

    public RenderLayerClothing(RenderPlayer playerRendererIn)
    {
        this.playerRenderer = playerRendererIn;
    }

    @Override
    public void doRenderLayer(EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        for(EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
            if(slot != EntityEquipmentSlot.MAINHAND && slot != EntityEquipmentSlot.OFFHAND && getModel(slot, player) != null) {
                System.out.println("Allowed: " + slot);
                IClothing model = getModel(slot, player);
                if (model != null) {
                    renderModel(model, player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                }
            }
      }

    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    private IClothing getModel(EntityEquipmentSlot slot, EntityPlayer player) {
        ItemStack stack = player.getItemStackFromSlot(slot);
        if(stack.getItem() instanceof IClothItem) {
            IClothItem clothItem = (IClothItem) stack.getItem();
            EnumClothes enumClothes = clothItem.getModel(player, slot);
            return enumClothes.getModel();
        }
        return null;
    }

    private void renderModel(IClothing clothItem, EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
            GlStateManager.pushMatrix();
            clothItem.renderHead(player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.popMatrix();
    }
}
