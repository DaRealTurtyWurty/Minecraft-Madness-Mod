package io.github.darealturtywurty.minecraftmadness.data.client;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockModelDataGenerator extends BlockModelProvider {

	public BlockModelDataGenerator(DataGenerator generator, ExistingFileHelper fileHelper, String modid) {
		super(generator, modid, fileHelper);
	}

	@Override
	protected void registerModels() {

	}
}
