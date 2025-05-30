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

public class CertusQuartzMaterial extends Material {
	public CertusQuartzMaterial() {
		super(
				"certus_quartz",
				"Certus Quartz",
				new Description("An alternative form of quartz with a slight blue tint.", "Breaks gravity-affected blocks in a column, and has a chance to gain various benefits from a successful attack."),
				new ItemTag("forge", "gems/certus_quartz"),
				null,
				3,
				MaterialType.CRYSTAL,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(211, 231, 255)),
				new MaterialStats(new HeadMaterialStats(250, 6.4f, Tiers.DIAMOND, 4.5f), new HandleMaterialStats(0.8f, 1, 1, 1), ExtraMaterialStats.DEFAULT),
				Resources.getModifier("elemental"),
				1000
		);
	}
}
