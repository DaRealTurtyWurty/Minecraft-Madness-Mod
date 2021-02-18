package io.github.darealturtywurty.minecraftmadness.core.init;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import java.util.function.Supplier;

import io.github.darealturtywurty.minecraftmadness.common.blocks.BirdBathBlock;
import io.github.darealturtywurty.minecraftmadness.common.blocks.CoffeeTableBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

	public static final RegistryObject<Block> BIRD_BATH = registerBlock("bird_bath",
			() -> new BirdBathBlock(Properties.from(Blocks.COBBLESTONE).notSolid()));

	public static final RegistryObject<Block> OAK_COFFEE_TABLE = registerBlock("oak_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.OAK_PLANKS).notSolid()));

	public static final RegistryObject<Block> DARK_OAK_COFFEE_TABLE = registerBlock("dark_oak_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.DARK_OAK_PLANKS).notSolid()));

	public static final RegistryObject<Block> SPRUCE_COFFEE_TABLE = registerBlock("spruce_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.SPRUCE_PLANKS).notSolid()));

	public static final RegistryObject<Block> BIRCH_COFFEE_TABLE = registerBlock("birch_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.BIRCH_PLANKS).notSolid()));

	public static final RegistryObject<Block> ACACIA_COFFEE_TABLE = registerBlock("acacia_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.ACACIA_PLANKS).notSolid()));

	public static final RegistryObject<Block> JUNGLE_COFFEE_TABLE = registerBlock("jungle_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.JUNGLE_PLANKS).notSolid()));

	public static final RegistryObject<Block> WARPED_COFFEE_TABLE = registerBlock("warped_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.WARPED_PLANKS).notSolid()));

	public static final RegistryObject<Block> CRIMSON_COFFEE_TABLE = registerBlock("crimson_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.CRIMSON_PLANKS).notSolid()));

	private static RegistryObject<Block> registerBlock(String name, Supplier<Block> block) {
		return BLOCKS.register(name, block);
	}

	private static RegistryObject<Block> registerNormalBlock(String name, Properties properties) {
		return registerBlock(name, () -> new Block(properties));
	}
}
