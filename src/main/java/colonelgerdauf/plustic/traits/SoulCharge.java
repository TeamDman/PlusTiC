package colonelgerdauf.plustic.traits;

import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.tslat.aoa3.library.*;
import net.tslat.aoa3.utils.*;
import slimeknights.tconstruct.library.traits.*;

public class SoulCharge extends AbstractTrait {
	public static final SoulCharge soulcharge = new SoulCharge();
	
	protected static final Object2IntMap<String> toolToCharge = Object2IntMaps.unmodifiable(
			new Object2IntOpenHashMap<>(
				new String[] {"pickaxe", "axe", "shovel"},
				new int[] {5, 2, 1}
				));
	
	public SoulCharge() {
		super("soulcharge", 0x29ffc2);
	}
	
	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
		if (!world.isRemote && wasEffective && player instanceof EntityPlayer) {
			PlayerUtil.addResourceToPlayer((EntityPlayer) player, Enums.Resources.SOUL, toolToCharge.getInt(state.getBlock().getHarvestTool(state)));
		}
	}
}
