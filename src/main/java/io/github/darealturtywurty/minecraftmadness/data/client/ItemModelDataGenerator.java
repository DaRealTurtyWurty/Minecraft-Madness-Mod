package io.github.darealturtywurty.minecraftmadness.data.client;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.LOGGER;
import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemModelDataGenerator extends ItemModelProvider {
	private final Set<Item> blacklist = new HashSet<>();

	public ItemModelDataGenerator(DataGenerator generator, ExistingFileHelper fileHelper, String modid) {
		super(generator, modid, fileHelper);
	}

	@Override
	protected void registerModels() {
		// this.blacklist.add(ItemInit.BLAH.get());
		for (ResourceLocation id : ForgeRegistries.ITEMS.getKeys()) {
			Item item = ForgeRegistries.ITEMS.getValue(id);
			if (item != null && MODID.equals(id.getNamespace()) && !this.blacklist.contains(item)) {
				if (item instanceof BlockItem) {
					this.defaultBlock(id, (BlockItem) item);
				} else {
					this.defaultItem(id, item);
				}
			}
		}
	}

	protected void defaultItem(ResourceLocation id, Item item) {
		this.withExistingParent(id.getPath(), "item/generated").texture("layer0",
				new ResourceLocation(id.getNamespace(), "item/" + id.getPath()));
		LOGGER.debug("Generated item model for: " + item.getRegistryName());
	}

	protected void defaultBlock(ResourceLocation id, BlockItem item) {
		this.getBuilder(id.getPath())
				.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath())));
		LOGGER.debug("Generated block item model for: " + item.getRegistryName());
	}
}
