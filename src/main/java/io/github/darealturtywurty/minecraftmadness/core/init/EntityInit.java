package io.github.darealturtywurty.minecraftmadness.core.init;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import io.github.darealturtywurty.minecraftmadness.common.entities.SittableEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);

	public static final RegistryObject<EntityType<?>> SEAT = ENTITIES.register("seat",
			() -> EntityType.Builder.create(SittableEntity::new, EntityClassification.MISC).size(1f, 1f)
					.build(new ResourceLocation(MODID, "seat").toString()));
}
