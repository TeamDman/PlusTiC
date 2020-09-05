package landmaster.plustic.traits;

import landmaster.plustic.api.Toggle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class DivineShield extends AbstractTrait {
    public static final DivineShield divineShield = new DivineShield();

    public DivineShield() {
        super("divineshield", 0x00FFFF);
        MinecraftForge.EVENT_BUS.register(this);
        Toggle.addToggleable(identifier);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (isSelected && entity instanceof EntityLivingBase) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20));
        }
    }

    /**
     * @updator: TeamDman
     * @changes: Fix divine shield not working from offhand (#2)
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void defend(LivingHurtEvent event) {
        ItemStack tool = event.getEntityLiving().getHeldItem(event.getEntityLiving().getActiveHand());
        if (event.getEntity().getEntityWorld().isRemote
                || !Toggle.getToggleState(tool, identifier)
                || event.isCanceled()
                || !TinkerUtil.hasTrait(
                TagUtil.getTagSafe(tool),
                getIdentifier())
                || ToolHelper.getCurrentDurability(tool) < 1)
            return;
        event.setAmount(event.getAmount() * 0.85f);
        ToolHelper.damageTool(tool, 1, event.getEntityLiving());
    }
}
