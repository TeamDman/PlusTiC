package landmaster.plustic.traits;

import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.*;

public class NaturesBlessing extends AbstractTrait {
	public static final NaturesBlessing naturesblessing = new NaturesBlessing();
	
	public NaturesBlessing() {
		super("naturesblessing",0xBEFA5C);
	}
	
	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
		dropBread(player.getEntityWorld(), pos.getX(), pos.getY(), pos.getZ(), 0.005f);
	}
	
	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit && !target.isEntityAlive()) {
			dropBread(target.getEntityWorld(),target.posX,target.posY,target.posZ,0.05f);
			float rnd = random.nextFloat();
			if (rnd < 0.3f) player.heal(3.2f);
		}
	}
	
	protected void dropBread(World world, double x, double y, double z, float chance) {
		if (!world.isRemote && random.nextFloat() < chance) {
			EntityItem entity = new EntityItem(world, x, y, z, new ItemStack(Items.BREAD));
			world.spawnEntity(entity);
		}
	}
}
