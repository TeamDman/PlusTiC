package colonelgerdauf.plustic.traits.armor;

import java.util.*;

import c4.conarm.lib.capabilities.*;
import c4.conarm.lib.traits.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class TomAndJerry extends AbstractArmorTrait {
	public static final TomAndJerry tomAndJerry = new TomAndJerry();
	
	public TomAndJerry() {
		super("tom_and_jerry", 0xb0b3b7);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onExplosion(ExplosionEvent.Detonate event) {
		if (event.getWorld().isRemote) return;
		Optional.ofNullable(event.getExplosion().getExplosivePlacedBy())
		.filter(causer -> causer instanceof EntityPlayer)
		.filter(causer -> !event.getAffectedEntities().contains(causer))
		.filter(causer -> ArmorAbilityHandler.getArmorAbilitiesData((EntityPlayer)causer)
				.getAbilityMap()
				.containsKey(identifier))
		.ifPresent(causer -> {
			event.getAffectedEntities().stream()
			.filter(entity -> entity instanceof EntityLivingBase)
			.forEach(entity -> 
				((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1)));
		});
	}
}
