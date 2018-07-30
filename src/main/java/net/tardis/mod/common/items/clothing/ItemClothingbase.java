package net.tardis.mod.common.items.clothing;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.tardis.mod.client.EnumClothes;
import net.tardis.mod.client.models.clothing.ModelBlankBiped;

import javax.annotation.Nullable;

public class ItemClothingbase extends ItemArmor implements IClothItem {

    private EnumClothes clothes;

    public ItemClothingbase(ArmorMaterial materialIn, EnumClothes clothing,  EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, 0, equipmentSlotIn);
        this.clothes = clothing;
    }

    @Nullable
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        return new ModelBlankBiped();
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return null;
    }


    @Override
    public EnumClothes getModel(EntityLivingBase entityLiving, EntityEquipmentSlot armorSlot) {
        return clothes;
    }
}
