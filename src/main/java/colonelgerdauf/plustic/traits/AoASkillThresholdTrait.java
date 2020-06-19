package colonelgerdauf.plustic.traits;

import java.util.*;

import it.unimi.dsi.fastutil.ints.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.items.*;
import net.tslat.aoa3.library.*;
import net.tslat.aoa3.utils.*;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.traits.*;

public class AoASkillThresholdTrait extends AbstractTrait {
	private static final EnumMap<Enums.Skills, Int2ObjectMap<AoASkillThresholdTrait>> traitsCache = new EnumMap<>(Enums.Skills.class);
	
	public static AoASkillThresholdTrait fetch(String skill, int minLevel) {
		return fetch(Enums.Skills.valueOf(skill.toUpperCase(Locale.US)), minLevel);
	}
	
	public static AoASkillThresholdTrait fetch(Enums.Skills skill, int minLevel) {
		Int2ObjectMap<AoASkillThresholdTrait> lvToTrait = traitsCache.computeIfAbsent(skill,
				k -> new Int2ObjectOpenHashMap<>());
		if (!lvToTrait.containsKey(minLevel)) {
			lvToTrait.put(minLevel, new AoASkillThresholdTrait(skill, minLevel));
		}
		return lvToTrait.get(minLevel);
	}
	
	private final Enums.Skills skill;
	private final int minLevel;
	
	public AoASkillThresholdTrait(Enums.Skills skill, int minLevel, String customIdentifier) {
		super(customIdentifier, 0xffffff);
		this.skill = skill;
		this.minLevel = minLevel;
	}
	public AoASkillThresholdTrait(Enums.Skills skill, int minLevel) {
		this(skill, minLevel, genIdentifier(skill, minLevel));
	}
	
	public static String genIdentifier(Enums.Skills skill, int minLevel) {
		return "aoa_threshold_" + skill.name().toLowerCase(Locale.US) + "_" + minLevel;
	}

	public Enums.Skills getSkill() {
		return skill;
	}

	public int getMinLevel() {
		return minLevel;
	}
	
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (!world.isRemote
				&& entity instanceof EntityPlayer
				&& !((EntityPlayer)entity).capabilities.isCreativeMode
				&& !PlayerUtil.doesPlayerHaveLevel((EntityPlayer)entity, getSkill(), getMinLevel())) {
			EntityPlayer player = (EntityPlayer)entity;
			ItemHandlerHelper.giveItemToPlayer(player, tool);
			player.setHeldItem(player.getHeldItemOffhand() == tool
					? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, ItemStack.EMPTY);
			player.inventoryContainer.detectAndSendChanges();
		}
	}
	
	@Override
	public String getLocalizedName() {
		return Util.translateFormatted(
				"modifier.aoa_threshold.name",
				Util.translate("skills.%s.name", getSkill().name().toLowerCase(Locale.US)),
				getMinLevel());
	}
	
	@Override
	public String getLocalizedDesc() {
		return Util.translateFormatted(
				"modifier.aoa_threshold.desc",
				Util.translate("skills.%s.name", getSkill().name().toLowerCase(Locale.US)),
				getMinLevel());
	}
}
