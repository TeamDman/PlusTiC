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

public class JadeMaterial extends Material {
	public JadeMaterial() {
		super(
				"jade",
				"Jade",
				new Description("A green-ish gemstone that is said to have healing properties...which is ironic, considering this stops other mobs from healing.", ""),
				new ItemTag("forge", "gems/jade"),
				null,
				2,
				MaterialType.CRYSTAL,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(1, 170, 1)),
				new MaterialStats(new HeadMaterialStats(1600, 8, Tiers.NETHERITE, 5), new HandleMaterialStats(1.35f, 1.1f, 1, 1), ExtraMaterialStats.DEFAULT),
				Resources.getModifier("jaded"),
				750
		);
	}
}
