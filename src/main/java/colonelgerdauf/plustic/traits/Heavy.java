package colonelgerdauf.plustic.traits;

import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import slimeknights.tconstruct.library.traits.*;

public class Heavy extends AbstractTrait {
	public static final Heavy heavy = new Heavy();
	
	public Heavy() {
		super("heavy_metal", 0x555555);
	}
	
	@Override
	public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical) {
		return newKnockback * 1.3f;
	}
	
	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit && target.isEntityAlive()) {
			target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120, 1));
		}
	}
}
