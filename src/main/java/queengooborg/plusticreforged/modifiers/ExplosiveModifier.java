package queengooborg.plusticreforged.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.awt.*;

public class ExplosiveModifier extends Modifier {
	public ExplosiveModifier() {
		super("explosive", "Explosive", new Description("As the name implies, an explosion is created when the tool is used."), new Color(255, 79, 79));
		this.usable = true;
	}

	@Override
	public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
		LivingEntity target = context.getLivingTarget();

		if (target != null) {
			target.level.explode(context.getLivingTarget(), context.getLivingTarget().getX(), context.getLivingTarget().getY(), context.getLivingTarget().getZ(), 0.5f, false, Explosion.BlockInteraction.NONE);
		}

		return 0;
	}
}
