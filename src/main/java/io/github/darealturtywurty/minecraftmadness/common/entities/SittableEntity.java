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
			this.seat = this.getEntityWorld().getBlockState(this.getPosition());
			if (this.seat == null || this.seat.isAir(this.getEntityWorld(), this.getPosition())) {
				this.remove();
			}
		}
	}

	@Override
	protected void registerData() {

	}

	@Override
	protected void readAdditional(CompoundNBT compound) {

	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {

	}

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return entityIn instanceof PlayerEntity;
	}

	@Override
	public void move(MoverType typeIn, Vector3d pos) {

	}

	@Override
	public boolean canCollide(Entity entity) {
		return false;
	}

	@Override
	public void applyEntityCollision(Entity entityIn) {

	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	protected void doBlockCollisions() {

	}

	@Override
	public void onCollideWithPlayer(PlayerEntity entityIn) {

	}

	@Override
	public float getCollisionBorderSize() {
		return 0.0f;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public double getMountedYOffset() {
		return 0.25D;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updatePassenger(Entity passenger) {
		super.updatePassenger(passenger);
		final float blockRotation = this.seat.hasProperty(BlockStateProperties.HORIZONTAL_FACING)
				? this.seat.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalAngle()
				: 0.0f;
		if (this.isPassenger(passenger)) {
			float f = 0.0F;
			float f1 = (float) ((this.removed ? (double) 0.01F : this.getMountedYOffset()) + passenger.getYOffset());
			if (this.getPassengers().size() > 1) {
				int i = this.getPassengers().indexOf(passenger);
				if (i == 0) {
					f = 0.2F;
				} else {
					f = -0.6F;
				}
			}

			Vector3d vector3d = (new Vector3d((double) f, 0.0D, 0.0D))
					.rotateYaw(-this.rotationYaw * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
			passenger.setPosition(this.getPosX() + vector3d.x, this.getPosY() + (double) f1, this.getPosZ() + vector3d.z);
			passenger.setRotationYawHead(passenger.getRotationYawHead() + blockRotation);
			this.applyYawToEntity(passenger);
		}
	}

	protected void applyYawToEntity(Entity entityToUpdate) {
		entityToUpdate.setRenderYawOffset(this.rotationYaw);
		float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
		float f1 = MathHelper.clamp(f, -75.0F, 75.0F);
		entityToUpdate.prevRotationYaw += f1 - f;
		entityToUpdate.rotationYaw += f1 - f;
		entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
	}
}
