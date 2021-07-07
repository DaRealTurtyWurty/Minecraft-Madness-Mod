package io.github.darealturtywurty.minecraftmadness.data.server;

import static io.github.darealturtywurty.minecraftmadness.MinecraftMadness.MODID;

import java.util.Optional;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.registry.Registry;

public abstract class RecipeDefinitions {

	private RecipeDefinitions() {
	}

	public static final Codec<Ingredient> INGREDIENT_CODEC = Codec.PASSTHROUGH.comapFlatMap(
			json -> DataResult.error("Deserializing of ingredients not implemented"),
			ingredient -> new Dynamic<JsonElement>(JsonOps.INSTANCE, ingredient.toJson()));

	@SuppressWarnings("deprecation")
	public static final Codec<ItemStack> ITEMSTACK_CODEC = RecordCodecBuilder.create(instance -> instance
			.group(Registry.ITEM.fieldOf("item").forGetter(ItemStack::getItem),
					Codec.INT.fieldOf("count").forGetter(ItemStack::getCount),
					CompoundNBT.CODEC.optionalFieldOf("tag").forGetter(stack -> Optional.ofNullable(stack.getTag())))
			.apply(instance, (item, count, optionalNBT) -> {
				ItemStack stack = new ItemStack(item, count);
				optionalNBT.ifPresent(stack::setTag);
				return stack;
			}));

	public static class ChoppingRecipeDefinition {
		public static final Codec<ChoppingRecipeDefinition> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(Codec.STRING.fieldOf("type").forGetter(ChoppingRecipeDefinition::getRecipeType),
						INGREDIENT_CODEC.fieldOf("input").forGetter(ChoppingRecipeDefinition::getInput),
						ITEMSTACK_CODEC.fieldOf("output").forGetter(ChoppingRecipeDefinition::getOutput))
				.apply(instance, ChoppingRecipeDefinition::new));

		private final String type;
		private final Ingredient input;
		private final ItemStack output;

		public ChoppingRecipeDefinition(Ingredient input, ItemStack output) {
			this.type = MODID + ":chopping";
			this.input = input;
			this.output = output;
		}

		public ChoppingRecipeDefinition(String type, Ingredient input, ItemStack output) {
			this.type = type;
			this.input = input;
			this.output = output;
		}

		public Ingredient getInput() {
			return this.input;
		}

		public ItemStack getOutput() {
			return this.output;
		}

		public String getRecipeType() {
			return this.type;
		}
	}
}
