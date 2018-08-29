package net.tardis.mod.common.entities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.tardis.mod.client.EnumExterior;
import net.tardis.mod.common.blocks.TBlocks;
import net.tardis.mod.common.sounds.TSounds;
import net.tardis.mod.common.tileentity.TileEntityDoor;

public class EntityTardis extends EntityFlying {
	
	public static final DataParameter<NBTTagCompound> STATE = EntityDataManager.createKey(EntityTardis.class, DataSerializers.COMPOUND_TAG);

    public static final DataParameter<String> UUID = EntityDataManager.createKey(EntityTardis.class, DataSerializers.STRING);


	public BlockPos consolePos = BlockPos.ORIGIN;
	public int renderRotation = 0;
	public int ticksOnGround = 0;
	private int ticks = 0;
	
	public EntityTardis(World worldIn) {
		super(worldIn);
		this.setSize(1.2F, 2.5F);
	}
	
	public EntityTardis(World worldIn, Entity e) {
		this(worldIn);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(STATE, new NBTTagCompound());
        this.getDataManager().register(UUID, "I DONT HAVE ONE KILL ME");
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		tag.setLong("tPos", consolePos.toLong());
		tag.setInteger("state", this.getState());
        tag.setString("uuid", getPilot());
		super.writeEntityToNBT(tag);
	}

    public String getPilot() {
        return getDataManager().get(UUID);
    }

    public void setPilot(String uuid) {
        getDataManager().set(UUID, uuid);
    }

	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		super.readEntityFromNBT(tag);
		consolePos = BlockPos.fromLong(tag.getLong("tPos"));
		this.setState(tag.getInteger("state"));
        setPilot(tag.getString("uuid"));
	}
	
	public BlockPos getConsolePos() {
		return consolePos;
	}
	
	public void setConsolePos(BlockPos consolePos) {
		this.consolePos = consolePos.toImmutable();
	}
	
	public int getState() {
		return this.dataManager.get(STATE).getInteger("block");
	}
	
	public void setState(int state) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("block", state);
		this.dataManager.set(STATE, tag);
		this.dataManager.setDirty(STATE);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

        if (onGround) {
            ticksOnGround++;
		} else {
			ticksOnGround = 0;
		}

		if (world.isRemote) {
			if (!onGround) {
				if (ticksExisted % 25 == 0) {
					playFlightSound();
				}
			}
		}
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if(world.getBlockState(this.getPosition().down()).getMaterial() == Material.AIR) this.renderRotation += 4;
		if (this.renderRotation > 360) this.renderRotation = 0;
	}
	
	@Override
	public Entity getControllingPassenger() {
		if (this.getPassengers().size() > 0) return this.getPassengers().get(0);
		return null;
	}

	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		if (!world.isRemote) {
			world.setBlockState(this.getPosition(), TBlocks.tardis.getDefaultState());
			world.setBlockState(this.getPosition().up(), TBlocks.tardis_top.getDefaultState());
			((TileEntityDoor) world.getTileEntity(this.getPosition().up())).consolePos = this.getConsolePos().toImmutable();
		}
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return true;
	}
	
	@Override
	public void applyEntityCollision(Entity entityIn) {
		super.applyEntityCollision(entityIn);
		if (!world.isRemote) {
			if (getSpeed() > 0.15) {
				if (entityIn != this.getControllingPassenger()) {
					entityIn.attackEntityFrom(DamageSource.GENERIC, 10F);
					world.playSound(null, this.getPosition(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1F, 1F);
				}
			}
		}
	}
	
	public double getSpeed() {
		return (Math.abs(motionX) + Math.abs(motionY) + Math.abs(motionZ)) / 3;
	}

	public String getExterior() {
		for(EnumExterior e : EnumExterior.values()) {
			if(e.block == Block.getStateById(this.getState()).getBlock()) {
				return e.name();
			}
		}
		return EnumExterior.FIRST.name();
	}

	@SideOnly(Side.CLIENT)
	public void playFlightSound() {
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(TSounds.flyLoop, 1.0F));
	}

	
}
