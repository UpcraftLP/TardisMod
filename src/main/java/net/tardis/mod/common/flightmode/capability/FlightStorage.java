package net.tardis.mod.common.flightmode.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class FlightStorage implements Capability.IStorage<IFlightMode> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IFlightMode> capability, IFlightMode instance, EnumFacing side) {
        return instance.writeNBT();
    }

    @Override
    public void readNBT(Capability<IFlightMode> capability, IFlightMode instance, EnumFacing side, NBTBase nbt) {
        instance.readNBT((NBTTagCompound) nbt);
    }
}
