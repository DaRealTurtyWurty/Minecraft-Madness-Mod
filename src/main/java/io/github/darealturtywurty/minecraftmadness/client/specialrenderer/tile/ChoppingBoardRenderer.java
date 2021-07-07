package io.github.darealturtywurty.minecraftmadness.client.specialrenderer.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.darealturtywurty.minecraftmadness.client.RenderingUtils;
import io.github.darealturtywurty.minecraftmadness.common.tileentities.ChoppingBoardTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.vector.Vector3f;

public class ChoppingBoardRenderer extends TileEntityRenderer<ChoppingBoardTileEntity> {

	public ChoppingBoardRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(ChoppingBoardTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		int durability = Math.round(10f - ((float) tileEntityIn.getDurability() / 10f));
		if (durability > 0) {
			tileEntityIn.getLevel().destroyBlockProgress(0, tileEntityIn.getBlockPos(), durability - 1);
		}
		
		matrixStackIn.pushPose();
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(
				tileEntityIn.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()));
		matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90));
		matrixStackIn.scale(0.4f, 0.4f, 0.4f);
		switch (tileEntityIn.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)) {
		case NORTH:
			matrixStackIn.translate(-1.25f, -1.25f, -0.2f);
			break;
		case SOUTH:
			matrixStackIn.translate(1.25f, 1.25f, -0.2f);
			break;
		case EAST:
			matrixStackIn.translate(1.25f, -1.25f, -0.2f);
			break;
		case WEST:
			matrixStackIn.translate(-1.25f, 1.25f, -0.2f);
			break;
		default:
			break;
		}
		RenderingUtils.renderItem(tileEntityIn.getItemInSlot(0), combinedLightIn, combinedOverlayIn, matrixStackIn,
				bufferIn);
		matrixStackIn.popPose();

		matrixStackIn.pushPose();
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(
				tileEntityIn.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()));
		matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90f));
		matrixStackIn.scale(0.3f, 0.3f, 0.3f);
		switch (tileEntityIn.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)) {
		case NORTH:
			matrixStackIn.translate(-1.95f, -0.9f, -0.2001f);
			break;
		case SOUTH:
			matrixStackIn.translate(0.55f, 1.6f, -0.2001f);
			break;
		case EAST:
			matrixStackIn.translate(0.55f, -0.9f, -0.2001f);
			break;
		case WEST:
			matrixStackIn.translate(-1.95f, 1.6f, -0.2001f);
			break;
		default:
			break;
		}
		RenderingUtils.renderItem(tileEntityIn.getItemInSlot(1), combinedLightIn, combinedOverlayIn, matrixStackIn,
				bufferIn);
		matrixStackIn.popPose();

		matrixStackIn.pushPose();
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(
				tileEntityIn.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()));
		matrixStackIn.scale(0.4f, 0.4f, 0.4f);
		matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(180f));
		switch (tileEntityIn.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)) {
		case NORTH:
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270f));
			matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(10f));
			matrixStackIn.translate(1.5f, -0.64f, 0.5f);
			break;
		case SOUTH:
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270f));
			matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(10f));
			matrixStackIn.translate(-1f, -0.15f, -0.64f);
			break;
		case EAST:
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90f));
			matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(10f));
			matrixStackIn.translate(-1f, -0.15f, 0.5f);
			break;
		case WEST:
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270f));
			matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(10f));
			matrixStackIn.translate(-1f, -0.15f, 0.5f);
			break;
		default:
			break;
		}
		RenderingUtils.renderItem(tileEntityIn.getItemInSlot(2), combinedLightIn, combinedOverlayIn, matrixStackIn,
				bufferIn);
		matrixStackIn.popPose();
	}
}
