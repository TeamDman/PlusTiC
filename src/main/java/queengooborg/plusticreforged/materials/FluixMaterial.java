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

public class FluixMaterial extends Material {
	public FluixMaterial() {
		super(
				"fluix",
				"Fluix",
				new Description("A supercharged quartz-like crystal.", "Can capture an entity to transport it easily."),
				new ItemTag("forge", "gems/fluix"),
				null,
				5,
				MaterialType.CRYSTAL,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(61, 0, 153, 136)),
				new MaterialStats(new HeadMaterialStats(700, 7, Tiers.NETHERITE, 6.2f), new HandleMaterialStats(1, 1, 1, 1), ExtraMaterialStats.DEFAULT),
				Resources.getModifier("portly"),
				1000
		);
	}
}
