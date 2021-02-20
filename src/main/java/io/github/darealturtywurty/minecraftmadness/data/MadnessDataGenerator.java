package io.github.darealturtywurty.minecraftmadness.data;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.LOGGER;
import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;
import static io.github.darealturtywurty.minecraftmadness.data.client.BlockStateDefinitions.variantBuilder;

import java.util.HashMap;
import java.util.Map;

import io.github.darealturtywurty.minecraftmadness.core.init.BlockInit;
import io.github.darealturtywurty.minecraftmadness.data.JsonDataProvider.ResourceType;
import io.github.darealturtywurty.minecraftmadness.data.client.BlockModelDataGenerator;
import io.github.darealturtywurty.minecraftmadness.data.client.BlockStateDefinitions.PartDefinition;
import io.github.darealturtywurty.minecraftmadness.data.client.BlockStateDefinitions.VariantBlockStateDefinition;
import io.github.darealturtywurty.minecraftmadness.data.client.ItemModelDataGenerator;
import io.github.darealturtywurty.minecraftmadness.data.client.LanguagesDataGenerator;
import io.github.darealturtywurty.minecraftmadness.data.client.SimpleModel;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = MODID, bus = Bus.MOD)
public class MadnessDataGenerator {

	@SubscribeEvent
	public static void gatherData(final GatherDataEvent event) {
		LOGGER.debug("Starting datagen!");
		DataGenerator generator = event.getGenerator();
		if (event.includeServer()) {
			LOGGER.debug("Server datagen!");
		}

		if (event.includeClient()) {
			LOGGER.debug("Client datagen!");
			Map<ResourceLocation, VariantBlockStateDefinition> blockstates = new HashMap<>();
			basicBlockstate(blockstates, BlockInit.BIRD_BATH);

			basicBlockstate(blockstates, BlockInit.OAK_COFFEE_TABLE);
			basicBlockstate(blockstates, BlockInit.DARK_OAK_COFFEE_TABLE);
			basicBlockstate(blockstates, BlockInit.BIRCH_COFFEE_TABLE);
			basicBlockstate(blockstates, BlockInit.SPRUCE_COFFEE_TABLE);
			basicBlockstate(blockstates, BlockInit.ACACIA_COFFEE_TABLE);
			basicBlockstate(blockstates, BlockInit.JUNGLE_COFFEE_TABLE);
			basicBlockstate(blockstates, BlockInit.WARPED_COFFEE_TABLE);
			basicBlockstate(blockstates, BlockInit.CRIMSON_COFFEE_TABLE);

			LanguagesDataGenerator languageGen = new LanguagesDataGenerator(generator, MODID, "en_us");

			JsonDataProvider blockModelProvider = new JsonDataProvider<>(generator, ResourceType.ASSETS, "models/block",
					SimpleModel.CODEC);

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
										.withTexture("wool",
												"block/" + blockNameWords[0] + "_" + blockNameWords[1] + "_wool")
										.withTexture("planks", "block/" + blockNameWords[2] + "_planks"));
					}
				} else if (blockNameWords.length == 5) {
					blockModelProvider.with(armchair.getId(), SimpleModel.builder(MODID + ":block/armchair")
							.withTexture("wool", "block/" + blockNameWords[0] + "_" + blockNameWords[1] + "_wool")
							.withTexture("planks", "block/" + blockNameWords[2] + "_" + blockNameWords[3] + "_planks"));
				}
			}

			JsonDataProvider blockstateProvider = new JsonDataProvider<>(generator, ResourceType.ASSETS, "blockstates",
					VariantBlockStateDefinition.CODEC);
			blockstates.forEach((id, codec) -> {
				blockstateProvider.with(id, codec);
			});

			generator.addProvider(blockModelProvider);
			generator.addProvider(blockstateProvider);
			generator.addProvider(languageGen);
			generator.addProvider(new BlockModelDataGenerator(generator, event.getExistingFileHelper(), MODID));
			generator.addProvider(new ItemModelDataGenerator(generator, event.getExistingFileHelper(), MODID));
		}
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
}
