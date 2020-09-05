package landmaster.plustic.traits.armor;

import c4.conarm.lib.capabilities.*;
import c4.conarm.lib.traits.*;
import landmaster.plustic.api.Toggle;
import landmaster.plustic.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Optional;

public class Invariant extends AbstractArmorTrait {
    public static final Invariant invariant = new Invariant();

    @CapabilityInject(Toggle.IToggleArmor.class)
    private static Capability<Toggle.IToggleArmor> TOGGLE_ARMOR = null;

    public Invariant() {
        super("invariant", 0xD6D6D6);
        MinecraftForge.EVENT_BUS.register(this);
        Toggle.addToggleable(Toggle.ARMOR_FLAG + identifier);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onExplosion(ExplosionEvent.Detonate event) {
        if (event.getWorld().isRemote) return;
        List<EntityPlayer> list = event.getWorld().getEntitiesWithinAABB(EntityPlayer.class, Utils.AABBfromVecs(
                event.getExplosion().getPosition().subtract(10, 10, 10),
                event.getExplosion().getPosition().add(10, 10, 10)),
                player -> {
                    return Optional.ofNullable(ArmorAbilityHandler.getArmorAbilitiesData(player))
                            .map(ArmorAbilityHandler.IArmorAbilities::getAbilityMap)
                            .filter(map -> map.containsKey(identifier))
                            .isPresent()
                            && Optional.ofNullable(player.getCapability(TOGGLE_ARMOR, null))
                            .map(toggle -> toggle.getDisabled())
                            .filter(set -> !set.contains(identifier))
                            .isPresent();
                });
        if (!list.isEmpty()) {
            event.getAffectedBlocks().clear();
        }
    }
}
