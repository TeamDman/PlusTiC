package landmaster.plustic.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Spades extends AbstractTrait {
    public static final Spades spades = new Spades();

    public static final float DAMAGE_MULT = 1.3f;

    public Spades() {
        super("spades", 0x0000D3);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        newDamage *= (1 + DAMAGE_MULT * (player.getMaxHealth() - player.getHealth()) / player.getMaxHealth());
        return super.damage(tool, player, target, damage, newDamage, isCritical);
    }
}
