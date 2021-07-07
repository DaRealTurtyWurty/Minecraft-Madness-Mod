package io.github.darealturtywurty.minecraftmadness.common.blocks;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import io.github.darealturtywurty.minecraftmadness.common.entities.SittableEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ArmchairBlock extends HorizontalBlock {

	private static final VoxelShape BASE_SHAPE = Stream
			.of(Block.box(2, 10, 3, 3, 11, 12), Block.box(13, 10, 3, 14, 11, 12), Block.box(3, 5, 2, 13, 7, 3),
					Block.box(2, 5, 3, 14, 7, 14), Block.box(2, 7, 12, 14, 16, 14), Block.box(2, 0, 2, 3, 11, 3),
					Block.box(13, 0, 13, 14, 5, 14), Block.box(13, 0, 2, 14, 11, 3), Block.box(2, 0, 13, 3, 5, 14))
			.reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

	private static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);

	public ArmchairBlock(Properties builder) {
		super(builder);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
		runCalculation(BASE_SHAPE);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
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
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult hit) {
		List<SittableEntity> seats = worldIn.getEntitiesOfClass(SittableEntity.class,
				new AxisAlignedBB(pos, pos.offset(1, 1, 1)));
		if (!worldIn.isClientSide()) {
			if (!seats.isEmpty()) {
				for (SittableEntity seat : seats) {
					if (player.startRiding(seat)) {
						return ActionResultType.SUCCESS;
					}
				}
			} else {
				SittableEntity seat = new SittableEntity(worldIn);
				seat.absMoveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, state.getValue(FACING).toYRot(), 0.0f);
				worldIn.addFreshEntity(seat);
				player.startRiding(seat);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.SUCCESS;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		List<SittableEntity> seats = worldIn.getEntitiesOfClass(SittableEntity.class,
				new AxisAlignedBB(pos, pos.offset(1, 1, 1)));
		seats.forEach(Entity::remove);
		super.onRemove(state, worldIn, pos, newState, isMoving);
	}
}
