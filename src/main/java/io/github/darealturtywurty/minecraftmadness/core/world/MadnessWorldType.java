package io.github.darealturtywurty.minecraftmadness.core.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.gen.settings.StructureSpreadSettings;
import net.minecraftforge.common.world.ForgeWorldType;

public class MadnessWorldType extends ForgeWorldType {

	public MadnessWorldType() {
		super(new MadnessChunkGenFactory());
	}

	private static class MadnessChunkGenFactory implements IBasicChunkGeneratorFactory {

		@Override
		public ChunkGenerator createChunkGenerator(Registry<Biome> biomeRegistry,
				Registry<DimensionSettings> dimensionSettingsRegistry, long seed) {
			Map<Structure<?>, StructureSeparationSettings> structures = new HashMap<>();
			structures.put(StructureFeatures.IGLOO.feature, new StructureSeparationSettings(1, 0, 5));
			structures.put(StructureFeatures.VILLAGE_SAVANNA.feature, new StructureSeparationSettings(1, 0, 5));
			structures.put(StructureFeatures.SWAMP_HUT.feature, new StructureSeparationSettings(1, 0, 5));
			DimensionStructuresSettings structSettings = new DimensionStructuresSettings(
					Optional.of(new StructureSpreadSettings(1, 0, 1)), structures);

			List<FlatLayerInfo> layerInfo = new ArrayList<>();
			layerInfo.add(new FlatLayerInfo(1, Blocks.ACACIA_PLANKS));
			layerInfo.add(new FlatLayerInfo(5, Blocks.ANDESITE));
			layerInfo.add(new FlatLayerInfo(7, Blocks.BROWN_WOOL));
			layerInfo.add(new FlatLayerInfo(1, Blocks.CAKE));

			FlatGenerationSettings genSettings = new FlatGenerationSettings(biomeRegistry, structSettings, layerInfo, true,
					true, Optional.of(() -> BiomeRegistry.PLAINS));
			return new FlatChunkGenerator(genSettings);
		}

	}
}
