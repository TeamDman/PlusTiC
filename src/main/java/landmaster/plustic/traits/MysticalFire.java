package landmaster.plustic.traits;

import landmaster.plustic.ModInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.potion.TinkerPotion;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import javax.annotation.Nonnull;

public class MysticalFire extends AbstractTrait {
    public static final MysticalFire mystical_fire = new MysticalFire();

    public static final MFPotion POTION = new MFPotion();

    public MysticalFire() {
        super("mystical_fire", 0x681302);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void registerPotion(RegistryEvent.Register<Potion> event) {
        event.getRegistry().register(POTION);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        target.setFire(20);
        POTION.apply(target, 20 * 20);
    }

    private static class MFPotion extends TinkerPotion {
        MFPotion() {
            super(new ResourceLocation(ModInfo.MODID, "mystical_fire_potion"), true, false, 0x681302);
        }

        @Override
        public boolean isReady(int duration, int strength) {
            return duration % 5 == 0;
        }

        @Override
        public void performEffect(@Nonnull EntityLivingBase entity, int id) {
            entity.attackEntityFrom(DamageSource.MAGIC, 1.0f);
        }
    }
}
