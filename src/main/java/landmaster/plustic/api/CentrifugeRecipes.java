package landmaster.plustic.api;

import com.google.common.base.Throwables;
import landmaster.plustic.config.Config;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.smeltery.AlloyRecipe;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;

import java.lang.reflect.Field;

public class CentrifugeRecipes {
    public static final int APPLY_PER_TICK;

    static {
        try {
            Field f = TileSmeltery.class.getDeclaredField("ALLOYING_PER_TICK");
            f.setAccessible(true);
            APPLY_PER_TICK = (int) f.get(null);
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
