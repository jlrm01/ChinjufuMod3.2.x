package com.ayutaki.chinjufumod.init.seasons;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.ayutaki.chinjufumod.handlers.SoundsHandler;
import com.ayutaki.chinjufumod.init.ASDecoModKitchen;
import com.ayutaki.chinjufumod.init.ChinjufuModFoodBlocks;
import com.ayutaki.chinjufumod.init.ChinjufuModSeasons;
import com.ayutaki.chinjufumod.init.ChinjufuModSeasons2;
import com.ayutaki.chinjufumod.init.blocks.BlockFacingSapo;
import com.ayutaki.chinjufumod.util.CollisionHelper;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAkuNabe_boil extends BlockFacingSapo {

	private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.25, 0.0, 0.25, 0.75, 0.5, 0.75);
	private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.25, 0.0, 0.25, 0.75, 0.5, 0.75);
	private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.25, 0.0, 0.25, 0.75, 0.5, 0.75);
	private static final AxisAlignedBB BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.25, 0.0, 0.25, 0.75, 0.5, 0.75);
	private static final AxisAlignedBB[] BOUNDING_BOX = { BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST };

	public BlockAkuNabe_boil()  {
		super(Material.WOOD);

		/*寸胴・フライパン*/
		this.setSoundType(SoundType.METAL);
		this.setHardness(1.0F);
		this.setResistance(5.0F);
	}

	/*湯気のエフェクト*/
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {

		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		World par1World = world;
		int par2 = i;
		int par3 = j;
		int par4 = k;
		Random par5Random = random;

		if (world.getBlockState(new BlockPos(i, j - 1, k)).getBlock() == Blocks.LIT_FURNACE || world
				.getBlockState(new BlockPos(i, j - 1, k)).getBlock() == ASDecoModKitchen.LIT_KITSTOVE || world
				.getBlockState(new BlockPos(i, j - 1, k)).getBlock() == ASDecoModKitchen.LIT_KITOVEN) {

			for (int la = 0; la < 1; ++la) {
				double d0 = (double) ((float) par2 + 0.5F) + (double) (par5Random.nextFloat() - 0.5F) * 0.01D;
				double d1 = ((double) ((float) par3 + 0.5F) + (double) (par5Random.nextFloat() - 0.5F) * 0.01D) + 0.5D;
				double d2 = (double) ((float) par4 + 0.5F) + (double) (par5Random.nextFloat() - 0.5F) * 0.01D;
				double d3 = 0.12D;
				double d4 = 0.17D;
				par1World.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, d0 - d4 + 0.25, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
			}
		}

		/**効果音**/
		if (random.nextDouble() < 0.1D) {

			if (world.getBlockState(new BlockPos(i, j - 1, k)).getBlock() == Blocks.LIT_FURNACE || world
					.getBlockState(new BlockPos(i, j - 1, k)).getBlock() == ASDecoModKitchen.LIT_KITSTOVE || world
					.getBlockState(new BlockPos(i, j - 1, k)).getBlock() == ASDecoModKitchen.LIT_KITOVEN) {

				world.playSound(i, j, k, SoundsHandler.GUTSUGUTSU, SoundCategory.BLOCKS, 0.5F, 0.7F, false);
			}
		}
	}

	/*火元が無くなると冷める*/
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();

		world.scheduleUpdate(new BlockPos(i, j, k), this, this.tickRate(world));

	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {

		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();

		if (world.getBlockState(new BlockPos(i, j - 1, k)).getBlock() != Blocks.LIT_FURNACE && world
				.getBlockState(new BlockPos(i, j - 1, k)).getBlock() != ASDecoModKitchen.LIT_KITSTOVE && world
				.getBlockState(new BlockPos(i, j - 1, k)).getBlock() != ASDecoModKitchen.LIT_KITOVEN) {

			world.setBlockState(pos, ChinjufuModSeasons.AKUNABE_boil2cold
					.getDefaultState().withProperty(FACING, state.getValue(FACING)));
		}
		world.scheduleUpdate(new BlockPos(i, j, k), this, this.tickRate(world));
	}

	@Override
	public int tickRate(World world) {
		return 100;
	}

	/*草束の投入*/
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		ItemStack itemstack = playerIn.getHeldItem(hand);
		Item item = itemstack.getItem();

		if (item != Item.getItemFromBlock(ChinjufuModSeasons2.KUSATABA)) {
			return true;
		}

		else if (item == Item.getItemFromBlock(ChinjufuModSeasons2.KUSATABA)) {

			/*特定のアイテムを1個消費*/
			itemstack.shrink(1);
			worldIn.playSound(null, pos, SoundsHandler.WATER_SPLASH, SoundCategory.BLOCKS, 0.5F, 1.2F);
			return  worldIn.setBlockState(pos, ChinjufuModSeasons.ORIITONABE_full
					.getDefaultState().withProperty(FACING, state.getValue(FACING)));
		}
		return true;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

		EnumFacing facing = state.getValue(FACING);
		return BOUNDING_BOX[facing.getHorizontalIndex()];
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes,
			@Nullable Entity entityIn, boolean p_185477_7_) {

		EnumFacing facing = state.getValue(FACING);
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX[facing.getHorizontalIndex()]);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	/*ドロップ指定、ピックアップ指定*/
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return new ItemStack(ChinjufuModFoodBlocks.ZUNDOU).getItem();
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(ChinjufuModFoodBlocks.ZUNDOU);
	}

}
