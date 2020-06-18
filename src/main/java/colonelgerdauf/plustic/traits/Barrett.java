package landmaster.plustic.traits;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.traits.*;

public class Barrett extends AbstractTrait {
	public static final Barrett barrett = new Barrett();
	
	public Barrett() {
		super("barrett", 0x0000FF);
	}
	
	@Override
	public boolean isCriticalHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target) {
		return random.nextFloat() < (player.getMaxHealth() - player.getHealth()) / (player.getMaxHealth() * 0.7f);
	}
}
