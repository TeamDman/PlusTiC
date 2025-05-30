package queengooborg.plusticreforged.modifiers;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.awt.*;
import java.util.Random;

public class NaturesPowerModifier extends Modifier {
	private Random random;

	public NaturesPowerModifier() {
		super("naturespower", "Nature's Power", new Description("After attacking a mob: 20% chance to burn target. 20% chance to give player Speed. 20% chance to give player Strength."), new Color(255, 255, 0));
		this.usable = true;
	}

	public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
		LivingEntity target = context.getLivingTarget();
		LivingEntity player = context.getPlayerAttacker();

		float rnd = random.nextFloat();
		if (rnd < 0.2 && target.isAlive()) {
			target.setSecondsOnFire(3);
		} else if (rnd < 0.4 && player.isAlive()) {
			player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 101));
		} else if (rnd < 0.6 && player.isAlive()) {
			player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 101));
		}

		return 0;
	}
}
