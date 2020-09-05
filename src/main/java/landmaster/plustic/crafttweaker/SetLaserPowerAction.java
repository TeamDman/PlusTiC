package landmaster.plustic.crafttweaker;

import com.blamejared.compat.tconstruct.materials.*;
import crafttweaker.IAction;
import landmaster.plustic.tools.stats.LaserMediumMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

public class SetLaserPowerAction implements IAction {
    private final ITICMaterial mat;
    private final float power;

    public SetLaserPowerAction(ITICMaterial mat, float power) {
        this.mat = mat;
        this.power = power;
    }

    @Override
    public void apply() {
        LaserMediumMaterialStats oldStats = ((Material) mat.getInternal()).getStatsOrUnknown(LaserMediumMaterialStats.TYPE);
        LaserMediumMaterialStats newStats = new LaserMediumMaterialStats(this.power, oldStats.range);
        ((Material) mat.getInternal()).addStats(newStats);
    }

    @Override
    public String describe() {
        return "Setting laser power of " + mat.getName() + " to " + power;
    }
}
