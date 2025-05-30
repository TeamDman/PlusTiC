package queengooborg.plusticreforged.materials;

import net.minecraft.world.item.Tiers;
import queengooborg.plusticreforged.api.*;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.awt.*;

import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.ORDER_COMPAT;
import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.ORDER_HARVEST;

public class SapphireMaterial extends Material {
	public SapphireMaterial() {
		super(
				"sapphire",
				"Sapphire",
				new Description("A blue gemstone that is said improve the owner's focus.", ""),
				new ItemTag("forge", "gems/sapphire"),
				null,
				2,
				MaterialType.CRYSTAL,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(1, 1, 200)),
				new MaterialStats(new HeadMaterialStats(700, 5, Tiers.NETHERITE, 6.4f), HandleMaterialStats.DEFAULT, ExtraMaterialStats.DEFAULT),
				new Modifier[]{}, // TinkerModifiers.aquaaffinity.get(),
				750
		);
	}
}
