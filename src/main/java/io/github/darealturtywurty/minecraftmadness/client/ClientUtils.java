package io.github.darealturtywurty.minecraftmadness.client;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

public final class ClientUtils {

	private ClientUtils() {
		throw new IllegalAccessError("Attempted to construct utility class!");
	}

	@SuppressWarnings("resource")
	public static <T extends IInventory> Set<IRecipe<T>> findRecipesByType(IRecipeType<IRecipe<T>> typeIn) {
		ClientWorld world = Minecraft.getInstance().level;
		if (world == null)
			return Collections.emptySet();
		return world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == typeIn)
				.map(IRecipe.class::cast).collect(Collectors.toSet());
	}
}
