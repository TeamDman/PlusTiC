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

public class RubyMaterial extends Material {
	public RubyMaterial() {
		super(
				"ruby",
				"Ruby",
				new Description("A red gemstone that is said to increase the owner's vitality.", ""),
				new ItemTag("forge", "gems/ruby"),
				null,
				2,
				MaterialType.CRYSTAL,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(200, 1, 1)),
				new MaterialStats(new HeadMaterialStats(660, 4.6f, Tiers.NETHERITE, 6.4f), new HandleMaterialStats(1.2f, 1.2f, 1, 1.4f), ExtraMaterialStats.DEFAULT),
				Resources.getModifier("bloodymary"), // new Modifier[]{Resources.getModifier("bloodymary"), TinkerModifiers.sharpness.get()},
				750
		);
	}
}
