package io.github.darealturtywurty.minecraftmadness.common.tileentities;

import net.minecraft.tileentity.TileEntityType;

public class BlenderTileEntity extends InventoryTile {

	private int runningTime, maxRunningTime;

	public BlenderTileEntity(TileEntityType<?> tileEntityTypeIn, int size) {
		super(tileEntityTypeIn, size);
	}

	@Override
	public void tick() {
		super.tick();
	}

	public int getRunningTime() {
		return this.runningTime;
	}

	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}

	public int getMaxRunningTime() {
		return this.maxRunningTime;
	}

	public void setMaxRunningTime(int maxRunningTime) {
		this.maxRunningTime = maxRunningTime;
	}
}
