package io.github.darealturtywurty.minecraftmadness.client;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import io.github.darealturtywurty.minecraftmadness.client.entity.render.SeatRenderer;
import io.github.darealturtywurty.minecraftmadness.client.specialrenderer.tile.ChoppingBoardRenderer;
import io.github.darealturtywurty.minecraftmadness.core.init.BlockInit;
import io.github.darealturtywurty.minecraftmadness.core.init.EntityInit;
import io.github.darealturtywurty.minecraftmadness.core.init.TileEntityInit;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class ClientEvents {
	
	private ClientEvents() {
		throw new IllegalAccessError("Attempted to construct container for client event subscribers!");
	}
	
	@EventBusSubscriber(modid = MODID, bus = Bus.MOD, value = Dist.CLIENT)
	public static final class ModEvents {
		
		private ModEvents() {
			throw new IllegalAccessError("Attempted to construct client event subscriber!");
		}

		@SubscribeEvent
		public static void clientSetup(final FMLClientSetupEvent event) {
			setRenderTypes();
			bindScreens();
			bindRenderers();
		}

		@SubscribeEvent
		public static void registerBlockColors(final ColorHandlerEvent.Block event) {
			final BlockColors blockColors = event.getBlockColors();
			blockColors.register((state, world, pos, tintIndex) -> BiomeColors.getAverageWaterColor(world, pos),
					BlockInit.BIRD_BATH.get());
		}

		@SubscribeEvent
		public static void registerItemColors(final ColorHandlerEvent.Item event) {
			final ItemColors itemColors = event.getItemColors();
			itemColors.register((stack, colour) -> stack.getOrCreateTagElement("BiomeWaterColor").contains("Color")
					? stack.getOrCreateTagElement("BiomeWaterColor").getInt("Color")
					: 0x3F76E4, BlockInit.BIRD_BATH.get());
		}
	}

	@EventBusSubscriber(modid = MODID, bus = Bus.FORGE, value = Dist.CLIENT)
	public static final class ForgeEvents {
		private ForgeEvents() {
			throw new IllegalAccessError("Attempted to construct client event subscriber!");
		}
	}

	static void setRenderTypes() {
		RenderTypeLookup.setRenderLayer(BlockInit.BIRD_BATH.get(), RenderType.translucent());
	}

	static void bindScreens() {

	}

	static void bindRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityInit.SEAT.get(), SeatRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TileEntityInit.CHOPPING_BOARD.get(), ChoppingBoardRenderer::new);
	}
}
