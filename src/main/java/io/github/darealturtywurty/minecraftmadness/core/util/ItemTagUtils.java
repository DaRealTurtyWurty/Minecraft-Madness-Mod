package io.github.darealturtywurty.minecraftmadness.core.util;

import static net.minecraft.tags.ItemTags.bind;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag.INamedTag;

public final class ItemTagUtils {

	public static final INamedTag<Item> KNIFE = bind("forge:knife");

	private ItemTagUtils() {
		throw new IllegalAccessError("Attempted to construct utility class.");
	}

	public static boolean containsTag(ItemStack stack, INamedTag<Item> tag) {
		return stack.getItem().getTags().contains(tag.getName());
	}
}
