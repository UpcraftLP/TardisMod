package net.tardis.mod.common.flightmode;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.tardis.mod.common.flightmode.capability.IFlightMode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityFlightMode implements IFlightMode {

    private EntityPlayer player;
    private boolean isInFlight;
    private int tardisEntityID;


    public CapabilityFlightMode() {
    }

    public CapabilityFlightMode(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public boolean isInFlight() {
        return isInFlight;
    }

    @Override
    public void setInFlight(boolean inFlight) {
        this.isInFlight = inFlight;
    }

    @Override
    public NBTTagCompound writeNBT() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setBoolean("isInFlight", isInFlight);
        nbtTagCompound.setInteger("tardisEntityID", tardisEntityID);
        return nbtTagCompound;
    }

    @Override
    public void readNBT(NBTTagCompound nbtTagCompound) {
        isInFlight = nbtTagCompound.getBoolean("isInFlight");
        tardisEntityID = nbtTagCompound.getInteger("tardisEntityID");
    }

    @Override
    public int getTardisEntityID() {
        return tardisEntityID;
    }

    @Override
    public void setTardisEntityID(int tardisEntityID) {
        this.tardisEntityID = tardisEntityID;
    }

    @Override
    public void sync() {
        //TODO Tell the client about updates to a players capabilities
    }

    @Override
    public void update() {

    }


    public static class FlightProvider implements ICapabilitySerializable<NBTTagCompound> {

        private IFlightMode capability;

        public FlightProvider(IFlightMode capability) {
            this.capability = capability;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return FlightModeHandler.CAPABILITY != null && capability == FlightModeHandler.CAPABILITY;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == FlightModeHandler.CAPABILITY ? FlightModeHandler.CAPABILITY.cast(this.capability) : null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return (NBTTagCompound) FlightModeHandler.CAPABILITY.getStorage().writeNBT(FlightModeHandler.CAPABILITY, this.capability, null);
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            FlightModeHandler.CAPABILITY.getStorage().readNBT(FlightModeHandler.CAPABILITY, this.capability, null, nbt);
        }
    }
}
