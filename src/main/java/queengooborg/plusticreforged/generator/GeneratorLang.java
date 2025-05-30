package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.api.Modifier;
import queengooborg.plusticreforged.config.ModInfo;

public class GeneratorLang extends LanguageProvider {
	public GeneratorLang(DataGenerator gen) {
		super(gen, ModInfo.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		for (Material material : Resources.MATERIALS) {
			add(String.format("material.%s.%s", ModInfo.MOD_ID, material.id), material.name);
			if (!material.description.flavor.isEmpty()) {
				add(String.format("material.%s.%s.flavor", ModInfo.MOD_ID, material.id), material.description.flavor);
			}
			if (!material.description.traits.isEmpty()) {
				add(String.format("material.%s.%s.encyclopedia", ModInfo.MOD_ID, material.id), material.description.traits);
			}

			if (material.moltenFluid != null && material.moltenFluid.id != null) {
				addBlock(material.moltenFluid.FLUID_BLOCK, material.moltenFluid.materialName);
				addItem(material.moltenFluid.FLUID_BUCKET, "Molten " + material.moltenFluid.materialName + " Bucket");
				add(String.format("fluid.%s.%s", ModInfo.MOD_ID, material.moltenFluid.id), "Molten " + material.moltenFluid.materialName);
			}
		}

		for (Modifier modifier : Resources.MODIFIERS) {
			add(String.format("modifier.%s.%s", ModInfo.MOD_ID, modifier.id), modifier.name);
			if (modifier.usable) {
				if (!modifier.description.flavor.isEmpty()) {
					add(String.format("modifier.%s.%s.flavor", ModInfo.MOD_ID, modifier.id), modifier.description.flavor);
				}
			} else {
				// XXX Remove this once all modifiers have been converted
				String warning = "THIS MODIFIER HAS NOT BEEN CONVERTED TO THE NEW SYSTEM AND IS NOT USABLE YET!";
				if (modifier.description.flavor.isEmpty()) {
					add(String.format("modifier.%s.%s.flavor", ModInfo.MOD_ID, modifier.id), warning);
				} else {
					add(String.format("modifier.%s.%s.flavor", ModInfo.MOD_ID, modifier.id), warning + "\n\n" + modifier.description.flavor);
				}
			}
			if (!modifier.description.traits.isEmpty()) {
				add(String.format("modifier.%s.%s.description", ModInfo.MOD_ID, modifier.id), modifier.description.traits);
			}
		}
	}

	@Override
	public String getName() {
		return "PlusTiC Reforged Language File Generator";
	}
}
