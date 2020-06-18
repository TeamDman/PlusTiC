package landmaster.plustic.api;

import java.lang.reflect.*;

import com.google.common.base.*;

import landmaster.plustic.config.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.library.smeltery.*;
import slimeknights.tconstruct.smeltery.tileentity.*;

public class CentrifugeRecipes {
	public static final int APPLY_PER_TICK;
	static {
		try {
			Field f = TileSmeltery.class.getDeclaredField("ALLOYING_PER_TICK");
			f.setAccessible(true);
			APPLY_PER_TICK = (int)f.get(null);
		} catch (Exception e) {
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}
	
	public static int matches(AlloyRecipe recipe, FluidStack input) {
		if (!recipe.isValid()
				|| recipe.getResult().amount <= 0
				|| !Config.isCentrifugeRecipeValid(recipe)
				|| !input.containsFluid(recipe.getResult())) {
			return 0;
		}
		return input.amount / recipe.getResult().amount;
	}
}
