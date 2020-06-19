package colonelgerdauf.plustic.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.*;

public class Hearts extends AbstractTrait {
	public static final Hearts hearts = new Hearts();
	
	public static final float DAMAGE_MULT = 0.7f;
	
	public Hearts() {
		super("hearts", 0xEF0000);
	}
	
	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		newDamage *= 1 + DAMAGE_MULT * player.getHealth() / player.getMaxHealth();
		return super.damage(tool, player, target, damage, newDamage, isCritical);
	}
}
