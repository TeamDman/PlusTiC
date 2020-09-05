package landmaster.plustic.crafttweaker;

import com.blamejared.compat.tconstruct.materials.*;
import crafttweaker.IAction;
import landmaster.plustic.tools.stats.BatteryCellMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

public class SetEnergyAction implements IAction {
    private final ITICMaterial mat;
    private final int energy;

    public SetEnergyAction(ITICMaterial mat, int energy) {
        this.mat = mat;
        this.energy = energy;
    }

    @Override
    public void apply() {
        //BatteryCellMaterialStats oldStats = ((Material)mat.getInternal()).getStatsOrUnknown(BatteryCellMaterialStats.TYPE);
        BatteryCellMaterialStats newStats = new BatteryCellMaterialStats(this.energy);
        ((Material) mat.getInternal()).addStats(newStats);
    }

    @Override
    public String describe() {
        return "Setting battery cell energy capacity of " + mat.getName() + " to " + energy;
    }

}
