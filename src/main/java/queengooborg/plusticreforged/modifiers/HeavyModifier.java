package queengooborg.plusticreforged.modifiers;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.awt.*;

public class HeavyModifier extends Modifier {
	public HeavyModifier() {
		super("heavy_metal", "Heavy", new Description("Increased knockback + Slowness on target."), new Color(85, 85, 85));
		this.usable = true;
	}

	@Override
	public float beforeEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
		return knockback * 1.3f;
	}

	@Override
	public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
		if ((damageDealt > 0) && context.getTarget().isAlive()) {
			context.getLivingTarget().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 1));
		}

		return 0;
	}
}
