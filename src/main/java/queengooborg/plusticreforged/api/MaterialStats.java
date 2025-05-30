package queengooborg.plusticreforged.api;

import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

public class MaterialStats {
	public HeadMaterialStats head = HeadMaterialStats.DEFAULT;
	public HandleMaterialStats handle = HandleMaterialStats.DEFAULT;
	public ExtraMaterialStats extra = ExtraMaterialStats.DEFAULT;

	public MaterialStats() {
	}

	public MaterialStats(HeadMaterialStats head, HandleMaterialStats handle, ExtraMaterialStats extra) {
		if (head != null) this.head = head;
		if (handle != null) this.handle = handle;
		if (extra != null) this.extra = extra;
	}
}