package io.github.darealturtywurty.minecraftmadness.common.blocks;

import java.util.Optional;
import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class BirdBathBlock extends Block {

	private static final Optional<VoxelShape> SHAPE = Stream
			.of(Block.box(4, 0, 4, 12, 1, 12), Block.box(5, 1, 5, 11, 3, 11), Block.box(6, 3, 6, 10, 11, 10),
					Block.box(4, 12, 4, 12, 13, 12), Block.box(5, 11, 5, 11, 12, 11), Block.box(3, 13, 3, 13, 14, 13),
					Block.box(13, 14, 3, 14, 15, 13), Block.box(14, 15, 2, 15, 16, 14), Block.box(2, 14, 3, 3, 15, 13),
					Block.box(1, 15, 2, 2, 16, 14), Block.box(2, 14, 13, 14, 15, 14), Block.box(1, 15, 14, 15, 16, 15),
					Block.box(2, 14, 2, 14, 15, 3), Block.box(1, 15, 1, 15, 16, 2), Block.box(2, 14, 2, 14, 16, 14))
			.reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR));

	public BirdBathBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE.isPresent() ? SHAPE.get() : VoxelShapes.empty();
	}
}
