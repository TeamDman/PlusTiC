package queengooborg.plusticreforged.materials;

import net.minecraft.world.item.Tiers;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.*;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.awt.*;

import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.ORDER_COMPAT;
import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.ORDER_HARVEST;

public class TopazMaterial extends Material {
	public TopazMaterial() {
		super(
				"topaz",
				"Topaz",
				new Description("Golden topaz, a gemstone that helps channel the power of manifestation.", ""),
				new ItemTag("forge", "gems/topaz"),
				null,
				2,
				MaterialType.CRYSTAL,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(255, 255, 0)),
				new MaterialStats(new HeadMaterialStats(690, 6, Tiers.NETHERITE, 6), new HandleMaterialStats(0.8f, 1, 1, 1), ExtraMaterialStats.DEFAULT),
				Resources.getModifier("naturespower"),
				750
		);
	}
}
