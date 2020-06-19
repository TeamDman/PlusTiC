package colonelgerdauf.plustic.traits;

import java.util.*;

import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.utils.*;

public class Ghastly extends AbstractTrait {
	public static final Ghastly ghastly = new Ghastly();
	
	public Ghastly() {
		super("ghastly", 0xFFFFFF);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void defend(LivingHurtEvent event) {
		if (event.getEntityLiving().isSneaking()
				&& event.getSource() instanceof EntityDamageSource
				&& Arrays.stream(EnumHand.values())
				.map(event.getEntityLiving()::getHeldItem)
				.map(TagUtil::getTagSafe)
				.anyMatch(nbt -> TinkerUtil.hasTrait(nbt, identifier))) {
			Optional.ofNullable(event.getSource().getTrueSource()).ifPresent(attacker -> {
				if (attacker instanceof EntityLivingBase) {
					((EntityLivingBase)attacker).addPotionEffect(
							new PotionEffect(MobEffects.SLOWNESS, 100, 2));
				}
			});
		}
	}
}
