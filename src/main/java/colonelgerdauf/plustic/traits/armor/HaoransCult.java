package colonelgerdauf.plustic.traits.armor;

import java.util.*;

import c4.conarm.lib.traits.*;
import colonelgerdauf.plustic.util.Utils;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.world.*;

public class HaoransCult extends AbstractArmorTrait {
	public static final HaoransCult haoranscult = new HaoransCult();
	
	public HaoransCult() {
		super("haorans_cult", 0x880000);
	}
	
	@Override
	public void onAbilityTick(int arg0, World arg1, EntityPlayer arg2) {
		if (arg2.isPotionActive(MobEffects.WITHER) && arg1.getTotalWorldTime() % 20 == 0) {
			List<EntityLivingBase> list = arg1.getEntitiesWithinAABB(EntityLivingBase.class,
					Utils.AABBfromVecs(arg2.getPositionVector().subtract(7, 7, 7),
							arg2.getPositionVector().add(7, 7, 7)),
					ent -> ent != arg2 && ent instanceof IMob);
			for (EntityLivingBase elb: list) {
				elb.setHealth(elb.getHealth() * 0.7f);
			}
		}
	}
}
