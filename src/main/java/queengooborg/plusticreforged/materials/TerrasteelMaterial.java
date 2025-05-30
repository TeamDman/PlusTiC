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

public class TerrasteelMaterial extends Material {
	public TerrasteelMaterial() {
		super(
				"terrasteel",
				"Terrasteel",
				new Description("", ""),
				new ItemTag("forge", "ingots/terrasteel"),
				null,
				2,
				MaterialType.METAL,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(0, 255, 0)),
				new MaterialStats(new HeadMaterialStats(1562, 9, Tiers.NETHERITE, 6.5f), new HandleMaterialStats(1.4f, 1, 1, 1), ExtraMaterialStats.DEFAULT),
				new Modifier[]{Resources.getModifier("mana"), Resources.getModifier("terrafirma")},
				760
		);
	}
}