package colonelgerdauf.plustic.crafttweaker;

import com.blamejared.compat.tconstruct.materials.*;

import crafttweaker.*;
import colonelgerdauf.plustic.tools.stats.*;
import slimeknights.tconstruct.library.materials.*;

public class SetLaserRangeAction implements IAction {
	private final ITICMaterial mat;
	private final float range;
	
	public SetLaserRangeAction(ITICMaterial mat, float range) {
		this.mat = mat;
		this.range = range;
	}
	
	@Override
	public void apply() {
		LaserMediumMaterialStats oldStats = ((Material)mat.getInternal()).getStatsOrUnknown(LaserMediumMaterialStats.TYPE);
		LaserMediumMaterialStats newStats = new LaserMediumMaterialStats(oldStats.power, this.range);
		((Material)mat.getInternal()).addStats(newStats);
	}
	
	@Override
	public String describe() {
		return "Setting laser range of " + mat.getName() + " to " + range;
	}
}
