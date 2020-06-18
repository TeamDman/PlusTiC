package landmaster.plustic.traits;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.traits.*;

public class Slashing extends AbstractTrait {
	public static final Slashing slashing = new Slashing();
	
	public Slashing() {
		super("slashing", 0xBBBBBB);
	}
	
	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		if (isCritical) {
			newDamage *= 1.2f;
		}
		return super.damage(tool, player, target, damage, newDamage, isCritical);
	}
}
