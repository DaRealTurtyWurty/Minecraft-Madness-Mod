package io.github.darealturtywurty.minecraftmadness.client.entity.render;

import io.github.darealturtywurty.minecraftmadness.MinecraftMadness;
import io.github.darealturtywurty.minecraftmadness.common.entities.SittableEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class SeatRenderer<T extends SittableEntity> extends EntityRenderer<T> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(MinecraftMadness.MODID, "");

	public SeatRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return TEXTURE;
	}
}
