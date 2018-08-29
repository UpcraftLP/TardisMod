package net.tardis.mod.common.flightmode;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.tardis.mod.Tardis;
import net.tardis.mod.common.entities.EntityTardis;
import net.tardis.mod.common.flightmode.capability.FlightStorage;
import net.tardis.mod.common.flightmode.capability.IFlightMode;

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

            if (tardis.onGround) {
                //TODO Set onground ticks + 1
                //TODO If they are more than 20 and the player shifts, return to interior
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

            if (moveType.jump) {
                //TODO SEND TARDIS UP PACKET
            }

            if (moveType.forwardKeyDown) {
                //TODO SEND TARDIS FORWARD PACKET
            }

            if (moveType.leftKeyDown) {
                //TODO SEND TARDIS LEFT PACKET
            }

            if (moveType.rightKeyDown) {
                //TODO SEND TARDIS RIGHT PACKET
            }

            if (moveType.sneak) {
                //TODO SEND TARDIS DOWN PACKET
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
