package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;

public class GeneratorMaterialStats extends AbstractMaterialStatsDataProvider {
	public GeneratorMaterialStats(DataGenerator gen, AbstractMaterialDataProvider materialData) {
		super(gen, materialData);
	}

	@Override
	protected void addMaterialStats() {
		for (Material material : Resources.MATERIALS) {
			addMaterialStats(material.resourceLocation, material.stats.head, material.stats.handle, material.stats.extra);
		}
	}

	@Override
	public String getName() {
		return "PlusTiC Reforged Material Stats";
	}
}
