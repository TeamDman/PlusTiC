package queengooborg.plusticreforged.modifiers;

import net.minecraft.world.entity.LivingEntity;
import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nullable;
import java.awt.*;

public class InvulnerableModifier extends Modifier {
	public InvulnerableModifier() {
		super("invulnerable", "Invulnerable", new Description("You could only obtain these tools through Creative or cheats -- so if you're gonna cheat, you might as well commit, right?", "The tool takes no damage"), new Color(85, 85, 85));
		this.usable = true;
	}

	@Override
	public int onDamageTool(IToolStackView toolStack, int level, int amount, @Nullable LivingEntity holder) {
		// Prevents the tool from taking damage
		return 0;
	}
}
