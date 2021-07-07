package io.github.darealturtywurty.minecraftmadness.core.init;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import io.github.darealturtywurty.minecraftmadness.common.entities.SittableEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class EntityInit {

	private EntityInit() {
		throw new IllegalAccessError("Attempted to construct initialization class!");
	}

	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);

	public static final RegistryObject<EntityType<SittableEntity>> SEAT = ENTITIES.register("seat",
			() -> EntityType.Builder.<SittableEntity>of(SittableEntity::new, EntityClassification.MISC).sized(1f, 1f)
					.build(new ResourceLocation(MODID, "seat").toString()));
}
