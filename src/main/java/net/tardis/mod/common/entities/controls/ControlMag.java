package net.tardis.mod.common.entities.controls;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.tardis.mod.common.strings.TStrings;
import net.tardis.mod.common.tileentity.TileEntityTardis;
import net.tardis.mod.common.tileentity.consoles.TileEntityTardis01;
import net.tardis.mod.common.tileentity.consoles.TileEntityTardis02;
import net.tardis.mod.util.helpers.Helper;

public class ControlMag extends EntityControl{
	
	public static final int[] mags = {1, 10, 100};
	private int index = 0;
	
	public ControlMag(TileEntityTardis tardis) {
		super(tardis);
	}
	
	public ControlMag(World world) {
		super(world);
		this.setSize(0.0625F, 0.0625F);
	}

	@Override
	public Vec3d getOffset(TileEntityTardis tardis) {
		if(tardis.getClass() == TileEntityTardis01.class || tardis.getClass() == TileEntityTardis02.class) {
			return Helper.convertToPixels(-3.25, -2.5, -13.5);
		}
		return Helper.convertToPixels(-9, -2, 6.5);
	}

	@Override
	public void preformAction(EntityPlayer player) {
		TileEntityTardis tardis = ((TileEntityTardis)world.getTileEntity(getConsolePos()));
		if(!player.isSneaking()) {
			tardis.magnitude = tardis.magnitude == 1 ? tardis.magnitude = 10 : (tardis.magnitude == 10 ? tardis.magnitude = 100 : 1);
		}
		player.sendStatusMessage(new TextComponentString(new TextComponentTranslation(TStrings.MAGNITUDE).getFormattedText() + " " + tardis.magnitude), true);
	}

}
