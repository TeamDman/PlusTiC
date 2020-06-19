package colonelgerdauf.plustic.traits;

import colonelgerdauf.plustic.tools.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.entity.*;
import slimeknights.tconstruct.library.tools.ranged.*;
import slimeknights.tconstruct.library.traits.*;

public class Jaded extends AbstractProjectileTrait {
	public static final Jaded jaded = new Jaded();
	
	public static final String JADED_LEVEL_TAG = "PlusTiC_JadedLevel";
	public static final String JADED_TIMER_TAG = "PlusTiC_JadedTimer";
	public static final String JADED_LASTHEALTH_TAG = "PlusTiC_JadedLastHealth";
	
	public Jaded() {
		super("jaded", 0x00e682);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	protected void applyJaded(Entity target) {
		if (!target.world.isRemote) {
			EntityLivingBase targetLiving = null;
			if (target instanceof EntityLivingBase) {
				targetLiving = (EntityLivingBase) target;
			}
			if (target instanceof MultiPartEntityPart) {
				IEntityMultiPart parent = ((MultiPartEntityPart)target).parent;
				if (parent instanceof EntityLivingBase) {
					targetLiving = (EntityLivingBase)((MultiPartEntityPart)target).parent;
				}
			}
			if (targetLiving == null) return;
			NBTTagCompound nbt = targetLiving.getEntityData();
			nbt.setByte(JADED_LEVEL_TAG, (byte)Math.min(nbt.getByte(JADED_LEVEL_TAG)+1, 3));
			nbt.setInteger(JADED_TIMER_TAG, 80);
			nbt.setFloat(JADED_LASTHEALTH_TAG, targetLiving.getHealth());
		}
	}
	
	@Override
	public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed) {
		applyJaded(target);
	}
	
	@SubscribeEvent
	public void onAttack(LivingAttackEvent event) { // for melee damage
		if (event.getEntity().world.isRemote) return;
		
		if (event.getSource() instanceof EntityDamageSource
				&& !(event.getSource() instanceof EntityDamageSourceIndirect)
				&& !(event.getSource() instanceof ProjectileCore.DamageSourceProjectileForEndermen)
				&& event.getSource().getTrueSource() instanceof EntityLivingBase) {
			ItemStack stack = event.getSource() instanceof ToolLaserGun.LaserDamageSource
					? ((ToolLaserGun.LaserDamageSource)event.getSource()).getStack()
							: ((EntityLivingBase)event.getSource().getTrueSource()).getHeldItemMainhand();
			if (this.isToolWithTrait(stack)) {
				applyJaded(event.getEntity());
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		NBTTagCompound nbt = event.getEntity().getEntityData();
		if (!event.getEntity().world.isRemote && nbt.getInteger(JADED_TIMER_TAG) > 0) {
			/*
			System.out.println("Cur health: "+event.getEntityLiving().getHealth());
			System.out.println("Last health: "+nbt.getFloat(JADED_LASTHEALTH_TAG));*/
			if (event.getEntityLiving().getHealth() > nbt.getFloat(JADED_LASTHEALTH_TAG)) {
				float healthDiff = event.getEntityLiving().getHealth() - nbt.getFloat(JADED_LASTHEALTH_TAG);
				float scalar = (3 - nbt.getByte(JADED_LEVEL_TAG)) / 3f;
				//System.out.println(healthDiff + " " + scalar);
				event.getEntityLiving().setHealth(
						nbt.getFloat(JADED_LASTHEALTH_TAG)
						+ healthDiff*scalar);
			}
			nbt.setFloat(JADED_LASTHEALTH_TAG, event.getEntityLiving().getHealth());
			nbt.setInteger(JADED_TIMER_TAG, Math.max(nbt.getInteger(JADED_TIMER_TAG)-1, 0));
			if (nbt.getInteger(JADED_TIMER_TAG) <= 0) {
				nbt.removeTag(JADED_LEVEL_TAG);
				nbt.removeTag(JADED_TIMER_TAG);
				nbt.removeTag(JADED_LASTHEALTH_TAG);
			}
		}
	}
}
