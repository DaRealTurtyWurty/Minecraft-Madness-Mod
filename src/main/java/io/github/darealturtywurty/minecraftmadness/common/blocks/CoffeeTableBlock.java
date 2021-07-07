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

public class CoffeeTableBlock extends Block {

	private static final Optional<VoxelShape> SHAPE = Stream
			.of(Block.box(0, 10, 0, 16, 11, 16), Block.box(15, 0, 0, 16, 10, 1), Block.box(15, 0, 15, 16, 10, 16),
					Block.box(0, 0, 15, 1, 10, 16), Block.box(0, 0, 0, 1, 10, 1))
			.reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR));

	public CoffeeTableBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE.isPresent() ? SHAPE.get() : VoxelShapes.empty();
	}
}
