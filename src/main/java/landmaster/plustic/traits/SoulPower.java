package landmaster.plustic.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.tslat.aoa3.library.*;
import net.tslat.aoa3.utils.*;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class SoulPower extends AbstractTrait {
    public static final SoulPower soulpower = new SoulPower();

    public SoulPower() {
        super("soulpower", 0x29ffc2);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if (player instanceof EntityPlayer) {
            float soulVal = PlayerUtil.getAdventPlayer((EntityPlayer) player).getResourceValue(Enums.Resources.SOUL);
            if (soulVal > 5) {
                newDamage += soulVal / 25f;
                PlayerUtil.consumeResource((EntityPlayer) player, Enums.Resources.SOUL, 5, true);
            }
        }
        return super.damage(tool, player, target, damage, newDamage, isCritical);
    }
}
