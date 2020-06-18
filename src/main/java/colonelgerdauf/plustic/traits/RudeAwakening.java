package landmaster.plustic.traits;

import landmaster.plustic.tools.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.entity.*;
import slimeknights.tconstruct.library.tools.ranged.*;
import slimeknights.tconstruct.library.traits.*;

public class RudeAwakening extends AbstractProjectileTrait {
	public static final RudeAwakening rudeawakening = new RudeAwakening();
	
	public RudeAwakening() {
		super("rudeawakening", 0xFFB200);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onAttack(LivingAttackEvent event) {
		if (event.getEntity().world.isRemote) return;
		if (!(event.getEntity() instanceof IMob)) return;
		
		if (event.getSource() instanceof EntityDamageSourceIndirect
				|| event.getSource() instanceof ProjectileCore.DamageSourceProjectileForEndermen) {
			// handle projectiles first
			Entity projectile = event.getSource().getImmediateSource();
			if (projectile instanceof EntityProjectileBase) {
				if (isToolWithTrait(((EntityProjectileBase)projectile).tinkerProjectile.getItemStack())) {
					event.getSource().setDamageBypassesArmor();
				}
			}
		} else if (event.getSource() instanceof EntityDamageSource
				&& event.getSource().getTrueSource() instanceof EntityLivingBase) {
			// have to specialcase the laser gun here
			ItemStack stack = event.getSource() instanceof ToolLaserGun.LaserDamageSource
					? ((ToolLaserGun.LaserDamageSource)event.getSource()).getStack()
							: ((EntityLivingBase)event.getSource().getTrueSource()).getHeldItemMainhand();
			if (this.isToolWithTrait(stack)) {
				event.getSource().setDamageBypassesArmor();
			}
		}
	}
}
