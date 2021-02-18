package io.github.darealturtywurty.minecraftmadness.core.init;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);
}
