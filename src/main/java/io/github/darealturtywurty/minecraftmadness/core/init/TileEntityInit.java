package io.github.darealturtywurty.minecraftmadness.core.init;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import io.github.darealturtywurty.minecraftmadness.common.tileentities.ChoppingBoardTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class TileEntityInit {

	private TileEntityInit() {
		throw new IllegalAccessError("Attempted to construct initialization class!");
	}

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister
			.create(ForgeRegistries.TILE_ENTITIES, MODID);

	public static final RegistryObject<TileEntityType<ChoppingBoardTileEntity>> CHOPPING_BOARD = registerTileEntity(
			"chopping_board", ChoppingBoardTileEntity::new,
			() -> getBlocksFromFilter(block -> block.getRegistryName().getPath().contains("chopping_board")));

	static <T extends Block> T[] getBlocksFromFilter(Predicate<T> filter) {
		List<T> blocks = new ArrayList<>();
		for (Block block : ForgeRegistries.BLOCKS) {
			if (filter.test((T) block)) {
				blocks.add((T) block);
			}
		}
		return (T[]) blocks.toArray(new Block[0]);
	}

	static <T extends Block> T[] getBlocksFromTag(ITag<T>... tags) {
		List<T> blocks = new ArrayList<>();
		for (ITag<T> tag : tags) {
			blocks.addAll(tag.getValues());
		}
		return (T[]) blocks.toArray(new Block[0]);
	}

	static <T extends TileEntity> RegistryObject<TileEntityType<T>> registerTileEntity(String name, Supplier<T> factory,
			Supplier<Block[]> validBlocks) {
		return TILE_ENTITIES.register(name, () -> TileEntityType.Builder.of(factory, validBlocks.get()).build(null));
	}
}
