package landmaster.plustic.traits;

import landmaster.plustic.tools.ToolLaserGun;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.tools.ranged.ProjectileCore;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;

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
                if (isToolWithTrait(((EntityProjectileBase) projectile).tinkerProjectile.getItemStack())) {
                    event.getSource().setDamageBypassesArmor();
                }
            }
        } else if (event.getSource() instanceof EntityDamageSource
                && event.getSource().getTrueSource() instanceof EntityLivingBase) {
            // have to specialcase the laser gun here
            ItemStack stack = event.getSource() instanceof ToolLaserGun.LaserDamageSource
                    ? ((ToolLaserGun.LaserDamageSource) event.getSource()).getStack()
                    : ((EntityLivingBase) event.getSource().getTrueSource()).getHeldItemMainhand();
            if (this.isToolWithTrait(stack)) {
                event.getSource().setDamageBypassesArmor();
            }
        }
    }
}
