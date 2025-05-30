package queengooborg.plusticreforged.modifiers;

import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.awt.*;
import java.util.Random;

public class NaturesWrathModifier extends Modifier {
	private Random random;

	public NaturesWrathModifier() {
		super("natureswrath", "Nature's Wrath", new Description("Nature's wrath burning...", "After attacking a mob, there is a chance to burn the target, as well as to heal the player."), new Color(0, 117, 35));
		this.usable = true;
	}

	public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
		float rnd = random.nextFloat();
		if (rnd < 0.2f && context.getLivingTarget().isAlive()) {
			context.getLivingTarget().setSecondsOnFire(5);
		}
		if (rnd < 0.5f) {
			context.getPlayerAttacker().heal(1.4f);
		}

		return 0;
	}
}
