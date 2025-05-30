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

public class ManasteelMaterial extends Material {
	public ManasteelMaterial() {
		super(
				"manasteel",
				"Manasteel",
				new Description("", ""),
				new ItemTag("forge", "ingots/manasteel"),
				null,
				2,
				MaterialType.METAL,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(84, 229, 255)),
				new MaterialStats(new HeadMaterialStats(540, 7, Tiers.NETHERITE, 6), new HandleMaterialStats(1.25f, 1, 1, 1), ExtraMaterialStats.DEFAULT),
				Resources.getModifier("mana"),
				681
		);
	}
}

