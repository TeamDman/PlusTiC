package colonelgerdauf.plustic.traits;

import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import slimeknights.tconstruct.library.traits.*;

public class NaturesPower extends AbstractTrait {
	public static final NaturesPower naturespower = new NaturesPower();
	
	public NaturesPower() {
		super("naturespower",0xFFFF00);
	}
	
	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit) {
			float rnd = random.nextFloat();
			if (rnd < 0.2 && target.isEntityAlive())
				target.setFire(3);
			else if (rnd < 0.4 && player.isEntityAlive())
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED,101));
			else if (rnd < 0.6 && player.isEntityAlive())
				player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH,101));
		}
	}
}
