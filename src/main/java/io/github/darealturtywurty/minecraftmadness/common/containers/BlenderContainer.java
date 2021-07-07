package io.github.darealturtywurty.minecraftmadness.common.containers;

import io.github.darealturtywurty.minecraftmadness.common.tileentities.BlenderTileEntity;
import io.github.darealturtywurty.minecraftmadness.core.init.BlockInit;
import io.github.darealturtywurty.minecraftmadness.core.init.ContainerInit;
import io.github.darealturtywurty.minecraftmadness.core.util.syncdata.BlenderSyncData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BlenderContainer extends Container {

	private final IWorldPosCallable callable;
	public static BlenderTileEntity tile;
	public final IIntArray data;

	public BlenderContainer(int id, final PlayerInventory playerInventory, IItemHandler slots, BlockPos pos,
			IIntArray data) {
		super(ContainerInit.BLENDER.get(), id);
		this.callable = IWorldPosCallable.create(playerInventory.player.level, pos);
		this.data = data;

		final int slotSizePlus2 = 18;
		// Boiler Inventory
		this.addSlot(new SlotItemHandler(slots, 0, 8, 56));
		this.addSlot(new SlotItemHandler(slots, 1, 98, 56));
		this.addSlot(new SlotItemHandler(slots, 2, 134, 56));
		this.addSlot(new SlotItemHandler(slots, 3, 98, 56));
		this.addSlot(new SlotItemHandler(slots, 4, 134, 56));

		// Main Inventory
		final int startX = 8;
		final int startY = 84;
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * slotSizePlus2),
						startY + (row * slotSizePlus2)));
			}
		}

		// Hotbar
		int hotbarY = 142;
		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(playerInventory, column, startX + column * slotSizePlus2, hotbarY));
		}

		this.addDataSlots(data);
	}

	public static IContainerProvider getServerContainerProvider(BlenderTileEntity te, BlockPos activationPos) {
		tile = te;
		return (id, playerInventory, serverPlayer) -> new BlenderContainer(id, playerInventory, te.getInventory(),
				activationPos, new BlenderSyncData(te));
	}

	public static BlenderContainer getClientContainer(int id, PlayerInventory playerInventory) {
		return new BlenderContainer(id, playerInventory, new ItemStackHandler(5), BlockPos.ZERO, new IntArray(2));
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		return stillValid(this.callable, player, BlockInit.BLENDER.get());
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
		ItemStack returnStack = ItemStack.EMPTY;
		final Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			final ItemStack slotStack = slot.getItem();
			returnStack = slotStack.copy();

			final int containerSlots = this.slots.size() - playerIn.inventory.items.size();
			if (index < containerSlots) {
				if (!moveItemStackTo(slotStack, containerSlots, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!moveItemStackTo(slotStack, 0, containerSlots, false)) {
				return ItemStack.EMPTY;
			}
			if (slotStack.getCount() == 0) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			if (slotStack.getCount() == returnStack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(playerIn, slotStack);
		}
		return returnStack;
	}

}
