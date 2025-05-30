package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;

public class GeneratorRenderInfo extends AbstractMaterialRenderInfoProvider {
	public GeneratorRenderInfo(DataGenerator gen, AbstractMaterialSpriteProvider spriteProvider) {
		super(gen, spriteProvider);
	}

	@Override
	protected void addMaterialRenderInfo() {
		for (Material material : Resources.MATERIALS) {
			buildRenderInfo(material.resourceLocation).color(material.color.base.getRGB()).luminosity(material.color.luminosity);
		}
	}

	@Override
	public String getName() {
		return "PlusTiC Reforged Render Info";
	}
}
