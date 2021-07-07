package io.github.darealturtywurty.minecraftmadness.core.recipes;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import javax.annotation.Nonnull;

import io.github.darealturtywurty.minecraftmadness.core.init.RecipeInit;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public interface IChoppingRecipe extends IRecipe<RecipeWrapper> {

	ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(MODID, "chopping");

	@Nonnull
	@Override
	default IRecipeType<?> getType() {
		return RecipeInit.CHOPPING_TYPE;
	}

	@Override
	default boolean canCraftInDimensions(int width, int height) {
		return false;
	}

	Ingredient getInput();
}
