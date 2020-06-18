package landmaster.plustic.traits;

import landmaster.plustic.api.*;
import landmaster.plustic.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.tools.modifiers.*;

public class Psicological extends AbstractTrait {
	public static final int PSI_COST = 28;
	
	public static final Psicological psicological = new Psicological();
	
	public Psicological() {
		super("psicological", 0x6D9EFF);
		Toggle.addToggleable(identifier);
	}
	
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (TagUtil.getTagSafe(tool).getBoolean(ModReinforced.TAG_UNBREAKABLE)) {
			return;
		}
		if (entity instanceof EntityPlayer
				&& Toggle.getToggleState(tool, identifier)
				&& ToolHelper.getCurrentDurability(tool) < ToolHelper.getMaxDurability(tool)) {
			if (PsiUtils.extractPsiExact((EntityPlayer)entity, PSI_COST) >= PSI_COST) {
				ToolHelper.unbreakTool(tool);
				ToolHelper.healTool(tool, 1, (EntityPlayer)entity);
			}
		}
	}
	
	@Override
	public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
		if (TagUtil.getTagSafe(tool).getBoolean(ModReinforced.TAG_UNBREAKABLE)) {
			return 0;
		}
		if (entity instanceof EntityPlayer
				&& newDamage >= 1
				&& Toggle.getToggleState(tool, identifier)) {
			if (PsiUtils.extractPsiExact((EntityPlayer)entity, PSI_COST) >= PSI_COST) {
				--newDamage;
			}
		}
		return super.onToolDamage(tool, damage, newDamage, entity);
	}
}
