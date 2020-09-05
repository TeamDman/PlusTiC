package landmaster.plustic.traits.armor;

import c4.conarm.lib.traits.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class CamDaiBay extends AbstractArmorTrait {
    public static final CamDaiBay camdaibay = new CamDaiBay();

    public CamDaiBay() {
        super("camdaibay", 0xBBAD00);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        if (!player.world.isRemote && source.getTrueSource() instanceof EntityLivingBase && player.canBlockDamageSource(source)) {
            player.getActivePotionEffects().stream()
                    .filter(eff -> eff.getPotion().isBadEffect())
                    .forEach(eff -> ((EntityLivingBase) source.getTrueSource()).addPotionEffect(eff));
        }
        return super.onHurt(armor, player, source, damage, newDamage, evt);
    }
}
