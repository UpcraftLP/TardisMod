package net.tardis.mod.common.flightmode;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.tardis.mod.Tardis;
import net.tardis.mod.common.blocks.TBlocks;
import net.tardis.mod.common.dimensions.TDimensions;
import net.tardis.mod.common.entities.EntityTardis;
import net.tardis.mod.common.flightmode.capability.FlightStorage;
import net.tardis.mod.common.flightmode.capability.IFlightMode;
import net.tardis.mod.common.tileentity.TileEntityDoor;
import net.tardis.mod.common.tileentity.TileEntityTardis;
import net.tardis.mod.packets.MessageSteerTardis;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = Tardis.MODID)
public class FlightModeHandler {

    @CapabilityInject(IFlightMode.class)
    public static final Capability<IFlightMode> CAPABILITY = null;
    private static final ResourceLocation FLIGHT_ID = new ResourceLocation(Tardis.MODID, "flight");

    // ======= Server/Common =======

    @SubscribeEvent
    public static void livingUpdateEvent(LivingEvent.LivingUpdateEvent e) {
        if (e.getEntityLiving() instanceof EntityTardis) {
            EntityTardis tardis = (EntityTardis) e.getEntityLiving();

            if (tardis.ticksOnGround >= 20) {
                //Return player to interior

                EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(UUID.fromString(tardis.getPilot()));

                if (player != null && player.isSneaking()) {

                    World world = tardis.world;

                    WorldServer ws = DimensionManager.getWorld(TDimensions.TARDIS_ID);
                    TileEntityTardis tardisTile = (TileEntityTardis) ws.getTileEntity(tardis.getConsolePos());
                    world.setBlockState(tardis.getPosition(), TBlocks.tardis.getDefaultState());
                    world.setBlockState(tardis.getPosition().up(), tardisTile.getTopBlock());
                    tardisTile.setLocation(tardis.getPosition());
                    ((TileEntityDoor) world.getTileEntity(tardis.getPosition().up())).consolePos = tardis.getConsolePos();
                    BlockPos cPos = tardis.consolePos.west(3);
                    ForgeChunkManager.forceChunk(((TileEntityTardis) ws.getTileEntity(tardis.consolePos)).tardisLocTicket, world.getChunkFromBlockCoords(tardis.getPosition()).getPos());
                } else {
                    tardis.setDead();
                }
            }
        }
    }


    //Attaching the capability to the player
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof EntityPlayer) {
            event.addCapability(FLIGHT_ID, new CapabilityFlightMode.FlightProvider(new CapabilityFlightMode((EntityPlayer) event.getObject())));
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        NBTTagCompound nbt = (NBTTagCompound) FlightModeHandler.CAPABILITY.getStorage().writeNBT(FlightModeHandler.CAPABILITY, event.getOriginal().getCapability(FlightModeHandler.CAPABILITY, null), null);
        FlightModeHandler.CAPABILITY.getStorage().readNBT(FlightModeHandler.CAPABILITY, event.getEntityPlayer().getCapability(FlightModeHandler.CAPABILITY, null), null, nbt);
    }

    @SubscribeEvent
    public static void playerTrackingEvent(PlayerEvent.StartTracking event) {
        if (event.getEntityPlayer().getCapability(FlightModeHandler.CAPABILITY, null) != null) {
            event.getEntityPlayer().getCapability(FlightModeHandler.CAPABILITY, null).sync();
        }
    }


    //Register the capability
    public static void init() {
        CapabilityManager.INSTANCE.register(IFlightMode.class, new FlightStorage(), CapabilityFlightMode::new);
    }


    // ======= Client =======

    //Stops the player moving and instead moves the tardis entity
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void inputEvent(InputUpdateEvent e) {
        if (Minecraft.getMinecraft().player == null || !Minecraft.getMinecraft().player.hasCapability(FlightModeHandler.CAPABILITY, null) || !Minecraft.getMinecraft().player.getCapability(FlightModeHandler.CAPABILITY, null).isInFlight())
            return;

        IFlightMode capability = FlightModeHandler.get(e.getEntityPlayer());

        if (capability.isInFlight()) {
            MovementInput moveType = e.getMovementInput();

            //I should uses a switch case here but shh

            int tardis = capability.getTardisEntityID();

            //TODO yes, these do indeed error, I need to find a way to make a world reference lol

            if (moveType.jump) {
                Tardis.NETWORK.sendToServer(new MessageSteerTardis(EnumSteerTardis.UP, tardis));
            }

            if (moveType.forwardKeyDown) {
                Tardis.NETWORK.sendToServer(new MessageSteerTardis(EnumSteerTardis.FORWARD, tardis));
            }

            if (moveType.leftKeyDown) {
                Tardis.NETWORK.sendToServer(new MessageSteerTardis(EnumSteerTardis.LEFT, tardis));
            }

            if (moveType.rightKeyDown) {
                Tardis.NETWORK.sendToServer(new MessageSteerTardis(EnumSteerTardis.RIGHT, tardis));
            }

            if (moveType.sneak) {
                Tardis.NETWORK.sendToServer(new MessageSteerTardis(EnumSteerTardis.DOWN, tardis));
            }

            moveType.rightKeyDown = false;
            moveType.leftKeyDown = false;
            moveType.backKeyDown = false;
            moveType.jump = false;
            moveType.moveForward = 0.0F;
            moveType.sneak = false;
            moveType.moveStrafe = 0.0F;
        }
    }


    // ======= Utils =======

    public static IFlightMode get(EntityPlayer player) {
        if (player.hasCapability(CAPABILITY, null)) {
            return player.getCapability(CAPABILITY, null);
        }
        throw new IllegalStateException("Missing capability: " + player);
    }

}
