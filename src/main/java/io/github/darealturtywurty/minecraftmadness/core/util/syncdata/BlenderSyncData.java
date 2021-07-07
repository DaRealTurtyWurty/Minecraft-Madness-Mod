package io.github.darealturtywurty.minecraftmadness.core.util.syncdata;

import io.github.darealturtywurty.minecraftmadness.common.tileentities.BlenderTileEntity;
import net.minecraft.util.IIntArray;

public class BlenderSyncData implements IIntArray {

	private final BlenderTileEntity te;

	public BlenderSyncData(BlenderTileEntity te) {
		this.te = te;
	}

	@Override
	public int get(int index) {
		switch (index) {
		case 0:
			return this.te.getRunningTime();
		case 1:
			return this.te.getMaxRunningTime();
		default:
			return 0;
		}
	}

	@Override
	public void set(int index, int value) {
		switch (index) {
		case 0:
			this.te.setRunningTime(value);
			break;
		case 1:
			this.te.setMaxRunningTime(value);
			break;
		default:
			break;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}
}
