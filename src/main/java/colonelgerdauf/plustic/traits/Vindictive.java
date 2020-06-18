package landmaster.plustic.traits;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.traits.*;

public class Vindictive extends AbstractTrait {
	public static final Vindictive vindictive = new Vindictive();
	
	public Vindictive() {
		super("vindictive", 0x000000);
	}
	
	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		if (target instanceof EntityPlayer) {
			newDamage *= 1.4f;
			player.heal(newDamage * 0.2f);
		}
		
		return super.damage(tool, player, target, damage, newDamage, isCritical);
	}
}
