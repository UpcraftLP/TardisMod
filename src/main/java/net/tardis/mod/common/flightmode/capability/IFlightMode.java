package net.tardis.mod.common.flightmode.capability;

import net.minecraft.nbt.NBTTagCompound;

public interface IFlightMode {

    boolean isInFlight();

    void setInFlight(boolean inFlight);

    NBTTagCompound writeNBT();

    void readNBT(NBTTagCompound nbtTagCompound);

    int getTardisEntityID();

    void setTardisEntityID(int tardisEntityID);

    void sync();

    void update();
}
