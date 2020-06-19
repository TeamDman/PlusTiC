package colonelgerdauf.plustic.traits;

import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.shared.*;

public class BloodyMary extends AbstractTrait {
	public static final BloodyMary bloodymary = new BloodyMary();
	
	public BloodyMary() {
		super("bloodymary",0xFF0000);
	}
	
	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		newDamage += Math.pow(target.getMaxHealth()-target.getHealth(),0.6);
		return super.damage(tool, player, target, damage, newDamage, isCritical);
	}
	
	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit) {
			spillBlood(target.getEntityWorld(),target.posX,target.posY,target.posZ,0.23f);
		}
	}
	
	protected void spillBlood(World world, double x, double y, double z, float chance) {
		if (!world.isRemote && random.nextFloat() < chance) {
			EntityItem entity = new EntityItem(world, x, y, z, TinkerCommons.matSlimeBallBlood.copy());
			world.spawnEntity(entity);
		}
	}
}
