package io.github.darealturtywurty.minecraftmadness.core.init;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import io.github.darealturtywurty.minecraftmadness.core.recipes.ChoppingRecipe;
import io.github.darealturtywurty.minecraftmadness.core.recipes.ChoppingRecipeSerializer;
import io.github.darealturtywurty.minecraftmadness.core.recipes.IChoppingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class RecipeInit {

	private RecipeInit() {
		throw new IllegalAccessError("Attempted to construct initialization class!");
	}

	public static final IRecipeSerializer<ChoppingRecipe> CHOPPING_RECIPE_SERIALIZER = new ChoppingRecipeSerializer();
	public static final IRecipeType<IChoppingRecipe> CHOPPING_TYPE = registerType(IChoppingRecipe.RECIPE_TYPE_ID);

	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
			.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);

	public static final RegistryObject<IRecipeSerializer<ChoppingRecipe>> CHOPPING_SERIALIZER = RECIPE_SERIALIZERS
			.register("chopping", () -> CHOPPING_RECIPE_SERIALIZER);

	static <T extends IRecipeType> T registerType(ResourceLocation recipeTypeId) {
		return (T) IRecipeType.register(recipeTypeId.toString());
	}
}
