package net.tardis.mod.common.flightmode;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
import net.tardis.mod.common.commands.CommandTardis;
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
        EntityLivingBase entityLiving = e.getEntityLiving();

        //Tardis
        if (entityLiving instanceof EntityTardis) {
            EntityTardis tardis = (EntityTardis) e.getEntityLiving();
            System.out.println(tardis.ticksOnGround);

            EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(UUID.fromString(tardis.getPilot()));
            WorldServer ws = DimensionManager.getWorld(TDimensions.TARDIS_ID);
            World world = tardis.world;


            tardis.rotationPitch = player.rotationPitch;
            tardis.rotationYaw = player.rotationYaw;
            tardis.rotationYawHead = player.rotationYawHead;

            //tardis.setRotation(player.rotationYaw, player.rotationPitch);

            if (player == null || !FlightModeHandler.get(player).isInFlight()) {
                TileEntityTardis tardisTile = (TileEntityTardis) ws.getTileEntity(tardis.getConsolePos());
                world.setBlockState(tardis.getPosition(), TBlocks.tardis.getDefaultState());
                if (tardisTile.getTopBlock() != null) {
                    world.setBlockState(tardis.getPosition().up(), tardisTile.getTopBlock());
                }

                tardisTile.setLocation(tardis.getPosition());
                ((TileEntityDoor) world.getTileEntity(tardis.getPosition().up())).consolePos = tardis.getConsolePos();

                ForgeChunkManager.forceChunk(((TileEntityTardis) ws.getTileEntity(tardis.consolePos)).tardisLocTicket, world.getChunkFromBlockCoords(tardis.getPosition()).getPos());
                tardis.setDead();
            }

            if (tardis.ticksOnGround >= 20) {
                //Return player to interior
                if (player != null) {
                    if (player.isSneaking()) {
                        FlightModeHandler.get(player).setInFlight(false);
                        TileEntityTardis tardisTile = (TileEntityTardis) ws.getTileEntity(tardis.getConsolePos());
                        world.setBlockState(tardis.getPosition(), TBlocks.tardis.getDefaultState());
                        world.setBlockState(tardis.getPosition().up(), tardisTile.getTopBlock());
                        tardisTile.setLocation(tardis.getPosition());
                        ((TileEntityDoor) world.getTileEntity(tardis.getPosition().up())).consolePos = tardis.getConsolePos();
                        BlockPos cPos = tardis.consolePos.west(3);
                        ForgeChunkManager.forceChunk(((TileEntityTardis) ws.getTileEntity(tardis.consolePos)).tardisLocTicket, world.getChunkFromBlockCoords(tardis.getPosition()).getPos());
                        tardis.setDead();
                    }
                }
            }
        }

        //Player
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            FlightModeHandler.get(player).update(player);
        }
    }

    //We want to make sure when the player logs in, that they are returned to the interior
    @SubscribeEvent
    public static void onPlayerLogin(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent e) {
        EntityPlayer player = e.player;
        IFlightMode capa = FlightModeHandler.get(player);
        if (capa.isInFlight() && player.dimension != TDimensions.TARDIS_ID) {
            CommandTardis.handleTeleport((EntityPlayerMP) player);
            capa.setInFlight(false);
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

            int worldID = capability.getWorldID();

            if (moveType.jump) {
                Tardis.NETWORK.sendToServer(new MessageSteerTardis(EnumSteerTardis.UP, tardis, worldID));
            }

            if (moveType.forwardKeyDown) {
                Tardis.NETWORK.sendToServer(new MessageSteerTardis(EnumSteerTardis.FORWARD, tardis, worldID));
            }

            if (moveType.leftKeyDown) {
                Tardis.NETWORK.sendToServer(new MessageSteerTardis(EnumSteerTardis.LEFT, tardis, worldID));
            }

            if (moveType.rightKeyDown) {
                Tardis.NETWORK.sendToServer(new MessageSteerTardis(EnumSteerTardis.RIGHT, tardis, worldID));
            }

            if (moveType.sneak) {
                Tardis.NETWORK.sendToServer(new MessageSteerTardis(EnumSteerTardis.DOWN, tardis, worldID));
            }

            if (moveType.backKeyDown) {
                Tardis.NETWORK.sendToServer(new MessageSteerTardis(EnumSteerTardis.BACK, tardis, worldID));
            }

            moveType.rightKeyDown = false;
            moveType.leftKeyDown = false;
            moveType.backKeyDown = false;
            moveType.jump = false;
            moveType.moveForward = 0.0F;
            // moveType.sneak = false;
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
