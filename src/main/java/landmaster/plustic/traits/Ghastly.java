package landmaster.plustic.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.Arrays;
import java.util.Optional;

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
                    ((EntityLivingBase) attacker).addPotionEffect(
                            new PotionEffect(MobEffects.SLOWNESS, 100, 2));
                }
            });
        }
    }
}
