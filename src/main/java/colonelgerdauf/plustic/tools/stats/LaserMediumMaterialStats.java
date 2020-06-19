package colonelgerdauf.plustic.tools.stats;

import java.util.*;

import com.google.common.collect.*;

import net.minecraft.client.resources.*;
import slimeknights.tconstruct.library.materials.*;

public class LaserMediumMaterialStats extends AbstractMaterialStats {
	public static final String TYPE = "laser_medium";
	
	private static float maxRange = 20;
	
	public static float getMaxRange() { return maxRange; }
	
	public final float power, range;
	
	static {
		Material.UNKNOWN.addStats(new LaserMediumMaterialStats(0, 0));
	}
	
	public LaserMediumMaterialStats(float power, float range) {
		super(TYPE);
		this.power = power;
		this.range = range;
		maxRange = Math.max(maxRange, range);
	}
	
	@Override
	public List<String> getLocalizedInfo() {
		return ImmutableList.of(I18n.format("stat.laser_medium.power.name", power),
				I18n.format("stat.laser_medium.range.name", range));
	}

	@Override
	public List<String> getLocalizedDesc() {
		return ImmutableList.of(I18n.format("stat.laser_medium.power.desc"),
				I18n.format("stat.laser_medium.range.desc"));
	}
}
