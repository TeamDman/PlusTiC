package colonelgerdauf.plustic.traits;

import colonelgerdauf.plustic.api.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.utils.*;

public class EntityCameoTrait extends AbstractTrait {
	@FunctionalInterface
	public static interface ICameoFactory {
		Entity create(World world, Entity summoner, EntityLivingBase target);
	}
	
	protected final ICameoFactory factory;
	
	public EntityCameoTrait(String identifier, int color, ICameoFactory factory) {
		super(identifier, color);
		this.factory = factory;
		MinecraftForge.EVENT_BUS.register(this);
		Toggle.addToggleable(identifier);
	}
	
	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit && target.isEntityAlive() && random.nextFloat() < 0.38f
				&& Toggle.getToggleState(tool, identifier)) {
			summonCameo(player, target);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void defend(LivingHurtEvent event) {
		ItemStack tool = event.getEntityLiving().getHeldItemMainhand();
		if (event.getEntity().getEntityWorld().isRemote
				|| !Toggle.getToggleState(tool, identifier)
				|| event.isCanceled()
				|| !TinkerUtil.hasTrait(
						TagUtil.getTagSafe(tool),
						getIdentifier())
				|| !(event.getSource() instanceof EntityDamageSource)
				|| !(event.getSource().getTrueSource() instanceof EntityLivingBase))
			return;
		if (random.nextFloat() < 0.38f) {
			EntityLivingBase target = (EntityLivingBase)event.getSource().getTrueSource();
			summonCameo(event.getEntity(), target);
		}
	}
	
	protected void summonCameo(Entity summoner, EntityLivingBase target) {
		Entity cameo = factory.create(summoner.getEntityWorld(), summoner, target);
		cameo.setPosition(summoner.posX + random.nextDouble()*4 - 2,
				summoner.posY,
				summoner.posZ + random.nextDouble()*4 - 2);
		summoner.getEntityWorld().spawnEntity(cameo);
	}
}
