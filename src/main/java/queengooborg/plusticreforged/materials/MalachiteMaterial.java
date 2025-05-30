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

public class MalachiteMaterial extends Material {
	public MalachiteMaterial() {
		super(
				"malachite",
				"Malachite",
				new Description("A dark green gemstone that is said to protect against negative energy.", ""),
				new ItemTag("forge", "gems/malachite"),
				null,
				2,
				MaterialType.CRYSTAL,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(0, 117, 35)),
				new MaterialStats(new HeadMaterialStats(640, 3, Tiers.NETHERITE, 6.1f), new HandleMaterialStats(1.3f, 1.5f, 1, 1), ExtraMaterialStats.DEFAULT),
				Resources.getModifier("natureswrath"),
				750
		);
	}
}
