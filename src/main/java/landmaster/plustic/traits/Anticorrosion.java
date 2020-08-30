package landmaster.plustic.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Anticorrosion extends AbstractTrait {
    public static final Anticorrosion anticorrosion = new Anticorrosion();

    public Anticorrosion() {
        super("anticorrosion", 0xFFFFFF);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && isSelected && entity instanceof EntityLivingBase && random.nextFloat() < 0.03f) {
            EntityLivingBase elb = (EntityLivingBase) entity;
            elb.removePotionEffect(MobEffects.POISON);
        }
    }
}
