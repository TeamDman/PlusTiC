package colonelgerdauf.plustic.traits;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.*;

public class DevilsStrength extends AbstractTrait {
	public static final DevilsStrength devilsstrength = new DevilsStrength();
	public static final float BONUS = 2;
	
	public DevilsStrength() {
		super("devilsstrength", 0xFF0000);
	}
	
	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		if (player.getEntityWorld().provider.getDimension() != 0) {
			newDamage += BONUS;
		}
		return super.damage(tool, player, target, damage, newDamage, isCritical);
	}
	
	@Override
	public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
	    String loc = String.format(LOC_Extra, getModifierIdentifier());
	    return ImmutableList.of(Util.translateFormatted(loc, Util.df.format(BONUS)));
	}
}
