package io.github.darealturtywurty.minecraftmadness.core.init;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import io.github.darealturtywurty.minecraftmadness.common.blocks.ArmchairBlock;
import io.github.darealturtywurty.minecraftmadness.common.blocks.BirdBathBlock;
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

public class BlockInit {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

	public static final Set<RegistryObject<Block>> ARMCHAIRS = Sets.newHashSet();

	public static final RegistryObject<BirdBathBlock> BIRD_BATH = registerBlock("bird_bath",
			() -> new BirdBathBlock(Properties.from(Blocks.COBBLESTONE).notSolid()));

	public static final RegistryObject<CoffeeTableBlock> OAK_COFFEE_TABLE = registerBlock("oak_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.OAK_PLANKS).notSolid()));

	public static final RegistryObject<CoffeeTableBlock> DARK_OAK_COFFEE_TABLE = registerBlock("dark_oak_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.DARK_OAK_PLANKS).notSolid()));

	public static final RegistryObject<CoffeeTableBlock> SPRUCE_COFFEE_TABLE = registerBlock("spruce_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.SPRUCE_PLANKS).notSolid()));

	public static final RegistryObject<CoffeeTableBlock> BIRCH_COFFEE_TABLE = registerBlock("birch_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.BIRCH_PLANKS).notSolid()));

	public static final RegistryObject<CoffeeTableBlock> ACACIA_COFFEE_TABLE = registerBlock("acacia_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.ACACIA_PLANKS).notSolid()));

	public static final RegistryObject<CoffeeTableBlock> JUNGLE_COFFEE_TABLE = registerBlock("jungle_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.JUNGLE_PLANKS).notSolid()));

	public static final RegistryObject<CoffeeTableBlock> WARPED_COFFEE_TABLE = registerBlock("warped_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.WARPED_PLANKS).notSolid()));

	public static final RegistryObject<CoffeeTableBlock> CRIMSON_COFFEE_TABLE = registerBlock("crimson_coffee_table",
			() -> new CoffeeTableBlock(Properties.from(Blocks.CRIMSON_PLANKS).notSolid()));

	public static void registerArmchairs() {
		for (WoodType wood : WoodType.getValues().collect(Collectors.toList())) {
			for (DyeColor color : DyeColor.values()) {
				ARMCHAIRS.add(registerBlock(color.getString() + "_" + wood.getName() + "_armchair",
						() -> new ArmchairBlock(Properties.from(ForgeRegistries.BLOCKS.getValue(ForgeRegistries.BLOCKS
								.getEntries().stream().map(Entry::getValue).map(Block::getRegistryName)
								.filter(name -> name.equals(new ResourceLocation("minecraft", wood.getName() + "_planks")))
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
