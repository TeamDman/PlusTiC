package landmaster.plustic.traits;

import java.util.*;

import landmaster.plustic.api.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.utils.*;

public class Ignoble extends AbstractTrait {
	public static final Ignoble ignoble = new Ignoble();
	
	public static final float MAX_IGNOBILITY = 40;
	
	public static final String ENTITIES_TAG = "IgnobleEntities", METER_TAG = "IgnobleMeter";
	
	public Ignoble() {
		super("ignoble", 0x270133);
		MinecraftForge.EVENT_BUS.register(this);
		Toggle.addToggleable(identifier);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tooltip(ItemTooltipEvent event) {
		NBTTagCompound nbt0 = TagUtil.getTagSafe(event.getItemStack());
		if (event.isCanceled()
				|| event.getItemStack() == null
				|| !TinkerUtil.hasTrait(nbt0, getIdentifier())) return;
		event.getToolTip().add(I18n.format("tooltip.plustic.ignoblemodifier.info", nbt0.getFloat(METER_TAG)));
	}
	
	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
		NBTTagCompound nbt = TagUtil.getTagSafe(tool);
		if (Toggle.getToggleState(nbt, identifier) && player.isSneaking()) {
			float damageToDeal = Math.min(nbt.getFloat(METER_TAG), target.getMaxHealth());
			// deliver the Ig Nobel prize
			target.attackEntityFrom(new EntityDamageSource("ignoble", player)
					.setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage(),
					damageToDeal);
			nbt.setFloat(METER_TAG, nbt.getFloat(METER_TAG) - damageToDeal);
		}
	}
	
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (!world.isRemote && entity instanceof EntityLivingBase) {
			final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			
			NBTTagCompound nbt = TagUtil.getTagSafe(tool);
			NBTTagCompound ignoble = TagUtil.getTagSafe(nbt, ENTITIES_TAG);
			
			for (final Iterator<String> it = ignoble.getKeySet().iterator(); it.hasNext(); ) {
				final String uuidString = it.next();
				final UUID uuid = UUID.fromString(uuidString);
				final Entity victim = server.getEntityFromUuid(uuid);
				if (victim == null) {
					it.remove();
				} else if (!victim.isEntityAlive()) {
					float initialHealth = ignoble.getFloat(uuidString);
					float diff = initialHealth - ((EntityLivingBase)entity).getHealth();
					if (diff > 0) {
						nbt.setFloat(METER_TAG, MathHelper.clamp(nbt.getFloat(METER_TAG)+diff, 0, MAX_IGNOBILITY));
					}
					it.remove();
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void defend(LivingHurtEvent event) {
		if (!event.getEntity().getEntityWorld().isRemote) {
			Arrays.stream(EnumHand.values())
			.map(event.getEntityLiving()::getHeldItem)
			.filter(stack -> TinkerUtil.hasTrait(TagUtil.getTagSafe(stack), identifier))
			.findFirst().ifPresent(stack -> {
				getAttacker(event.getSource()).ifPresent(attacker -> {
					NBTTagCompound nbt = TagUtil.getTagSafe(stack);
					NBTTagCompound ignoble = TagUtil.getTagSafe(nbt, ENTITIES_TAG);
					String uuidString = attacker.getUniqueID().toString();
					if (!ignoble.hasKey(uuidString)) {
						ignoble.setFloat(uuidString, event.getEntityLiving().getHealth());
					}
					nbt.setTag(ENTITIES_TAG, ignoble);
					stack.setTagCompound(nbt);
				});
			});
		}
	}
	
	private static @javax.annotation.Nonnull Optional<Entity> getAttacker(DamageSource source) {
		if (source instanceof EntityDamageSource) {
			return Optional.ofNullable(((EntityDamageSource)source).getTrueSource());
		}
		return Optional.empty();
	}
}
