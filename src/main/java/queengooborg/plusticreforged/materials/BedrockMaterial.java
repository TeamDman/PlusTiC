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

public class BedrockMaterial extends Material {
	public BedrockMaterial() {
		super(
				"bedrock",
				"Bedrock",
				new Description("You could only obtain these tools through Creative or cheats -- so if you're gonna cheat, you might as well commit, right?", "The tool takes no damage"),
				new ItemID("minecraft", "bedrock"),
				null,
				6,
				MaterialType.STONE,
				ORDER_HARVEST + ORDER_COMPAT,
				new MaterialColors(new Color(60, 60, 60)),
				new MaterialStats(new HeadMaterialStats(99999999, 10, Tiers.NETHERITE, 99), new HandleMaterialStats(9999999, 10, 10, 99), ExtraMaterialStats.DEFAULT),
				Resources.getModifier("invulnerable"),
				1000
		);
	}
}
