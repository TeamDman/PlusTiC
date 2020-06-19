package colonelgerdauf.plustic.traits;

import java.util.*;

import colonelgerdauf.plustic.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.utils.*;

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
					Utils.AABBfromVecs(entity.getPositionVector().subtract(8,8,8),
							entity.getPositionVector().add(8,8,8)),
					ent -> ent instanceof IMob && ent != entity);
			if (lst.size() > 0) {
				EntityLiving randomEntity = lst.get(random.nextInt(lst.size()));
				randomEntity.attackEntityFrom(new EntityDamageSource("darktraveler", entity).setDamageBypassesArmor(),
						2f+random.nextFloat()*2.5f);
				ToolHelper.damageTool(tool, 1, entity instanceof EntityLivingBase
						? (EntityLivingBase)entity : null);
			}
		}
	}
}
