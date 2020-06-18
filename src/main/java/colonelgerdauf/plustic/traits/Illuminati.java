package landmaster.plustic.traits;

import java.util.*;

import landmaster.plustic.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.utils.*;

public class Illuminati extends AbstractTrait {
	public static final Illuminati illuminati = new Illuminati();
	
	public Illuminati() {
		super("illuminati", 0xFFFF7F);
	}
	
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (isSelected && entity instanceof EntityLivingBase) {
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 20));
			List<EntityLivingBase> lst = world.getEntitiesWithinAABB(EntityLivingBase.class, Utils.AABBfromVecs(
					entity.getPositionVector().subtract(11, 11, 11),
					entity.getPositionVector().add(11, 11, 11)),
					ent -> !ent.equals(entity) && !TinkerUtil.hasTrait(TagUtil.getTagSafe(ent.getHeldItemMainhand()), identifier));
			for (EntityLivingBase ent: lst) {
				ent.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 20));
			}
		}
	}
}
