package landmaster.plustic.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Explosive extends AbstractTrait {
    public static final Explosive explosive = new Explosive();

    public Explosive() {
        super("explosive", 0xFF4F4F);
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        if (!player.getEntityWorld().isRemote)
            target.getEntityWorld().createExplosion(player, target.posX, target.posY, target.posZ, 2.4f, false);
    }
}
