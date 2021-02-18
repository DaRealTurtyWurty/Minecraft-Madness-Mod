package io.github.darealturtywurty.minecraftmadness.client;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import io.github.darealturtywurty.minecraftmadness.core.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {
	@EventBusSubscriber(modid = MODID, bus = Bus.MOD, value = Dist.CLIENT)
	public static class ModEvents {

		@SubscribeEvent
		public static void clientSetup(final FMLClientSetupEvent event) {
			setRenderTypes();
			bindScreens();
			bindRenderers();
		}

		@SubscribeEvent
		public static void registerBlockColors(final ColorHandlerEvent.Block event) {
			final BlockColors blockColors = event.getBlockColors();
			blockColors.register(new IBlockColor() {
				@Override
				public int getColor(BlockState state, IBlockDisplayReader world, BlockPos pos, int tintIndex) {
					return BiomeColors.getWaterColor(world, pos);
				}
			}, BlockInit.BIRD_BATH.get());
		}

		@SubscribeEvent
		public static void registerItemColors(final ColorHandlerEvent.Item event) {
			final ItemColors itemColors = event.getItemColors();
			itemColors.register(new IItemColor() {
				@Override
				public int getColor(ItemStack stack, int tintIndex) {
					return stack.getOrCreateChildTag("BiomeWaterColor").contains("Color")
							? stack.getOrCreateChildTag("BiomeWaterColor").getInt("Color")
							: 0x3F76E4;
				}
			}, BlockInit.BIRD_BATH.get());
		}
	}

	@EventBusSubscriber(modid = MODID, bus = Bus.FORGE, value = Dist.CLIENT)
	public static class ForgeEvents {

	}

	static void setRenderTypes() {
		RenderTypeLookup.setRenderLayer(BlockInit.BIRD_BATH.get(), RenderType.getTranslucent());
	}

	static void bindScreens() {

	}

	static void bindRenderers() {

	}
}
