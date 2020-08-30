package landmaster.plustic.traits.armor;

import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Optional;

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
                .filter(causer -> ArmorAbilityHandler.getArmorAbilitiesData((EntityPlayer) causer)
                        .getAbilityMap()
                        .containsKey(identifier))
                .ifPresent(causer -> {
                    event.getAffectedEntities().stream()
                            .filter(entity -> entity instanceof EntityLivingBase)
                            .forEach(entity ->
                                    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1)));
                });
    }
}
