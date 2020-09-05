package landmaster.plustic.traits.armor;

import c4.conarm.lib.capabilities.*;
import c4.conarm.lib.traits.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Optional;

public class GoodFridayAgreement extends AbstractArmorTrait {
    public static final GoodFridayAgreement goodfridayagreement = new GoodFridayAgreement();

    public GoodFridayAgreement() {
        super("goodfridayagreement", 0x00FF00);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onSetAttackTarget(LivingSetAttackTargetEvent event) {
        if (!event.getEntity().world.isRemote
                && event.getEntity() instanceof EntityIronGolem
                && event.getTarget() instanceof EntityPlayer) {
            boolean doReset = Optional.ofNullable(ArmorAbilityHandler.getArmorAbilitiesData((EntityPlayer) event.getTarget()))
                    .map(ArmorAbilityHandler.IArmorAbilities::getAbilityMap)
                    .map(map -> map.containsKey(identifier))
                    .orElse(false);

            if (doReset) {
                ((EntityIronGolem) event.getEntityLiving()).setAttackTarget(null);
            }
        }
    }
}
