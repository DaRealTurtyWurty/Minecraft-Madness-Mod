package io.github.darealturtywurty.minecraftmadness.common.items;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColors;

public class BirdBathItem extends BlockItem {

	public BirdBathItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		if (entityIn != null) {
			stack.getOrCreateChildTag("BiomeWaterColor").putInt("Color",
					BiomeColors.getWaterColor(entityIn.getEntityWorld(), entityIn.getPosition()));
		} else {
			stack.getOrCreateChildTag("BiomeWaterColor").putInt("Color", 0x3F76E4);
		}
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		stack.getOrCreateChildTag("BiomeWaterColor").putInt("Color",
				BiomeColors.getWaterColor(entity.getEntityWorld(), entity.getPosition()));
		return super.onEntityItemUpdate(stack, entity);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return oldStack.getItem() != newStack.getItem();
	}
}
