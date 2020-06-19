package colonelgerdauf.plustic.traits;

import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.library.traits.*;

public class Anticorrosion extends AbstractTrait {
	public static final Anticorrosion anticorrosion = new Anticorrosion();
	
	public Anticorrosion() {
		super("anticorrosion", 0xFFFFFF);
	}
	
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (!world.isRemote && isSelected && entity instanceof EntityLivingBase && random.nextFloat() < 0.03f) {
			EntityLivingBase elb = (EntityLivingBase)entity;
			elb.removePotionEffect(MobEffects.POISON);
		}
	}
}
