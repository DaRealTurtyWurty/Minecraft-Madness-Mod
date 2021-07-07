package io.github.darealturtywurty.minecraftmadness.core.init;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import io.github.darealturtywurty.minecraftmadness.core.world.MadnessWorldType;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class WorldTypeInit {

	private WorldTypeInit() {
		throw new IllegalAccessError("Attempted to construct initialization class!");
	}

	public static final DeferredRegister<ForgeWorldType> WORLD_TYPES = DeferredRegister.create(ForgeRegistries.WORLD_TYPES,
			MODID);

	public static final RegistryObject<ForgeWorldType> MADNESS_WORLD_TYPE = WORLD_TYPES.register("madness",
			MadnessWorldType::new);
}
