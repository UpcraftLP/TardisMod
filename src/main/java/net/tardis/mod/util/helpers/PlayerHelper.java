package net.tardis.mod.util.helpers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerHelper {

    public static void sendMessage(EntityPlayer player, String message, boolean hotBar) {
        if (!player.world.isRemote) {
            player.sendStatusMessage(new TextComponentTranslation(message), hotBar);
        }
    }

    /**
     * This method is client side only.
     *
     * @param player - The player
     * @return whether the player has alex arms
     */
    @SideOnly(Side.CLIENT)
    public static boolean hasSmallArms(AbstractClientPlayer player) {
        return player.getSkinType().equals("slim");
    }

}
