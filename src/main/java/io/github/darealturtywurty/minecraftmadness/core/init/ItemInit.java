package io.github.darealturtywurty.minecraftmadness.core.init;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MADNESS_GROUP;
import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import java.util.HashSet;
import java.util.Set;

import io.github.darealturtywurty.minecraftmadness.common.items.BirdBathItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = MODID, bus = Bus.MOD)
public final class ItemInit {

	private ItemInit() {
		throw new IllegalAccessError("Attempted to construct initialization class!");
	}

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

	public static final RegistryObject<Item> BIRD_BATH_ITEM = ITEMS.register("bird_bath",
			() -> new BirdBathItem(BlockInit.BIRD_BATH.get(), new Item.Properties().tab(MADNESS_GROUP)));

	public static final RegistryObject<Item> KNIFE = ITEMS.register("knife",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)));

	public static final RegistryObject<Item> BREAD_CHUNK = ITEMS.register("bread_chunk", () -> new Item(new Item.Properties()
			.tab(MADNESS_GROUP).food(new Food.Builder().nutrition(1).saturationMod(0.12f).fast().build())));

	public static final RegistryObject<Item> APPLE_SLICE = ITEMS.register("apple_slice", () -> new Item(new Item.Properties()
			.tab(MADNESS_GROUP).food(new Food.Builder().nutrition(1).saturationMod(0.075f).fast().build())));

	public static final RegistryObject<Item> BEEF_STRIP = ITEMS.register("beef_strip", () -> new Item(new Item.Properties()
			.tab(MADNESS_GROUP).food(new Food.Builder().nutrition(1).saturationMod(0.1f).fast().meat().build())));

	public static final RegistryObject<Item> BEETROOT_SLICE = ITEMS.register("beetroot_slice",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(0).saturationMod(0.12f).fast().build())));

	public static final RegistryObject<Item> CARROT_SLICE = ITEMS.register("carrot_slice",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(1).saturationMod(0.2f).fast().build())));

	public static final RegistryObject<Item> CHICKEN_STRIP = ITEMS.register("chicken_strip",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP).food(new Food.Builder().nutrition(0).saturationMod(0.05f)
					.fast().meat().effect(() -> new EffectInstance(Effects.HUNGER, 100, 0), 0.05F).build())));

	public static final RegistryObject<Item> COD_FILLET = ITEMS.register("cod_fillet", () -> new Item(new Item.Properties()
			.tab(MADNESS_GROUP).food(new Food.Builder().nutrition(1).saturationMod(0.05f).fast().meat().build())));

	public static final RegistryObject<Item> COOKED_BEEF_STRIP = ITEMS.register("cooked_beef_strip",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(3).saturationMod(0.27f).fast().meat().build())));

	public static final RegistryObject<Item> COOKED_CHICKEN_STRIP = ITEMS.register("cooked_chicken_strip",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(1).saturationMod(0.1f).fast().meat().build())));

	public static final RegistryObject<Item> COOKED_COD_FILLET = ITEMS.register("cooked_cod_fillet",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(3).saturationMod(0.3f).fast().meat().build())));

	public static final RegistryObject<Item> COOKED_MUTTON_STRIP = ITEMS.register("cooked_mutton_strip",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(2).saturationMod(0.27f).fast().meat().build())));

	public static final RegistryObject<Item> COOKED_BACON = ITEMS.register("cooked_bacon",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(3).saturationMod(0.27f).fast().meat().build())));

	public static final RegistryObject<Item> COOKED_RABBIT_STRIP = ITEMS.register("cooked_rabbit_strip",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(1).saturationMod(0.1f).fast().meat().build())));

	public static final RegistryObject<Item> COOKED_SALMON_FILLET = ITEMS.register("cooked_salmon_fillet",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(3).saturationMod(0.4f).fast().meat().build())));

	public static final RegistryObject<Item> ENCHANTED_GOLDEN_APPLE_SLICE = ITEMS.register("enchanted_golden_apple_slice",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(1).saturationMod(0.2f).fast()
							.effect(() -> new EffectInstance(Effects.REGENERATION, 100, 1), 1.0F)
							.effect(() -> new EffectInstance(Effects.DAMAGE_RESISTANCE, 1500, 1), 1.0F)
							.effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 1500, 1), 1.0F)
							.effect(() -> new EffectInstance(Effects.ABSORPTION, 600, 1), 1.0F).alwaysEat().build())));

	public static final RegistryObject<Item> GOLDEN_APPLE_SLICE = ITEMS
			.register("golden_apple_slice",
					() -> new Item(new Item.Properties().tab(MADNESS_GROUP).food(new Food.Builder().nutrition(1)
							.saturationMod(0.2f).fast().effect(() -> new EffectInstance(Effects.REGENERATION, 25, 1), 1.0F)
							.effect(() -> new EffectInstance(Effects.ABSORPTION, 600, 1), 1.0F).alwaysEat().build())));

	public static final RegistryObject<Item> GOLDEN_CARROT_SLICE = ITEMS.register("golden_carrot_slice",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(2).saturationMod(0.4f).fast().build())));

	public static final RegistryObject<Item> MUTTON_STRIP = ITEMS.register("mutton_strip",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(1).saturationMod(0.075f).fast().meat().build())));

	public static final RegistryObject<Item> BACON = ITEMS.register("bacon", () -> new Item(new Item.Properties()
			.tab(MADNESS_GROUP).food(new Food.Builder().nutrition(1).saturationMod(0.1f).fast().meat().build())));

	public static final RegistryObject<Item> POTATO_CHIP = ITEMS.register("potato_chip", () -> new Item(new Item.Properties()
			.tab(MADNESS_GROUP).food(new Food.Builder().nutrition(0).saturationMod(0.0375f).fast().build())));

	public static final RegistryObject<Item> PUFFERFISH_FILLET = ITEMS.register("pufferfish_fillet",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(0).saturationMod(0.05f).fast()
							.effect(() -> new EffectInstance(Effects.POISON, 600, 1), 0.95F)
							.effect(() -> new EffectInstance(Effects.HUNGER, 150, 1), 0.95F)
							.effect(() -> new EffectInstance(Effects.CONFUSION, 150, 1), 0.95F).build())));

	public static final RegistryObject<Item> COOKED_PUFFERFISH_FILLET = ITEMS.register("cooked_pufferfish_fillet",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(3).saturationMod(0.15f).fast()
							.effect(() -> new EffectInstance(Effects.POISON, 300, 3), 0.05F)
							.effect(() -> new EffectInstance(Effects.HUNGER, 300, 3), 0.05F)
							.effect(() -> new EffectInstance(Effects.CONFUSION, 300, 3), 0.05F).build())));

	public static final RegistryObject<Item> PUMPKIN_PIE_SLICE = ITEMS.register("pumpkin_pie_slice",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(2).saturationMod(0.075f).fast().build())));

	public static final RegistryObject<Item> RABBIT_STRIP = ITEMS.register("rabbit_strip",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(0).saturationMod(0.05f).fast().meat().build())));

	public static final RegistryObject<Item> SALMON_FILLET = ITEMS.register("salmon_fillet",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(1).saturationMod(0.05f).fast().meat().build())));

	public static final RegistryObject<Item> TROPICAL_FILLET = ITEMS.register("tropical_fillet",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(0).saturationMod(0.05f).fast().meat().build())));

	public static final RegistryObject<Item> COOKED_TROPICAL_FILLET = ITEMS.register("cooked_tropical_fillet",
			() -> new Item(new Item.Properties().tab(MADNESS_GROUP)
					.food(new Food.Builder().nutrition(3).saturationMod(0.2f).fast().meat()
							.effect(() -> new EffectInstance(Effects.HEALTH_BOOST, 2400, 5), 0.2f)
							.effect(() -> new EffectInstance(Effects.POISON, 2400, 5), 0.00025f).build())));

	@SubscribeEvent
	public static void registerBlockItems(final Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		final Set<RegistryObject<? extends Block>> blacklist = new HashSet<>();
		blacklist.add(BlockInit.BIRD_BATH);

		BlockInit.BLOCKS.getEntries().stream().filter(block -> !blacklist.contains(block)).map(RegistryObject::get)
				.forEach(block -> {
					final Properties properties = new Properties().tab(MADNESS_GROUP);
					final BlockItem item = new BlockItem(block, properties);
					item.setRegistryName(block.getRegistryName());
					registry.register(item);
				});
	}
}
