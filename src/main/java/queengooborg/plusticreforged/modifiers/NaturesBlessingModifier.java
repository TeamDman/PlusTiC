package queengooborg.plusticreforged.modifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.awt.*;
import java.util.Random;

public class NaturesBlessingModifier extends Modifier {
	private Random random;

	public NaturesBlessingModifier() {
		super("naturesblessing", "Nature's Blessing", new Description("A chance to drop bread per mob killed or block broken. Sometimes heals the player by a bit after killing a mob."), new Color(190, 250, 92));
		this.usable = true;
	}

	@Override
	public void afterBlockBreak(IToolStackView tool, int level, ToolHarvestContext context) {
		dropBread(context.getWorld(), context.getPos(), 0.005f);
	}

	@Override
	public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
		if (!context.getLivingTarget().isAlive()) {
			dropBread(context.getLivingTarget().level, context.getLivingTarget().blockPosition(), 0.05f);
			float rnd = random.nextFloat();
			if (rnd < 0.3f) {
				context.getPlayerAttacker().heal(3.2f);
			}
		}

		return 0;
	}

	protected void dropBread(Level level, BlockPos pos, float chance) {
		if (random.nextFloat() < chance) {
			ItemEntity entity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.BREAD, 1));
			level.addFreshEntity(entity);
		}
	}
}
