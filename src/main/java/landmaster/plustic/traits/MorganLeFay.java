package landmaster.plustic.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.MathHelper;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class MorganLeFay extends AbstractTrait {
    public static final MorganLeFay morganlefay = new MorganLeFay();

    public MorganLeFay() {
        super("morganlefay", 0xFF00FF);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (wasHit) {
            target.hurtResistantTime = 0;
            target.lastDamage = 0;
            target.attackEntityFrom(new EntityDamageSource("morganlefay", player)
                            .setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage(),
                    MathHelper.clamp((float) (2.5 + random.nextGaussian() * 1.5), 0, 5));
        }
    }
}
