package queengooborg.plusticreforged.generator;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.config.ModInfo;
import slimeknights.tconstruct.common.TinkerTags;

public class GeneratorFluidTags extends FluidTagsProvider {
	public GeneratorFluidTags(DataGenerator gen, ExistingFileHelper existingFileHelper) {
		super(gen, ModInfo.MOD_ID, existingFileHelper);
	}

	public void addTags() {
		for (Material material : Resources.MATERIALS) {
			if (material.moltenFluid == null) {
				continue;
			}

			tag(material.moltenFluid.getLocalTag()).add(material.moltenFluid.FLUID.get());
			tag(material.moltenFluid.getForgeTag()).add(material.moltenFluid.FLUID.get());
			tag(TinkerTags.Fluids.METAL_TOOLTIPS).addTag(material.moltenFluid.getForgeTag());
		}
	}

	@Override
	public String getName() {
		return "PlusTiC Reforged Fluid Tags";
	}
}
