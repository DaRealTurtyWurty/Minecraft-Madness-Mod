package io.github.darealturtywurty.minecraftmadness.common.blocks;

import java.util.EnumMap;
import java.util.Map;

import io.github.darealturtywurty.minecraftmadness.common.tileentities.ChoppingBoardTileEntity;
import io.github.darealturtywurty.minecraftmadness.common.tileentities.InventoryTile;
import io.github.darealturtywurty.minecraftmadness.core.init.TileEntityInit;
import io.github.darealturtywurty.minecraftmadness.core.recipes.ChoppingRecipe;
import io.github.darealturtywurty.minecraftmadness.core.util.ItemTagUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ChoppingBoardBlock extends HorizontalBlock {
	private static final VoxelShape BASE_SHAPE = Block.box(2, 0, 4, 14, 1, 12);
	private static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
	public static final EnumProperty<BoardType> BOARD_TYPE = EnumProperty.create("board_type", BoardType.class);

	public ChoppingBoardBlock(Properties builder, BoardType boardType) {
		super(builder);
		this.registerDefaultState(
				this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(BOARD_TYPE, boardType));
		runCalculation(BASE_SHAPE);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityInit.CHOPPING_BOARD.get().create();
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult hit) {
		ItemStack heldItem = player.getItemInHand(handIn);
		if (!worldIn.isClientSide) {
			TileEntity tile = worldIn.getBlockEntity(pos);
			if (tile instanceof ChoppingBoardTileEntity) {
				ChoppingBoardTileEntity choppingBoard = (ChoppingBoardTileEntity) tile;
				ItemStack toChop = choppingBoard.getItemInSlot(0);
				ItemStack chopped = choppingBoard.getItemInSlot(1);
				ItemStack knife = choppingBoard.getItemInSlot(2);
				if (choppingBoard.isChoppable(heldItem)) {
					heldItem.shrink(heldItem.getCount() - choppingBoard.insertItem(0, heldItem).getCount());
				} else if (ItemTagUtils.containsTag(heldItem, ItemTagUtils.KNIFE)) {
					if (!toChop.isEmpty()
							&& (chopped.isEmpty() || choppingBoard.getChopped(toChop).getResultItem().sameItem(chopped)
									|| chopped.getCount() < 64)) {
						ChoppingRecipe recipe = choppingBoard.getChopped(choppingBoard.extractItem(0));
						choppingBoard.insertItem(1, recipe.getResultItem());
						choppingBoard.decreaseDurability();
					} else {
						if (knife.isEmpty()) {
							heldItem.shrink(heldItem.getCount() - choppingBoard.insertItem(2, heldItem).getCount());
						}
					}
				} else if (!chopped.isEmpty()) {
					player.addItem(choppingBoard.extractItem(1));
				} else if (!knife.isEmpty()) {
					player.addItem(choppingBoard.extractItem(2));
				} else if (!toChop.isEmpty()) {
					player.addItem(choppingBoard.extractItem(0));
				}
			}
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, BOARD_TYPE);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES.get(state.getValue(FACING));
	}

	protected static VoxelShape calculateShapes(Direction to, VoxelShape shape) {
		VoxelShape[] buffer = new VoxelShape[] { shape, VoxelShapes.empty() };

		int times = (to.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;
		for (int i = 0; i < times; i++) {
			buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.or(buffer[1],
					VoxelShapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
			buffer[0] = buffer[1];
			buffer[1] = VoxelShapes.empty();
		}

		return buffer[0];
	}

	protected void runCalculation(VoxelShape shape) {
		for (Direction direction : Direction.values()) {
			SHAPES.put(direction, calculateShapes(direction, shape));
		}
	}

	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		TileEntity tile = worldIn.getBlockEntity(pos);
		if (tile instanceof InventoryTile) {
			InventoryTile choppingBoard = (InventoryTile) tile;
			for (int slotIndex = 0; slotIndex < choppingBoard.getInventory().getSlots(); slotIndex++) {
				InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(),
						choppingBoard.getItemInSlot(slotIndex));
			}
		}

		worldIn.destroyBlockProgress(0, pos, -1);

		if (state.hasTileEntity() && (!state.is(newState.getBlock()) || !newState.hasTileEntity())) {
			worldIn.removeBlockEntity(pos);
		}
	}

	public enum BoardType implements IStringSerializable {
		WOOD("wood"), STONE("stone");

		private String type;

		private BoardType(String type) {
			this.type = type;
		}

		@Override
		public String getSerializedName() {
			return this.type;
		}
	}
}
