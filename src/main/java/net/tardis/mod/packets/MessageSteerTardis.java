package net.tardis.mod.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.tardis.mod.common.entities.EntityTardis;
import net.tardis.mod.common.flightmode.EnumSteerTardis;

public class MessageSteerTardis implements IMessage {

    public EnumSteerTardis enumSteerTardis;
    int tardisID, worldID;

    public MessageSteerTardis() {
    }

    public MessageSteerTardis(EnumSteerTardis enumSteerTardis, int tardisID, int worldID) {
        this.enumSteerTardis = enumSteerTardis;
        this.tardisID = tardisID;
        this.worldID = worldID;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        enumSteerTardis = EnumSteerTardis.valueOf(ByteBufUtils.readUTF8String(buf));
        tardisID = buf.readInt();
        worldID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, enumSteerTardis.name());
        buf.writeInt(tardisID);
        buf.writeInt(worldID);
    }

    public static class Handler implements IMessageHandler<MessageSteerTardis, IMessage> {

        public Handler() {
        }

        @Override
        public IMessage onMessage(MessageSteerTardis mes, MessageContext ctx) {

            MinecraftServer server = ctx.getServerHandler().player.getServer();
            Entity test = server.getWorld(mes.worldID).getEntityByID(mes.tardisID);

            if (test instanceof EntityTardis) {
                EntityTardis tardis = (EntityTardis) test;
                EnumSteerTardis direction = mes.enumSteerTardis;

                if (direction.equals(EnumSteerTardis.DOWN)) {
                    tardis.motionY = -1;
                }

                if (direction.equals(EnumSteerTardis.UP)) {
                    tardis.motionY = 1;
                }
            }

            return null;
        }
    }
}
