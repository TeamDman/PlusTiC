package landmaster.plustic.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class NaturesWrath extends AbstractTrait {
    public static final NaturesWrath natureswrath = new NaturesWrath();

    public NaturesWrath() {
        super("natureswrath", 0x007523);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (wasHit) {
            float rnd = random.nextFloat();
            if (rnd < 0.2f && target.isEntityAlive()) {
                target.setFire(5);
            }
            if (rnd < 0.5f) player.heal(1.4f);
        }
    }
}
