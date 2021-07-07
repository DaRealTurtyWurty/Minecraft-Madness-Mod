package io.github.darealturtywurty.minecraftmadness.core.init;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import io.github.darealturtywurty.minecraftmadness.common.containers.BlenderContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ContainerInit {

	private ContainerInit() {
		throw new IllegalAccessError("Attempted to construct initialization class!");
	}

	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS,
			MODID);

	public static final RegistryObject<ContainerType<BlenderContainer>> BLENDER = CONTAINERS.register("blender",
			() -> new ContainerType<>(BlenderContainer::getClientContainer));
}
