package landmaster.plustic.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Sassy extends AbstractTrait {
    public static final Sassy sassy = new Sassy();

    public Sassy() {
        super("sassy", 0xFFFF00);
    }

    @Override
    public boolean isCriticalHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target) {
        return target != null && (target instanceof EntityPlayer || !target.isNonBoss());
    }
}
