package queengooborg.plusticreforged.generator;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;

public class GeneratorMaterialTextures extends AbstractMaterialSpriteProvider {
	@Override
	protected void addAllMaterials() {
		for (Material material : Resources.MATERIALS) {
			buildMaterial(material.resourceLocation).meleeHarvest().ranged().statType(TinkerPartSpriteProvider.PLATE).fallbacks(material.type.fallbacks).colorMapper(material.color.getColorMapper());
		}
	}

	@Override
	public String getName() {
		return "PlusTiC Reforged Material Textures";
	}
}
