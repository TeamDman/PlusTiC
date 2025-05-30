package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

public class GeneratorMaterials extends AbstractMaterialDataProvider {
	private static final Logger log = LogManager.getLogger(GeneratorMaterials.class);

	public GeneratorMaterials(DataGenerator gen) {
		super(gen);
	}

	@Override
	protected void addMaterials() {
		for (Material material : Resources.MATERIALS) {
			log.info("Adding material {}", material.id);
			addMaterial(material.resourceLocation, material.tier, material.order, false, false, material.condition);
			// XXX Need to re-add material colors...?
			// addMaterial(material.resourceLocation, material.tier, material.order, false, material.color.base.getRGB(), false, material.condition);
		}
	}

	@Override
	public String getName() {
		return "PlusTiC Reforged Materials";
	}
}
