package es.amadornes.techquake.lib.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class ExtendedRenderBlocks extends RenderBlocks {
	
	public ExtendedRenderBlocks() {
		super();
	}
	
	public ExtendedRenderBlocks(IBlockAccess world){
		super(world);
	}

	public boolean renderCustomBeaconBase(Block b, int x, int y, int z) {
		float f = 0.1875F;
		float pixel = 0.0625F;
		this.setOverrideBlockTexture(this.getBlockIcon(Block.glass));
		this.setRenderBounds(x, y + f, z, x + 1.0D, y + 1.0D, z + 1.0D);
		this.renderStandardBlock(b, x, y, z);
		this.renderAllFaces = true;
		this.setOverrideBlockTexture(this.getBlockIcon(Block.blockIron));
//		this.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, f - pixel, 1.0F);
//		this.renderStandardBlock(b, x, y, z);
		this.setRenderBounds(0.0F, f - pixel, 0.0F, 1.0F, f, 1.0F);
		this.renderStandardBlock(b, x, y, z);
		return true;
	}

}
