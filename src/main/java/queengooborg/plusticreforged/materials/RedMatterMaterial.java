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

public class RedMatterMaterial extends Material {
	public RedMatterMaterial() {
		super(
				"red_matter",
				"Red Matter",
				new Description("A very powerful material...and far more dangerous than Dark Matter.", ""),
				new ItemID("projecte", "red_matter"),
				null,
				5,
				MaterialType.CRYSTAL,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(227, 0, 0)),
				new MaterialStats(new HeadMaterialStats(2017, 14, Tiers.NETHERITE, 15), new HandleMaterialStats(2, 1.5f, 1.3f, 1.7f), ExtraMaterialStats.DEFAULT),
				Resources.getModifier("unstable_matter"),
				1100
		);
	}
}
