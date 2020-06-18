package landmaster.plustic.tools.stats;

import java.util.*;

import com.google.common.collect.*;

import net.minecraft.client.resources.*;
import slimeknights.tconstruct.library.materials.*;

public class BatteryCellMaterialStats extends AbstractMaterialStats {
	public static final String TYPE = "battery_cell";
	
	public final int energy;
	
	static {
		Material.UNKNOWN.addStats(new BatteryCellMaterialStats(0));
	}
	
	public BatteryCellMaterialStats(int energy) {
		super(TYPE);
		this.energy = energy;
	}
	
	@Override
	public List<String> getLocalizedInfo() {
		return ImmutableList.of(I18n.format("stat.battery_cell.energy.name", energy));
	}
	
	@Override
	public List<String> getLocalizedDesc() {
		return ImmutableList.of(I18n.format("stat.battery_cell.energy.desc"));
	}
	
}
