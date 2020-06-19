package colonelgerdauf.plustic.traits;

import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import slimeknights.tconstruct.library.traits.*;

public class Apocalypse extends AbstractTrait {
	public static final Apocalypse apocalypse = new Apocalypse();
	
	public Apocalypse() {
		super("apocalypse",0x3A2D7D);
	}
	
	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit && target.isEntityAlive()) {
			int amp = -1;
			PotionEffect potionEffect = target.getActivePotionEffect(MobEffects.WITHER);
			if (potionEffect != null) amp = potionEffect.getAmplifier();
			amp = Math.min(3, amp+1);
			target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 130, amp));
		}
	}
}
