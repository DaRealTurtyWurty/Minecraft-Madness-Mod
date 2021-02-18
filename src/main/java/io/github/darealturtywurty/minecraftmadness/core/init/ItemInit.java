package io.github.darealturtywurty.minecraftmadness.core.init;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MADNESS_GROUP;
import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import java.util.HashSet;
import java.util.Set;

import io.github.darealturtywurty.minecraftmadness.common.items.BirdBathItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = MODID, bus = Bus.MOD)
public class ItemInit {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

	public static final RegistryObject<Item> BIRD_BATH_ITEM = ITEMS.register("bird_bath",
			() -> new BirdBathItem(BlockInit.BIRD_BATH.get(), new Item.Properties().group(MADNESS_GROUP)));

	@SubscribeEvent
	public static void registerBlockItems(final Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		final Set<RegistryObject<Block>> blacklist = new HashSet<>();
		blacklist.add(BlockInit.BIRD_BATH);

		BlockInit.BLOCKS.getEntries().stream().filter(block -> !blacklist.contains(block)).map(RegistryObject::get)
				.forEach(block -> {
					final Properties properties = new Properties().group(MADNESS_GROUP);
					final BlockItem item = new BlockItem(block, properties);
					item.setRegistryName(block.getRegistryName());
					registry.register(item);
				});
	}
}
