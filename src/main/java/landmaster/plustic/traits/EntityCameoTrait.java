package landmaster.plustic.traits;

import landmaster.plustic.api.Toggle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class EntityCameoTrait extends AbstractTrait {
    protected final ICameoFactory factory;

    public EntityCameoTrait(String identifier, int color, ICameoFactory factory) {
        super(identifier, color);
        this.factory = factory;
        MinecraftForge.EVENT_BUS.register(this);
        Toggle.addToggleable(identifier);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (wasHit && target.isEntityAlive() && random.nextFloat() < 0.38f && Toggle.getToggleState(tool, identifier)) {
            summonCameo(player, target);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void defend(LivingHurtEvent event) {
        ItemStack tool = event.getEntityLiving().getHeldItemMainhand();
        if (event.getEntity().getEntityWorld().isRemote
                || !Toggle.getToggleState(tool, identifier)
                || event.isCanceled()
                || !TinkerUtil.hasTrait(TagUtil.getTagSafe(tool), getIdentifier())
                || !(event.getSource() instanceof EntityDamageSource)
                || !(event.getSource().getTrueSource() instanceof EntityLivingBase))
            return;
        if (random.nextFloat() < 0.38f) {
            EntityLivingBase target = (EntityLivingBase) event.getSource().getTrueSource();
            summonCameo(event.getEntity(), target);
        }
    }

    protected void summonCameo(Entity summoner, EntityLivingBase target) {
        Entity cameo = factory.create(summoner.getEntityWorld(), summoner, target);
        cameo.setPosition(summoner.posX + random.nextDouble() * 4 - 2, summoner.posY, summoner.posZ + random.nextDouble() * 4 - 2);
        summoner.getEntityWorld().spawnEntity(cameo);
    }

    @FunctionalInterface
    public interface ICameoFactory {
        Entity create(World world, Entity summoner, EntityLivingBase target);
    }
}
