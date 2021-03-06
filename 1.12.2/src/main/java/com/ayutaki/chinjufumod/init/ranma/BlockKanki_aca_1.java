package com.ayutaki.chinjufumod.init.ranma;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.ayutaki.chinjufumod.init.ASDecoModRanma;
import com.ayutaki.chinjufumod.init.blocks.BlockFacingSapo;
import com.ayutaki.chinjufumod.util.CollisionHelper;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockKanki_aca_1 extends BlockFacingSapo {

	public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

	private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.8125, 0.0, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.8125, 0.0, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.8125, 0.0, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.8125, 0.0, 0.0, 1.0, 1.0, 1.0);
	private static final AxisAlignedBB[] BOUNDING_BOX = { BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST };

	public BlockKanki_aca_1(Material material, String unlocalizedName) {
		super(material);

		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(unlocalizedName);

		this.setHardness(0.5F);
		this.setResistance(5.0F);
		this.setSoundType(SoundType.WOOD);

	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		EnumFacing facing = state.getValue(FACING);
		return BOUNDING_BOX[facing.getHorizontalIndex()];
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {

		EnumFacing facing = state.getValue(FACING);
		super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX[facing.getHorizontalIndex()]);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if (playerIn.isSneaking()) {
			worldIn.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F);
			return worldIn.setBlockState(pos, ASDecoModRanma.KANKI_aca.getDefaultState().withProperty(FACING, state.getValue(FACING)));
		}
		return false;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

			EnumFacing facing = (EnumFacing) state.getValue(FACING);
			IBlockState left_block = worldIn.getBlockState(pos.offset(facing.rotateYCCW()));
			IBlockState right_block = worldIn.getBlockState(pos.offset(facing.rotateY()));
			boolean left = left_block.getBlock() instanceof BlockKanki_aca_1 && left_block.getValue(FACING).equals(facing);
			boolean right = right_block.getBlock() instanceof BlockKanki_aca_1 && right_block.getValue(FACING).equals(facing);

			 if(right)
			{
				if(left)
				{
					return state.withProperty(TYPE, Type.BOTH);
				}
				else
				{
					return state.withProperty(TYPE, Type.RIGHT);
				}
			}
			else if(left)
			{
				if(right)
				{
					return state.withProperty(TYPE, Type.BOTH);
				}
				else
				{
					return state.withProperty(TYPE, Type.LEFT);
				}
			}
			return state.withProperty(TYPE, Type.DEFAULT);

	}

	@Override
	protected BlockStateContainer createBlockState() {

		return new BlockStateContainer(this, new IProperty[] { FACING, TYPE}) ;
	}


	public static enum Type implements IStringSerializable {

		DEFAULT, LEFT, RIGHT, BOTH ;

		@Override
		public String getName() {

			return this.toString().toLowerCase();
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {

		return new ItemStack(ASDecoModRanma.KANKI_aca).getItem();
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {

		return new ItemStack(ASDecoModRanma.KANKI_aca);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
	    return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
	    return false;
	}

}
