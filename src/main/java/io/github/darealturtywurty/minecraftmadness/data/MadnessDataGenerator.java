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
			JsonDataProvider blockstateProvider = new JsonDataProvider<>(generator, ResourceType.ASSETS, "blockstates",
					VariantBlockStateDefinition.CODEC);
			blockstates.forEach((id, codec) -> {
				blockstateProvider.with(id, codec);
			});
			generator.addProvider(blockstateProvider);
			generator.addProvider(new BlockModelDataGenerator(generator, event.getExistingFileHelper(), MODID));
			generator.addProvider(new ItemModelDataGenerator(generator, event.getExistingFileHelper(), MODID));
		}
	}

	static void basicBlockstate(Map<ResourceLocation, VariantBlockStateDefinition> blockstates,
			RegistryObject<Block> block) {
		blockstates.put(block.getId(), variantBuilder().withVariant("",
				new PartDefinition(new ResourceLocation(MODID, "block/" + block.getId().getPath()), 0, 0)));
	}
}
