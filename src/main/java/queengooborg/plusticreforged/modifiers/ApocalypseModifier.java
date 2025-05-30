package queengooborg.plusticreforged.modifiers;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.awt.*;

public class ApocalypseModifier extends Modifier {
	public ApocalypseModifier() {
		super("apocalypse", "Apocalypse", new Description("The end times...", "Your victim becomes moribund and is afflicted with increasing levels of Wither."), new Color(58, 45, 125));
		this.usable = true;
	}

	@Override
	public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
		LivingEntity target = context.getLivingTarget();
		if (target.isAlive()) {
			int amp = -1;
			MobEffectInstance potionEffect = target.getActiveEffectsMap().get(MobEffects.WITHER);
			if (potionEffect != null) amp = potionEffect.getAmplifier();
			amp = Math.min(3, amp + 1);
			target.addEffect(new MobEffectInstance(MobEffects.WITHER, 130, amp));
		}

		return 0;
	}
}
