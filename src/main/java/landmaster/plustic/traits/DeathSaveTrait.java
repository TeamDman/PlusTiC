package landmaster.plustic.traits;

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import c4.conarm.lib.traits.IArmorAbility;
import c4.conarm.lib.traits.IArmorTrait;
import landmaster.plustic.api.Portal;
import landmaster.plustic.api.Toggle;
import landmaster.plustic.util.Coord4D;
import landmaster.plustic.util.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Abstract class for PlusTiC traits that rescue the holder from death given certain items.
 *
 * @author Landmaster
 */
@net.minecraftforge.fml.common.Optional.Interface(iface = "c4.conarm.lib.traits.IArmorTrait", modid = "conarm")
@net.minecraftforge.fml.common.Optional.Interface(iface = "c4.conarm.lib.traits.IArmorAbility", modid = "conarm")
public abstract class DeathSaveTrait extends AbstractTrait implements IArmorTrait, IArmorAbility {
    @CapabilityInject(Portal.IPortalArmor.class)
    private static Capability<Portal.IPortalArmor> PORTAL_ARMOR = null;
    @CapabilityInject(Toggle.IToggleArmor.class)
    private static Capability<Toggle.IToggleArmor> TOGGLE_ARMOR = null;

    private final int cost;
    private final Predicate<ItemStack> stackMatcher;
    private final String unlocSaveMessage;

    public DeathSaveTrait(String identifier, int color, int cost, Predicate<ItemStack> stackMatcher, String unlocSaveMessage) {
        super(identifier, color);
        this.cost = cost;
        this.stackMatcher = stackMatcher;
        this.unlocSaveMessage = unlocSaveMessage;
        MinecraftForge.EVENT_BUS.register(this);
        Toggle.addToggleable(identifier);
        Toggle.addToggleable(Toggle.ARMOR_FLAG + identifier);
        Portal.addPortalable(identifier);
    }

    @Override
    public String getLocalizedDesc() {
        // add the item cost to the description
        return String.format(super.getLocalizedDesc(), cost);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void timing(LivingDeathEvent event) {
        if (event.getEntity().getEntityWorld().isRemote || !(event.getEntity() instanceof EntityPlayerMP)) {
            return;
        }

        if (Loader.isModLoaded("conarm")
                && hasDeathSaveArmor((EntityPlayer) event.getEntity())
                && event.getEntity().hasCapability(PORTAL_ARMOR, null)
                && event.getEntity().hasCapability(TOGGLE_ARMOR, null)
                && !event.getEntity().getCapability(TOGGLE_ARMOR, null).getDisabled().contains(identifier)
                && Utils.canTeleportTo((EntityPlayer) event.getEntity(), event.getEntity().getCapability(PORTAL_ARMOR, null).location())
                && !event.getEntity().getCapability(PORTAL_ARMOR, null).location().equals(Coord4D.NIHIL)) {
            checkItems(event, event.getEntity().getCapability(PORTAL_ARMOR, null).location());
        } else {
            Arrays.stream(EnumHand.values())
                    .map(event.getEntityLiving()::getHeldItem)
                    .map(TagUtil::getTagSafe)
                    .filter(nbt -> TinkerUtil.hasTrait(nbt, identifier) && Toggle.getToggleState(nbt, identifier) && nbt.hasKey(Portal.PORTAL_NBT, 10))
                    .map(nbt -> nbt.getCompoundTag(Portal.PORTAL_NBT))
                    .map(Coord4D::fromNBT)
                    .filter(coord -> Utils.canTeleportTo((EntityPlayer) event.getEntity(), coord))
                    .findFirst().ifPresent(coord -> {
                checkItems(event, coord);
            });
        }
    }

    private boolean hasDeathSaveArmor(EntityPlayer player) {
        return Optional.ofNullable(ArmorAbilityHandler.getArmorAbilitiesData(player))
                .map(ArmorAbilityHandler.IArmorAbilities::getAbilityMap)
                .filter(map -> map.containsKey(identifier))
                .isPresent();
    }

    private void checkItems(LivingDeathEvent event, Coord4D coord) {
        IItemHandler ih = event.getEntity().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        for (int i = 0; i < ih.getSlots(); ++i) {
            ItemStack is = ih.extractItem(i, cost, true);
            if (stackMatcher.test(is) && is.getCount() >= cost) {
                ih.extractItem(i, cost, false);
                event.setCanceled(true);
                event.getEntityLiving().clearActivePotions();
                MinecraftForge.EVENT_BUS.register(new Object() {
                    @SubscribeEvent
                    public void onServerTick(TickEvent.ServerTickEvent event0) {
                        if (!event.getEntityLiving().isBurning()) {
                            MinecraftForge.EVENT_BUS.unregister(this);
                        }
                        event.getEntityLiving().extinguish();
                    }
                });
                event.getEntityLiving().setHealth(1);
                event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 160, 1));
                event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 160));
                event.getEntity().sendMessage(new TextComponentTranslation(unlocSaveMessage));
                event.getEntity().fallDistance = 0;
                Utils.teleportPlayerTo((EntityPlayerMP) event.getEntity(), coord);
                return;
            }
        }
    }

    @Override
    public boolean disableRendering(ItemStack arg0, EntityLivingBase arg1) {
        return false;
    }

    @Override
    @net.minecraftforge.fml.common.Optional.Method(modid = "conarm")
    public ArmorModifications getModifications(EntityPlayer arg0, ArmorModifications arg1, ItemStack arg2, DamageSource arg3, double arg4, int arg5) {
        return arg1;
    }

    @Override
    public void onAbilityTick(int arg0, World arg1, EntityPlayer arg2) {
    }

    @Override
    public int onArmorDamage(ItemStack arg0, DamageSource arg1, int arg2, int arg3, EntityPlayer arg4, int arg5) {
        return arg3;
    }

    @Override
    public void onArmorEquipped(ItemStack arg0, EntityPlayer arg1, int arg2) {
    }

    @Override
    public int onArmorHeal(ItemStack arg0, DamageSource arg1, int arg2, int arg3, EntityPlayer arg4, int arg5) {
        return arg3;
    }

    @Override
    public void onArmorRemoved(ItemStack arg0, EntityPlayer arg1, int arg2) {
    }

    @Override
    public float onDamaged(ItemStack arg0, EntityPlayer arg1, DamageSource arg2, float arg3, float arg4, LivingDamageEvent arg5) {
        return arg4;
    }

    @Override
    public void onFalling(ItemStack arg0, EntityPlayer arg1, LivingFallEvent arg2) {
    }

    @Override
    public float onHeal(ItemStack arg0, EntityPlayer arg1, float arg2, float arg3, LivingHealEvent arg4) {
        return arg3;
    }

    @Override
    public float onHurt(ItemStack arg0, EntityPlayer arg1, DamageSource arg2, float arg3, float arg4, LivingHurtEvent arg5) {
        return arg4;
    }

    @Override
    public void onItemPickup(ItemStack arg0, EntityItem arg1, EntityItemPickupEvent arg2) {
    }

    @Override
    public void onJumping(ItemStack arg0, EntityPlayer arg1, LivingEvent.LivingJumpEvent arg2) {
    }

    @Override
    public void onKnockback(ItemStack arg0, EntityPlayer arg1, LivingKnockBackEvent arg2) {
    }

    @Override
    public int getAbilityLevel(ModifierNBT arg0) {
        return arg0.level;
    }
}
