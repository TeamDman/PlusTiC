package landmaster.plustic.traits;

import landmaster.plustic.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.List;

public class Illuminati extends AbstractTrait {
    public static final Illuminati illuminati = new Illuminati();

    public Illuminati() {
        super("illuminati", 0xFFFF7F);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (isSelected && entity instanceof EntityLivingBase) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 20));
            List<EntityLivingBase> lst = world.getEntitiesWithinAABB(EntityLivingBase.class, Utils.AABBfromVecs(
                    entity.getPositionVector().subtract(11, 11, 11),
                    entity.getPositionVector().add(11, 11, 11)),
                    ent -> !ent.equals(entity) && !TinkerUtil.hasTrait(TagUtil.getTagSafe(ent.getHeldItemMainhand()), identifier));
            for (EntityLivingBase ent : lst) {
                ent.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 20));
            }
        }
    }
}
