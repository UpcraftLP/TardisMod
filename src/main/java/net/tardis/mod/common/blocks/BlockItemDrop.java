package net.tardis.mod.common.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.tardis.mod.Tardis;

public class BlockItemDrop extends BlockBase {
	
	private Item item;
	private int count = 1;
	
	public BlockItemDrop(Item item) {
		this.item = item;
		this.setCreativeTab(Tardis.tab);
		this.setResistance(1F);
	}
	
	public BlockItemDrop(Item item, int count) {
		this(item);
		this.count = count;
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,int fortune) {
		drops.add(new ItemStack(item, count));
	}

}
