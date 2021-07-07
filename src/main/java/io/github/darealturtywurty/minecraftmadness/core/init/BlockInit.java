package io.github.darealturtywurty.minecraftmadness.core.init;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import io.github.darealturtywurty.minecraftmadness.common.blocks.ArmchairBlock;
import io.github.darealturtywurty.minecraftmadness.common.blocks.BirdBathBlock;
import io.github.darealturtywurty.minecraftmadness.common.blocks.BlenderBlock;
import io.github.darealturtywurty.minecraftmadness.common.blocks.ChoppingBoardBlock;
import io.github.darealturtywurty.minecraftmadness.common.blocks.ChoppingBoardBlock.BoardType;
import io.github.darealturtywurty.minecraftmadness.common.blocks.CoffeeTableBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.WoodType;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class BlockInit {

	private BlockInit() {
		throw new IllegalAccessError("Attempted to construct initialization class!");
	}

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

	public static final Set<RegistryObject<Block>> ARMCHAIRS = Sets.newHashSet();

	public static final RegistryObject<BirdBathBlock> BIRD_BATH = registerBlock("bird_bath",
			() -> new BirdBathBlock(Properties.copy(Blocks.COBBLESTONE).noOcclusion()));

	public static final RegistryObject<CoffeeTableBlock> OAK_COFFEE_TABLE = registerBlock("oak_coffee_table",
			() -> new CoffeeTableBlock(Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));

	public static final RegistryObject<CoffeeTableBlock> DARK_OAK_COFFEE_TABLE = registerBlock("dark_oak_coffee_table",
			() -> new CoffeeTableBlock(Properties.copy(Blocks.DARK_OAK_PLANKS).noOcclusion()));

	public static final RegistryObject<CoffeeTableBlock> SPRUCE_COFFEE_TABLE = registerBlock("spruce_coffee_table",
			() -> new CoffeeTableBlock(Properties.copy(Blocks.SPRUCE_PLANKS).noOcclusion()));

	public static final RegistryObject<CoffeeTableBlock> BIRCH_COFFEE_TABLE = registerBlock("birch_coffee_table",
			() -> new CoffeeTableBlock(Properties.copy(Blocks.BIRCH_PLANKS).noOcclusion()));

	public static final RegistryObject<CoffeeTableBlock> ACACIA_COFFEE_TABLE = registerBlock("acacia_coffee_table",
			() -> new CoffeeTableBlock(Properties.copy(Blocks.ACACIA_PLANKS).noOcclusion()));

	public static final RegistryObject<CoffeeTableBlock> JUNGLE_COFFEE_TABLE = registerBlock("jungle_coffee_table",
			() -> new CoffeeTableBlock(Properties.copy(Blocks.JUNGLE_PLANKS).noOcclusion()));

	public static final RegistryObject<CoffeeTableBlock> WARPED_COFFEE_TABLE = registerBlock("warped_coffee_table",
			() -> new CoffeeTableBlock(Properties.copy(Blocks.WARPED_PLANKS).noOcclusion()));

	public static final RegistryObject<CoffeeTableBlock> CRIMSON_COFFEE_TABLE = registerBlock("crimson_coffee_table",
			() -> new CoffeeTableBlock(Properties.copy(Blocks.CRIMSON_PLANKS).noOcclusion()));

	public static final RegistryObject<ChoppingBoardBlock> OAK_CHOPPING_BOARD = registerBlock("oak_chopping_board",
			() -> new ChoppingBoardBlock(Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BoardType.WOOD));

	public static final RegistryObject<ChoppingBoardBlock> DARK_OAK_CHOPPING_BOARD = registerBlock("dark_oak_chopping_board",
			() -> new ChoppingBoardBlock(Properties.copy(Blocks.DARK_OAK_PLANKS).noOcclusion(), BoardType.WOOD));

	public static final RegistryObject<ChoppingBoardBlock> SPRUCE_CHOPPING_BOARD = registerBlock("spruce_chopping_board",
			() -> new ChoppingBoardBlock(Properties.copy(Blocks.SPRUCE_PLANKS).noOcclusion(), BoardType.WOOD));

	public static final RegistryObject<ChoppingBoardBlock> BIRCH_CHOPPING_BOARD = registerBlock("birch_chopping_board",
			() -> new ChoppingBoardBlock(Properties.copy(Blocks.BIRCH_PLANKS).noOcclusion(), BoardType.WOOD));

	public static final RegistryObject<ChoppingBoardBlock> ACACIA_CHOPPING_BOARD = registerBlock("acacia_chopping_board",
			() -> new ChoppingBoardBlock(Properties.copy(Blocks.ACACIA_PLANKS).noOcclusion(), BoardType.WOOD));

	public static final RegistryObject<ChoppingBoardBlock> JUNGLE_CHOPPING_BOARD = registerBlock("jungle_chopping_board",
			() -> new ChoppingBoardBlock(Properties.copy(Blocks.JUNGLE_PLANKS).noOcclusion(), BoardType.WOOD));

	public static final RegistryObject<ChoppingBoardBlock> WARPED_CHOPPING_BOARD = registerBlock("warped_chopping_board",
			() -> new ChoppingBoardBlock(Properties.copy(Blocks.WARPED_PLANKS).noOcclusion(), BoardType.WOOD));

	public static final RegistryObject<ChoppingBoardBlock> CRIMSON_CHOPPING_BOARD = registerBlock("crimson_chopping_board",
			() -> new ChoppingBoardBlock(Properties.copy(Blocks.CRIMSON_PLANKS).noOcclusion(), BoardType.WOOD));

	public static final RegistryObject<ChoppingBoardBlock> STONE_CHOPPING_BOARD = registerBlock("stone_chopping_board",
			() -> new ChoppingBoardBlock(Properties.copy(Blocks.SMOOTH_STONE).noOcclusion(), BoardType.STONE));

	public static final RegistryObject<BlenderBlock> BLENDER = registerBlock("blender",
			() -> new BlenderBlock(Properties.copy(Blocks.SMOOTH_STONE).noOcclusion()));

	public static void registerSpecialBlocks() {
		registerArmchairs();
	}

	static void registerArmchairs() {
		for (WoodType wood : WoodType.values().collect(Collectors.toList())) {
			for (DyeColor color : DyeColor.values()) {
				ARMCHAIRS.add(registerBlock(color.getSerializedName() + "_" + wood.name() + "_armchair",
						() -> new ArmchairBlock(Properties.copy(ForgeRegistries.BLOCKS.getValue(ForgeRegistries.BLOCKS
								.getEntries().stream().map(Entry::getValue).map(Block::getRegistryName)
								.filter(name -> name.equals(new ResourceLocation("minecraft", wood.name() + "_planks")))
								.collect(Collectors.toList()).get(0))))));
			}
		}
	}

	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
		return BLOCKS.register(name, block);
	}

	private static RegistryObject<Block> registerNormalBlock(String name, Properties properties) {
		return registerBlock(name, () -> new Block(properties));
	}
}
