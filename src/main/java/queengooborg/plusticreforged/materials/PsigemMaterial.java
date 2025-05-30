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

public class PsigemMaterial extends Material {
	public PsigemMaterial() {
		super(
				"psigem",
				"Psigem",
				new Description("A gemstone that resonates with psionic energy.", ""),
				new ItemTag("forge", "gems/psigem"),
				null,
				2,
				MaterialType.CRYSTAL,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(8, 67, 163)),
				new MaterialStats(new HeadMaterialStats(620, 7, Tiers.NETHERITE, 5), new HandleMaterialStats(1.3f, 1, 1, 1), ExtraMaterialStats.DEFAULT),
				new Modifier[]{Resources.getModifier("psicological"), Resources.getModifier("portly")},
				750
		);
	}
}
