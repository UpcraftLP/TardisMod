package net.tardis.mod.common.items.clothing;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.tardis.mod.client.EnumClothes;

public interface IClothItem {

    EnumClothes getModel(EntityLivingBase entityLiving, EntityEquipmentSlot armorSlot);

}
