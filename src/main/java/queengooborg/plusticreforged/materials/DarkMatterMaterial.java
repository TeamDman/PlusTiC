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

public class DarkMatterMaterial extends Material {
	public DarkMatterMaterial() {
		super(
				"dark_matter",
				"Dark Matter",
				new Description("A mysterious substance that seems to defy the laws of physics.", ""),
				new ItemID("projecte", "dark_matter"),
				null,
				4,
				MaterialType.CRYSTAL,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(39, 1, 51)),
				new MaterialStats(new HeadMaterialStats(1729, 10, Tiers.NETHERITE, 10.5f), new HandleMaterialStats(1.7f, 1.2f, 1, 1.5f), ExtraMaterialStats.DEFAULT),
				new Modifier[]{Resources.getModifier("ignoble"), Resources.getModifier("dark_traveler")},
				1000
		);
	}
}
