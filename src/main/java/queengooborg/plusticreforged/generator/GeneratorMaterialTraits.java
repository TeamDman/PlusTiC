package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;

public class GeneratorMaterialTraits extends AbstractMaterialTraitDataProvider {
	private static final Logger log = LogManager.getLogger(GeneratorMaterialTraits.class);

	public GeneratorMaterialTraits(DataGenerator gen, AbstractMaterialDataProvider materialData) {
		super(gen, materialData);
	}

	@Override
	protected void addMaterialTraits() {
		for (Material material : Resources.MATERIALS) {
			if (material.modifiers.length == 0) {
				log.info("No modifiers found for material {}", material.id);
				noTraits(material.resourceLocation);
			} else {
				log.info("Adding {} modifiers to material {}", material.modifiers.length, material.id);
				addDefaultTraits(material.resourceLocation, material.getModifiers());
			}
		}
	}

	@Override
	public String getName() {
		return "PlusTiC Reforged Material Traits";
	}
}
