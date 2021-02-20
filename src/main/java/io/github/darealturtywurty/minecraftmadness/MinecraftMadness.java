package io.github.darealturtywurty.minecraftmadness;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.darealturtywurty.minecraftmadness.core.init.BiomeInit;
import io.github.darealturtywurty.minecraftmadness.core.init.BlockInit;
import io.github.darealturtywurty.minecraftmadness.core.init.ContainerInit;
import io.github.darealturtywurty.minecraftmadness.core.init.EntityInit;
import io.github.darealturtywurty.minecraftmadness.core.init.ItemInit;
import io.github.darealturtywurty.minecraftmadness.core.init.TileEntityInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MinecraftMadness.MODID)
public class MinecraftMadness {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "minecraftmadness";
	public static final ItemGroup MADNESS_GROUP = new ItemGroup(MODID) {
		@Override
		public ItemStack createIcon() {
			return BlockInit.BIRD_BATH.get().asItem().getDefaultInstance();
		}
	};

	public MinecraftMadness() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::setup);

		ItemInit.ITEMS.register(bus);
		BlockInit.registerArmchairs();
		BlockInit.BLOCKS.register(bus);
		TileEntityInit.TILE_ENTITIES.register(bus);
		ContainerInit.CONTAINERS.register(bus);
		EntityInit.ENTITIES.register(bus);
		BiomeInit.BIOMES.register(bus);
	}

	void setup(FMLCommonSetupEvent event) {

	}
}
