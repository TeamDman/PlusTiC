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

public class PhoenixiteMaterial extends Material {
	public PhoenixiteMaterial() {
		super(
				"phoenixite",
				"Phoenixite",
				new Description("A fiery gemstone that is said to be reborn from its ashes.", ""),
				new ItemTag("forge", "gems/phoenixite"),
				null,
				2,
				MaterialType.CRYSTAL,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(255, 69, 17)),
				new MaterialStats(new HeadMaterialStats(1300, 9, Tiers.NETHERITE, 5), new HandleMaterialStats(1.4f, 1, 1, 1), ExtraMaterialStats.DEFAULT),
				Resources.getModifier("fromtheashes"),
				750
		);
	}
}
