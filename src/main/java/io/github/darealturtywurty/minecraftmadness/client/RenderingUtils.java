package io.github.darealturtywurty.minecraftmadness.client;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;

public final class RenderingUtils {

	private RenderingUtils() {
		throw new IllegalAccessError("Attempted to construct utility class!");
	}

	public static void renderItem(ItemStack itemStackIn, int combinedLightIn, int combinedOverlayIn,
			MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn) {
		Minecraft.getInstance().getItemRenderer().renderStatic(itemStackIn, TransformType.FIXED, combinedLightIn,
				combinedOverlayIn, matrixStackIn, bufferIn);
	}
}
