package net.tardis.mod.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.tardis.mod.common.flightmode.FlightModeHandler;
import net.tardis.mod.common.flightmode.capability.IFlightMode;

import java.util.UUID;

public class MessageUpdateFlight implements IMessage {

    private EntityPlayer player;
    private NBTTagCompound data;

    public MessageUpdateFlight() {
    }

    public MessageUpdateFlight(EntityPlayer player, NBTTagCompound data) {
        this.player = player;
        this.data = data;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, player.getGameProfile().getId().toString());
        ByteBufUtils.writeTag(buf, data);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        if (Minecraft.getMinecraft().player != null)
            player = Minecraft.getMinecraft().player.world.getPlayerEntityByUUID(UUID.fromString(ByteBufUtils.readUTF8String(buf)));
        if (player != null)
            data = ByteBufUtils.readTag(buf);
    }

    public static class Handler implements IMessageHandler<MessageUpdateFlight, IMessage> {

        @Override
        public IMessage onMessage(MessageUpdateFlight message, MessageContext ctx) {
            EntityPlayer player = message.player;
            if (player == null || !player.hasCapability(FlightModeHandler.CAPABILITY, null)) return null;
            IFlightMode handler = player.getCapability(FlightModeHandler.CAPABILITY, null);
            Minecraft.getMinecraft().addScheduledTask(() -> handler.readNBT(message.data));

            return null;
        }
    }
}
