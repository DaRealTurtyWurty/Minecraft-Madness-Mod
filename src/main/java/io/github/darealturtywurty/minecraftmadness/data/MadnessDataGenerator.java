package io.github.darealturtywurty.minecraftmadness.data;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.LOGGER;
import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;
import static io.github.darealturtywurty.minecraftmadness.data.client.BlockStateDefinitions.variantBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.github.darealturtywurty.minecraftmadness.core.init.BlockInit;
import io.github.darealturtywurty.minecraftmadness.core.init.ItemInit;
import io.github.darealturtywurty.minecraftmadness.data.JsonDataProvider.ResourceType;
import io.github.darealturtywurty.minecraftmadness.data.client.BlockStateDefinitions.PartDefinition;
import io.github.darealturtywurty.minecraftmadness.data.client.BlockStateDefinitions.VariantBlockStateDefinition;
import io.github.darealturtywurty.minecraftmadness.data.client.LanguagesDataGenerator;
import io.github.darealturtywurty.minecraftmadness.data.client.SimpleModel;
import io.github.darealturtywurty.minecraftmadness.data.server.RecipeDefinitions.ChoppingRecipeDefinition;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = MODID, bus = Bus.MOD)
public final class MadnessDataGenerator {

	private MadnessDataGenerator() {
		throw new IllegalAccessError("Attempted to construct event bus subscriber!");
	}

	@SubscribeEvent
	public static void gatherData(final GatherDataEvent event) {
		LOGGER.debug("Starting datagen!");
		DataGenerator generator = event.getGenerator();
		if (event.includeServer()) {
			LOGGER.debug("Server datagen!");

			final Map<ResourceLocation, ChoppingRecipeDefinition> choppedRecipes = new HashMap<>();
			JsonDataProvider<ChoppingRecipeDefinition> choppedRecipeGen = new JsonDataProvider<>(generator,
					ResourceType.DATA, "recipes/chopping", ChoppingRecipeDefinition.CODEC);
			choppingRecipes(choppedRecipes);
			choppedRecipes.forEach(choppedRecipeGen::with);
			generator.addProvider(choppedRecipeGen);
		}

		if (event.includeClient()) {
			LOGGER.debug("Client datagen!");
			final Map<ResourceLocation, VariantBlockStateDefinition> blockstates = new HashMap<>();
			final Map<ResourceLocation, SimpleModel> itemModels = new HashMap<>();
			LanguagesDataGenerator languageProvider = new LanguagesDataGenerator(generator, MODID, "en_us");
			JsonDataProvider<SimpleModel> blockModelProvider = new JsonDataProvider<>(generator, ResourceType.ASSETS,
					"models/block", SimpleModel.CODEC);
			JsonDataProvider<VariantBlockStateDefinition> blockstateProvider = new JsonDataProvider<>(generator,
					ResourceType.ASSETS, "blockstates", VariantBlockStateDefinition.CODEC);
			JsonDataProvider<SimpleModel> itemModelProvider = new JsonDataProvider<>(generator, ResourceType.ASSETS,
					"models/item", SimpleModel.CODEC);

			basicBlockstate(blockstates, BlockInit.BIRD_BATH);
			coffeeTables(blockstates, blockModelProvider);
			armchairs(blockstates, languageProvider, blockModelProvider);
			choppingBoards(blockstates, blockModelProvider);

			blockstates.forEach(blockstateProvider::with);

			final Set<Item> itemModelBlacklist = new HashSet<>();
			registerItemModels(itemModels, itemModelBlacklist);
			itemModels.forEach(itemModelProvider::with);

			addProviders(generator, blockModelProvider, blockstateProvider, languageProvider, itemModelProvider);
		}
	}

	static void registerItemModels(final Map<ResourceLocation, SimpleModel> itemModels, Set<Item> blacklist) {
		for (ResourceLocation id : ForgeRegistries.ITEMS.getKeys()) {
			Item item = ForgeRegistries.ITEMS.getValue(id);
			if (item != null && MODID.equals(id.getNamespace()) && !blacklist.contains(item)) {
				if (item instanceof BlockItem) {
					defaultBlock(itemModels, id, (BlockItem) item);
				} else {
					defaultItem(itemModels, id, item);
				}
			}
		}
	}

	static void defaultItem(final Map<ResourceLocation, SimpleModel> itemModels, ResourceLocation id, Item item) {
		itemModels.put(id, SimpleModel.builder("item/generated").withTexture("layer0",
				new ResourceLocation(MODID, "items/" + id.getPath()).toString()));
		LOGGER.debug("Generated item model for: {}", item.getRegistryName());
	}

	static void defaultBlock(final Map<ResourceLocation, SimpleModel> itemModels, ResourceLocation id, BlockItem item) {
		itemModels.put(id, SimpleModel.builder(new ResourceLocation(MODID, "block/" + id.getPath()).toString()));
		LOGGER.debug("Generated block item model for: {}", item.getRegistryName());
	}

	static void basicBlockstate(Map<ResourceLocation, VariantBlockStateDefinition> blockstates,
			RegistryObject<? extends Block> block) {
		blockstates.put(block.getId(), variantBuilder().withVariant("",
				new PartDefinition(new ResourceLocation(MODID, "block/" + block.getId().getPath()), 0, 0)));
	}

	static void horizontalBlockstate(Map<ResourceLocation, VariantBlockStateDefinition> blockstates,
			RegistryObject<? extends Block> block) {
		ResourceLocation modelLoc = new ResourceLocation(MODID, "block/" + block.getId().getPath());
		blockstates.put(block.getId(),
				variantBuilder().withVariant("facing=north", new PartDefinition(modelLoc, 0, 0))
						.withVariant("facing=east", new PartDefinition(modelLoc, 0, 90))
						.withVariant("facing=south", new PartDefinition(modelLoc, 0, 180))
						.withVariant("facing=west", new PartDefinition(modelLoc, 0, 270)));
	}

	static void choppingBoardBlockstate(Map<ResourceLocation, VariantBlockStateDefinition> blockstates,
			RegistryObject<? extends Block> block) {
		ResourceLocation modelLoc = new ResourceLocation(MODID, "block/" + block.getId().getPath());
		blockstates.put(block.getId(),
				variantBuilder().withVariant("facing=north,board_type=wood", new PartDefinition(modelLoc, 0, 0))
						.withVariant("facing=east,board_type=wood", new PartDefinition(modelLoc, 0, 90))
						.withVariant("facing=south,board_type=wood", new PartDefinition(modelLoc, 0, 180))
						.withVariant("facing=west,board_type=wood", new PartDefinition(modelLoc, 0, 270))
						.withVariant("facing=north,board_type=stone", new PartDefinition(modelLoc, 0, 0))
						.withVariant("facing=east,board_type=stone", new PartDefinition(modelLoc, 0, 90))
						.withVariant("facing=south,board_type=stone", new PartDefinition(modelLoc, 0, 180))
						.withVariant("facing=west,board_type=stone", new PartDefinition(modelLoc, 0, 270)));
	}

	static void armchairs(Map<ResourceLocation, VariantBlockStateDefinition> blockstates, LanguagesDataGenerator languageGen,
			JsonDataProvider<SimpleModel> blockModelProvider) {
		for (RegistryObject<Block> armchair : BlockInit.ARMCHAIRS) {
			String[] blockNameWords = armchair.getId().getPath().split("_");
			horizontalBlockstate(blockstates, armchair);
			languageGen.addArmchairLang(armchair);
			if (blockNameWords.length == 3) {
				blockModelProvider.with(armchair.getId(),
						SimpleModel.builder(MODID + ":block/armchair")
								.withTexture("wool", "block/" + blockNameWords[0] + "_wool")
								.withTexture("planks", "block/" + blockNameWords[1] + "_planks"));
			} else if (blockNameWords.length == 4) {
				if ((blockNameWords[1] + "_" + blockNameWords[2]).equals("dark_oak")) {
					blockModelProvider.with(armchair.getId(),
							SimpleModel.builder(MODID + ":block/armchair")
									.withTexture("wool", "block/" + blockNameWords[0] + "_wool").withTexture("planks",
											"block/" + blockNameWords[1] + "_" + blockNameWords[2] + "_planks"));
				} else {
					blockModelProvider.with(armchair.getId(),
							SimpleModel.builder(MODID + ":block/armchair")
									.withTexture("wool", "block/" + blockNameWords[0] + "_" + blockNameWords[1] + "_wool")
									.withTexture("planks", "block/" + blockNameWords[2] + "_planks"));
				}
			} else if (blockNameWords.length == 5) {
				blockModelProvider.with(armchair.getId(),
						SimpleModel.builder(MODID + ":block/armchair")
								.withTexture("wool", "block/" + blockNameWords[0] + "_" + blockNameWords[1] + "_wool")
								.withTexture("planks", "block/" + blockNameWords[2] + "_" + blockNameWords[3] + "_planks"));
			}
		}
	}

	static void choppingBoards(Map<ResourceLocation, VariantBlockStateDefinition> blockstates,
			JsonDataProvider<SimpleModel> blockModelProvider) {
		choppingBoardBlockstate(blockstates, BlockInit.OAK_CHOPPING_BOARD);
		choppingBoardBlockstate(blockstates, BlockInit.DARK_OAK_CHOPPING_BOARD);
		choppingBoardBlockstate(blockstates, BlockInit.BIRCH_CHOPPING_BOARD);
		choppingBoardBlockstate(blockstates, BlockInit.SPRUCE_CHOPPING_BOARD);
		choppingBoardBlockstate(blockstates, BlockInit.ACACIA_CHOPPING_BOARD);
		choppingBoardBlockstate(blockstates, BlockInit.JUNGLE_CHOPPING_BOARD);
		choppingBoardBlockstate(blockstates, BlockInit.WARPED_CHOPPING_BOARD);
		choppingBoardBlockstate(blockstates, BlockInit.CRIMSON_CHOPPING_BOARD);

		blockModelProvider.with(BlockInit.OAK_CHOPPING_BOARD.getId(), SimpleModel.builder(MODID + ":block/chopping_board")
				.withTexture("planks", "block/oak_planks").withTexture("log", "block/oak_log"));

		blockModelProvider.with(BlockInit.DARK_OAK_CHOPPING_BOARD.getId(),
				SimpleModel.builder(MODID + ":block/chopping_board").withTexture("planks", "block/dark_oak_planks")
						.withTexture("log", "block/dark_oak_log"));

		blockModelProvider.with(BlockInit.BIRCH_CHOPPING_BOARD.getId(), SimpleModel.builder(MODID + ":block/chopping_board")
				.withTexture("planks", "block/birch_planks").withTexture("log", "block/birch_log"));

		blockModelProvider.with(BlockInit.SPRUCE_CHOPPING_BOARD.getId(), SimpleModel.builder(MODID + ":block/chopping_board")
				.withTexture("planks", "block/spruce_planks").withTexture("log", "block/spruce_log"));

		blockModelProvider.with(BlockInit.ACACIA_CHOPPING_BOARD.getId(), SimpleModel.builder(MODID + ":block/chopping_board")
				.withTexture("planks", "block/acacia_planks").withTexture("log", "block/acacia_log"));

		blockModelProvider.with(BlockInit.JUNGLE_CHOPPING_BOARD.getId(), SimpleModel.builder(MODID + ":block/chopping_board")
				.withTexture("planks", "block/jungle_planks").withTexture("log", "block/jungle_log"));

		blockModelProvider.with(BlockInit.WARPED_CHOPPING_BOARD.getId(), SimpleModel.builder(MODID + ":block/chopping_board")
				.withTexture("planks", "block/warped_planks").withTexture("log", "block/warped_stem"));

		blockModelProvider.with(BlockInit.CRIMSON_CHOPPING_BOARD.getId(),
				SimpleModel.builder(MODID + ":block/chopping_board").withTexture("planks", "block/crimson_planks")
						.withTexture("log", "block/crimson_stem"));
	}

	static void coffeeTables(Map<ResourceLocation, VariantBlockStateDefinition> blockstates,
			JsonDataProvider<SimpleModel> blockModelProvider) {
		basicBlockstate(blockstates, BlockInit.OAK_COFFEE_TABLE);
		basicBlockstate(blockstates, BlockInit.DARK_OAK_COFFEE_TABLE);
		basicBlockstate(blockstates, BlockInit.BIRCH_COFFEE_TABLE);
		basicBlockstate(blockstates, BlockInit.SPRUCE_COFFEE_TABLE);
		basicBlockstate(blockstates, BlockInit.ACACIA_COFFEE_TABLE);
		basicBlockstate(blockstates, BlockInit.JUNGLE_COFFEE_TABLE);
		basicBlockstate(blockstates, BlockInit.WARPED_COFFEE_TABLE);
		basicBlockstate(blockstates, BlockInit.CRIMSON_COFFEE_TABLE);

		blockModelProvider.with(BlockInit.OAK_COFFEE_TABLE.getId(), SimpleModel.builder(MODID + ":block/coffee_table")
				.withTexture("planks", "block/oak_planks").withTexture("log", "block/oak_log"));

		blockModelProvider.with(BlockInit.DARK_OAK_COFFEE_TABLE.getId(), SimpleModel.builder(MODID + ":block/coffee_table")
				.withTexture("planks", "block/dark_oak_planks").withTexture("log", "block/dark_oak_log"));

		blockModelProvider.with(BlockInit.BIRCH_COFFEE_TABLE.getId(), SimpleModel.builder(MODID + ":block/coffee_table")
				.withTexture("planks", "block/birch_planks").withTexture("log", "block/birch_log"));

		blockModelProvider.with(BlockInit.SPRUCE_COFFEE_TABLE.getId(), SimpleModel.builder(MODID + ":block/coffee_table")
				.withTexture("planks", "block/spruce_planks").withTexture("log", "block/spruce_log"));

		blockModelProvider.with(BlockInit.ACACIA_COFFEE_TABLE.getId(), SimpleModel.builder(MODID + ":block/coffee_table")
				.withTexture("planks", "block/acacia_planks").withTexture("log", "block/acacia_log"));

		blockModelProvider.with(BlockInit.JUNGLE_COFFEE_TABLE.getId(), SimpleModel.builder(MODID + ":block/coffee_table")
				.withTexture("planks", "block/jungle_planks").withTexture("log", "block/jungle_log"));

		blockModelProvider.with(BlockInit.WARPED_COFFEE_TABLE.getId(), SimpleModel.builder(MODID + ":block/coffee_table")
				.withTexture("planks", "block/warped_planks").withTexture("log", "block/warped_stem"));

		blockModelProvider.with(BlockInit.CRIMSON_COFFEE_TABLE.getId(), SimpleModel.builder(MODID + ":block/coffee_table")
				.withTexture("planks", "block/crimson_planks").withTexture("log", "block/crimson_stem"));
	}

	static void choppingRecipes(Map<ResourceLocation, ChoppingRecipeDefinition> choppedRecipes) {
		choppedRecipes.put(new ResourceLocation(MODID, "bread_to_chunks"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.BREAD), new ItemStack(ItemInit.BREAD_CHUNK.get(), 5)));

		choppedRecipes.put(new ResourceLocation(MODID, "apple_to_slices"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.APPLE), new ItemStack(ItemInit.APPLE_SLICE.get(), 4)));

		choppedRecipes.put(new ResourceLocation(MODID, "beef_to_strips"),
				new ChoppingRecipeDefinition(Ingredient.of(Items.BEEF), new ItemStack(ItemInit.BEEF_STRIP.get(), 3)));

		choppedRecipes.put(new ResourceLocation(MODID, "beetroot_to_slices"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.BEETROOT), new ItemStack(ItemInit.BEETROOT_SLICE.get(), 5)));

		choppedRecipes.put(new ResourceLocation(MODID, "carrot_to_slices"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.CARROT), new ItemStack(ItemInit.CARROT_SLICE.get(), 3)));

		choppedRecipes.put(new ResourceLocation(MODID, "chicken_to_strips"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.CHICKEN), new ItemStack(ItemInit.CHICKEN_STRIP.get(), 6)));

		choppedRecipes.put(new ResourceLocation(MODID, "cod_to_fillets"),
				new ChoppingRecipeDefinition(Ingredient.of(Items.COD), new ItemStack(ItemInit.COD_FILLET.get(), 2)));

		choppedRecipes.put(new ResourceLocation(MODID, "cooked_beef_to_fillets"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.COOKED_BEEF), new ItemStack(ItemInit.COOKED_BEEF_STRIP.get(), 3)));

		choppedRecipes.put(new ResourceLocation(MODID, "cooked_chicken_to_strips"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.COOKED_CHICKEN), new ItemStack(ItemInit.COOKED_CHICKEN_STRIP.get(), 6)));

		choppedRecipes.put(new ResourceLocation(MODID, "cooked_cod_to_fillets"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.COOKED_COD), new ItemStack(ItemInit.COOKED_COD_FILLET.get(), 2)));

		choppedRecipes.put(new ResourceLocation(MODID, "cooked_mutton_to_strips"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.COOKED_MUTTON), new ItemStack(ItemInit.COOKED_MUTTON_STRIP.get(), 3)));

		choppedRecipes.put(new ResourceLocation(MODID, "cooked_porkchops_to_bacon"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.COOKED_PORKCHOP), new ItemStack(ItemInit.COOKED_BACON.get(), 3)));

		choppedRecipes.put(new ResourceLocation(MODID, "cooked_rabbit_to_strips"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.COOKED_RABBIT), new ItemStack(ItemInit.COOKED_RABBIT_STRIP.get(), 6)));

		choppedRecipes.put(new ResourceLocation(MODID, "cooked_salmon_to_fillets"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.COOKED_SALMON), new ItemStack(ItemInit.COOKED_SALMON_FILLET.get(), 2)));

		choppedRecipes.put(new ResourceLocation(MODID, "enchanted_golden_apple_to_slices"),
				new ChoppingRecipeDefinition(Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE),
						new ItemStack(ItemInit.ENCHANTED_GOLDEN_APPLE_SLICE.get(), 4)));

		choppedRecipes.put(new ResourceLocation(MODID, "golden_apple_to_slices"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.GOLDEN_APPLE), new ItemStack(ItemInit.GOLDEN_APPLE_SLICE.get(), 4)));

		choppedRecipes.put(new ResourceLocation(MODID, "golden_carrot_to_slices"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.GOLDEN_CARROT), new ItemStack(ItemInit.GOLDEN_CARROT_SLICE.get(), 3)));

		choppedRecipes.put(new ResourceLocation(MODID, "mutton_to_strips"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.MUTTON), new ItemStack(ItemInit.MUTTON_STRIP.get(), 3)));

		choppedRecipes.put(new ResourceLocation(MODID, "porkchop_to_bacon"),
				new ChoppingRecipeDefinition(Ingredient.of(Items.PORKCHOP), new ItemStack(ItemInit.BACON.get(), 3)));

		choppedRecipes.put(new ResourceLocation(MODID, "potato_to_chips"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.POTATO), new ItemStack(ItemInit.POTATO_CHIP.get(), 8)));

		choppedRecipes.put(new ResourceLocation(MODID, "pufferfish_to_fillets"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.PUFFERFISH), new ItemStack(ItemInit.PUFFERFISH_FILLET.get(), 2)));

		choppedRecipes.put(new ResourceLocation(MODID, "pumpkin_pie_to_slices"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.PUMPKIN_PIE), new ItemStack(ItemInit.PUMPKIN_PIE_SLICE.get(), 4)));

		choppedRecipes.put(new ResourceLocation(MODID, "rabbit_to_strips"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.RABBIT), new ItemStack(ItemInit.RABBIT_STRIP.get(), 6)));

		choppedRecipes.put(new ResourceLocation(MODID, "salmon_to_fillets"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.SALMON), new ItemStack(ItemInit.SALMON_FILLET.get(), 2)));

		choppedRecipes.put(new ResourceLocation(MODID, "tropical_to_fillets"), new ChoppingRecipeDefinition(
				Ingredient.of(Items.TROPICAL_FISH), new ItemStack(ItemInit.TROPICAL_FILLET.get(), 2)));
	}

	static void addProviders(DataGenerator generator, IDataProvider... dataProviders) {
		for (IDataProvider provider : dataProviders)
			generator.addProvider(provider);
	}
}
