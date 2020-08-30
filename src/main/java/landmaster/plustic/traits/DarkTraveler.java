package landmaster.plustic.traits;

import landmaster.plustic.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.List;

public class DarkTraveler extends AbstractTrait {
    public static final DarkTraveler darktraveler = new DarkTraveler();

    public DarkTraveler() {
        super("darktraveler", 0x000044);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (world.isRemote || !isSelected) return;
        if (random.nextFloat() < 0.035f && ToolHelper.getCurrentDurability(tool) >= 1) {
            List<EntityLiving> lst = world.getEntitiesWithinAABB(EntityLiving.class,
                    Utils.AABBfromVecs(
                            entity.getPositionVector().subtract(8, 8, 8),
                            entity.getPositionVector().add(new Vec3d(8, 8, 8))),
                    ent -> ent instanceof IMob && ent != entity);
            if (lst.size() > 0) {
                EntityLiving randomEntity = lst.get(random.nextInt(lst.size()));
                randomEntity.attackEntityFrom(new EntityDamageSource("darktraveler", entity).setDamageBypassesArmor(),
                        2f + random.nextFloat() * 2.5f);
                ToolHelper.damageTool(tool, 1, entity instanceof EntityLivingBase ? (EntityLivingBase) entity : null);
            }
        }
    }
}
