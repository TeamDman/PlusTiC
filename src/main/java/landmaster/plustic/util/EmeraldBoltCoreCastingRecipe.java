package landmaster.plustic.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.shared.TinkerFluids;
import slimeknights.tconstruct.tools.ranged.BoltCoreCastingRecipe;

public class EmeraldBoltCoreCastingRecipe extends BoltCoreCastingRecipe {
    public static final EmeraldBoltCoreCastingRecipe INSTANCE = new EmeraldBoltCoreCastingRecipe();
    public static final int boltCoreAmount = Material.VALUE_Gem * 2;

    protected EmeraldBoltCoreCastingRecipe() {
    }

    @Override
    public boolean matches(ItemStack cast, Fluid fluid) {
        return super.matches(cast, fluid) && TinkerFluids.emerald.equals(fluid);
    }

    @Override
    public int getFluidAmount() {
        return boltCoreAmount;
    }
}
