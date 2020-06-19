package colonelgerdauf.plustic.traits.armor;

import c4.conarm.lib.armor.*;
import c4.conarm.lib.traits.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class DunansTransport extends AbstractArmorTrait {
	public static final DunansTransport dunanstransport = new DunansTransport();
	
	private static final float TOUGH_PER_LEVEL = 2.0F;
	private static final int[] ARMOR_VALUES = new int[] {2, 3, 4, 2};
	
	public DunansTransport() {
		super("dunanstransport", 0xFFE0F1);
	}
	
	@Override
	public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
		if (player.isRiding()) {
			mods.addArmor(ARMOR_VALUES[slot]);
			mods.addToughness(TOUGH_PER_LEVEL);
		}
		return super.getModifications(player, mods, armor, source, damage, slot);
	}
}
