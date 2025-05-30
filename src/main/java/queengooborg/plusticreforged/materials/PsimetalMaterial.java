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

public class PsimetalMaterial extends Material {
	public PsimetalMaterial() {
		super(
				"psimetal",
				"Psimetal",
				new Description("A metal infused with psionic energy.", ""),
				new ItemTag("forge", "ingots/psimetal"),
				null,
				2,
				MaterialType.METAL,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(109, 158, 255)),
				new MaterialStats(new HeadMaterialStats(620, 7, Tiers.NETHERITE, 5), new HandleMaterialStats(1.3f, 1, 1, 1), ExtraMaterialStats.DEFAULT),
				new Modifier[]{Resources.getModifier("psicological"), Resources.getModifier("global")},
				696
		);
	}
}
