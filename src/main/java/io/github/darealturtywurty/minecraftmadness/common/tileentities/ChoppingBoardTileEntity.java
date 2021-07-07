package io.github.darealturtywurty.minecraftmadness.common.tileentities;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import io.github.darealturtywurty.minecraftmadness.common.blocks.ChoppingBoardBlock;
import io.github.darealturtywurty.minecraftmadness.common.blocks.ChoppingBoardBlock.BoardType;
import io.github.darealturtywurty.minecraftmadness.core.init.RecipeInit;
import io.github.darealturtywurty.minecraftmadness.core.init.TileEntityInit;
import io.github.darealturtywurty.minecraftmadness.core.recipes.ChoppingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.BlockFlags;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class ChoppingBoardTileEntity extends InventoryTile {

	private int durability = 100;

	public ChoppingBoardTileEntity(TileEntityType<?> tileEntityTypeIn, int size) {
		super(tileEntityTypeIn, size);
	}

	public ChoppingBoardTileEntity() {
		super(TileEntityInit.CHOPPING_BOARD.get(), 3);
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		this.durability = nbt.getInt("Durability");
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
		compound.putInt("Durability", this.durability);
		return compound;
	}

	public int getDurability() {
		return this.durability;
	}

	public void decreaseDurability() {
		if (this.getBlockState().getValue(ChoppingBoardBlock.BOARD_TYPE) == BoardType.WOOD) {
			this.durability--;
			this.setChanged();
			this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), BlockFlags.BLOCK_UPDATE);
			if (this.durability <= 0) {
				this.level.setBlock(this.worldPosition, Blocks.AIR.defaultBlockState(), BlockFlags.BLOCK_UPDATE);
			}
		}
	}

	public boolean isChoppable(ItemStack stack) {
		Set<IRecipe<RecipeWrapper>> recipes = findRecipesByType(RecipeInit.CHOPPING_TYPE, this.level);
		for (IRecipe<RecipeWrapper> iRecipe : recipes) {
			ChoppingRecipe recipe = (ChoppingRecipe) iRecipe;
			if (recipe.getInput().test(stack) || stack.getItem() == Items.CAKE) {
				return true;
			}
		}
		return false;
	}

	@Override
	public IItemHandlerModifiable createHandler() {
		return new ItemStackHandler(this.size) {
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), BlockFlags.BLOCK_UPDATE);
				return super.insertItem(slot, stack, simulate);
			}

			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate) {
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), BlockFlags.BLOCK_UPDATE);
				return super.extractItem(slot, amount, simulate);
			}

			@Override
			public int getSlotLimit(int slot) {
				return slot == 1 ? 64 : 1;
			}
		};
	}

	@Nullable
	public ChoppingRecipe getChopped(ItemStack stack) {
		if (stack == null) {
			return null;
		}

		Set<IRecipe<RecipeWrapper>> recipes = findRecipesByType(RecipeInit.CHOPPING_TYPE, this.level);
		for (IRecipe<RecipeWrapper> iRecipe : recipes) {
			ChoppingRecipe recipe = (ChoppingRecipe) iRecipe;
			if (recipe.getInput().test(stack)) {
				return recipe;
			}
		}

		return null;
	}

	public static <T extends IInventory> Set<IRecipe<T>> findRecipesByType(IRecipeType<? extends IRecipe<T>> typeIn,
			World world) {
		return world != null
				? world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == typeIn)
						.map(IRecipe.class::cast).collect(Collectors.toSet())
				: Collections.emptySet();
	}

	public static Set<ItemStack> getAllRecipeInputs(IRecipeType<?> typeIn, World worldIn) {
		Set<ItemStack> inputs = new HashSet<>();
		Set<IRecipe<?>> recipes = findRecipesByType(typeIn, worldIn);
		for (IRecipe<?> recipe : recipes) {
			NonNullList<Ingredient> ingredients = recipe.getIngredients();
			ingredients.forEach(ingredient -> inputs.addAll(Arrays.asList(ingredient.getItems())));
		}
		return inputs;
	}
}
