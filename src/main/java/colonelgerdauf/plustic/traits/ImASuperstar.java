package colonelgerdauf.plustic.traits;

import hellfirepvp.astralsorcery.common.constellation.*;
import hellfirepvp.astralsorcery.common.data.research.*;
import hellfirepvp.astralsorcery.common.lib.*;
import colonelgerdauf.plustic.api.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.utils.*;

public class ImASuperstar extends AbstractTrait {
	public static final ImASuperstar imasuperstar = new ImASuperstar();
	
	public ImASuperstar() {
		super("im_a_superstar", 0x000b56);
	}
	
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (isSelected && entity instanceof EntityPlayer) {
			IMajorConstellation attuned = ResearchManager.getProgress((EntityPlayer)entity, Side.SERVER).getAttunedConstellation();
			if (random.nextFloat() < 0.1
				&& attuned == Constellations.aevitas) {
				((EntityPlayer)entity).heal(1.0f);
			} else if (random.nextFloat() < 0.005 && attuned == Constellations.vicio) {
				((EntityPlayer)entity).getFoodStats().addStats(1, 1);
				ToolHelper.damageTool(tool, 1, (EntityPlayer)entity);
			}
		}
	}
	
	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		if (!player.world.isRemote && player instanceof EntityPlayer && random.nextFloat() < 0.25
				&& ResearchManager.getProgress((EntityPlayer)player, Side.SERVER).getAttunedConstellation()
				== Constellations.discidia) {
			newDamage *= 2.5;
		}
		return super.damage(tool, player, target, damage, newDamage, isCritical);
	}
	
	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
		if (!event.getEntity().world.isRemote
				&& ResearchManager.getProgress(event.getEntityPlayer(), Side.SERVER).getAttunedConstellation()
				== Constellations.evorsio) {
			event.setNewSpeed(event.getNewSpeed() * 1.75f);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void defend(LivingHurtEvent event) {
		ItemStack tool = event.getEntityLiving().getHeldItemMainhand();
		if (event.getEntity().getEntityWorld().isRemote
				|| !Toggle.getToggleState(tool, identifier)
				|| event.isCanceled()
				|| !TinkerUtil.hasTrait(
						TagUtil.getTagSafe(tool),
						getIdentifier())
				|| ToolHelper.getCurrentDurability(tool) < 1
				|| !(event.getEntity() instanceof EntityPlayer)
				|| ResearchManager.getProgress((EntityPlayer)event.getEntity(), Side.SERVER).getAttunedConstellation()
				!= Constellations.armara)
			return;
		event.setAmount(event.getAmount() * 0.5f);
		ToolHelper.damageTool(tool, 1, event.getEntityLiving());
	}
}
