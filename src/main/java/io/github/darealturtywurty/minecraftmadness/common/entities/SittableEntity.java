package io.github.darealturtywurty.minecraftmadness.common.entities;

import io.github.darealturtywurty.minecraftmadness.core.init.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SittableEntity extends Entity {

	private BlockState seat;

	public SittableEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public SittableEntity(World worldIn) {
		super(EntityInit.SEAT.get(), worldIn);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		super.tick();
		if (this.seat == null) {
			this.seat = this.getCommandSenderWorld().getBlockState(this.blockPosition());
			if (this.seat == null || this.seat.isAir()) {
				this.remove();
			}
		}
	}

	@Override
	protected void defineSynchedData() {

	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT compound) {

	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT compound) {

	}

	@Override
	protected boolean canRide(Entity entityIn) {
		return entityIn instanceof PlayerEntity;
	}

	@Override
	public void move(MoverType typeIn, Vector3d pos) {

	}

	@Override
	public boolean canCollideWith(Entity entity) {
		return false;
	}

	@Override
	public void push(Entity entityIn) {

	}

	@Override
	public boolean isPickable() {
		return false;
	}

	@Override
	protected void checkInsideBlocks() {

	}

	@Override
	public void playerTouch(PlayerEntity entityIn) {

	}

	@Override
	public float getPickRadius() {
		return 0.0f;
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public double getPassengersRidingOffset() {
		return 0.25D;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void positionRider(Entity passenger) {
		super.positionRider(passenger);
		final float blockRotation = this.seat.hasProperty(BlockStateProperties.HORIZONTAL_FACING)
				? this.seat.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()
				: 0.0f;
		if (this.hasPassenger(passenger)) {
			float f = 0.0F;
			float f1 = (float) ((this.removed ? (double) 0.01F : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());
			if (this.getPassengers().size() > 1) {
				int i = this.getPassengers().indexOf(passenger);
				if (i == 0) {
					f = 0.2F;
				} else {
					f = -0.6F;
				}
			}

			Vector3d vector3d = (new Vector3d((double) f, 0.0D, 0.0D))
					.yRot(-this.yRot * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
			passenger.setPos(this.getX() + vector3d.x, this.getY() + (double) f1, this.getZ() + vector3d.z);
			passenger.setYHeadRot(passenger.getYHeadRot() + blockRotation);
			this.applyYawToEntity(passenger);
		}
	}

	protected void applyYawToEntity(Entity entityToUpdate) {
		entityToUpdate.setYBodyRot(this.yRot);
		float f = MathHelper.wrapDegrees(entityToUpdate.yRot - this.yRot);
		float f1 = MathHelper.clamp(f, -75.0F, 75.0F);
		entityToUpdate.yRotO += f1 - f;
		entityToUpdate.yRot += f1 - f;
		entityToUpdate.setYHeadRot(entityToUpdate.yRot);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof SittableEntity))
			return false;
		return super.equals(object) && this.seat.equals(((SittableEntity) object).seat);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + this.seat.hashCode();
	}
}
