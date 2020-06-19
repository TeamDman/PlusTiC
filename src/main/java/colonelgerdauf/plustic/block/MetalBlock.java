package colonelgerdauf.plustic.block;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.library.*;

public class MetalBlock extends Block {
	public MetalBlock(String name) {
		super(Material.IRON);
		this.setHarvestLevel("pickaxe", -1);
		this.setHardness(5);
		this.setTranslationKey(name).setRegistryName(name);
		this.setCreativeTab(TinkerRegistry.tabGeneral);
	}
	
	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
		return true;
	}
}
