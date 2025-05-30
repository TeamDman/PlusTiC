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

public class ElementiumMaterial extends Material {
	public ElementiumMaterial() {
		super(
				"elementium",
				"Elementium",
				new Description("", ""),
				new ItemTag("forge", "ingots/elementium"),
				null,
				5,
				MaterialType.METAL,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(246, 106, 253)),
				new MaterialStats(new HeadMaterialStats(540, 7, Tiers.NETHERITE, 6), new HandleMaterialStats(1.25f, 1, 1, 1), ExtraMaterialStats.DEFAULT),
				new Modifier[]{Resources.getModifier("mana"), Resources.getModifier("elemental")},
				800
		);
	}
}