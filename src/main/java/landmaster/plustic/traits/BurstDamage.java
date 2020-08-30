package landmaster.plustic.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class BurstDamage extends AbstractTrait {
    public static final BurstDamage burstdamage = new BurstDamage();

    public BurstDamage() {
        super("burstdamage", 0xCC4A0A);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        return (float) (damage + Math.pow(newDamage - damage, 1.2));
    }

    @Override
    public int getPriority() {
        return -1000;
    }
}
