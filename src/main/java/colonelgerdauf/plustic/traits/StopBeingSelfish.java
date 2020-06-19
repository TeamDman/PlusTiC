package colonelgerdauf.plustic.traits;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.traits.*;

public class StopBeingSelfish extends AbstractTrait {
	public static final StopBeingSelfish stopbeingselfish = new StopBeingSelfish();
	
	public StopBeingSelfish() {
		super("stopbeingselfish", 0x070070);
	}
	
	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		float theDamage = super.damage(tool, player, target, damage, newDamage, isCritical);
		for (EntityPlayer ep: player.world.playerEntities) {
			if (ep != player) {
				theDamage += 10.0f / Math.max(1, ep.getPositionVector().squareDistanceTo(player.getPositionVector()));
			}
		}
		return theDamage;
	}
}
