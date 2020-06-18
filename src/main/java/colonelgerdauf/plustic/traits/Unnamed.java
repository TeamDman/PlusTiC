package landmaster.plustic.traits;

import java.util.*;

import landmaster.plustic.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.traits.*;

public class Unnamed extends AbstractTrait {
	public static final Unnamed unnamed = new Unnamed();
	
	public Unnamed() {
		super("unnamed", 0x222222);
	}
	
	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		List<? extends EntityLivingBase> lst = target.getEntityWorld().getEntitiesWithinAABB(target.getClass(),
				Utils.AABBfromVecs(
						target.getPositionVector().subtract(8,8,8),
						target.getPositionVector().add(8,8,8)),
				ent -> ent != target);
		newDamage += lst.size();
		return super.damage(tool, player, target, damage, newDamage, isCritical);
	}
}
