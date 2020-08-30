package landmaster.plustic.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class NaturesPower extends AbstractTrait {
    public static final NaturesPower naturespower = new NaturesPower();

    public NaturesPower() {
        super("naturespower", 0xFFFF00);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (wasHit) {
            float rnd = random.nextFloat();
            if (rnd < 0.2 && target.isEntityAlive())
                target.setFire(3);
            else if (rnd < 0.4 && player.isEntityAlive())
                player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 101));
            else if (rnd < 0.6 && player.isEntityAlive())
                player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 101));
        }
    }
}
