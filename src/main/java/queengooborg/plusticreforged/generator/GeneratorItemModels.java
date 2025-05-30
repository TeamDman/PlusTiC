package queengooborg.plusticreforged.generator;

import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BucketItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.CustomLoaderBuilder;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import queengooborg.plusticreforged.Resources;
import queengooborg.plusticreforged.api.Fluid;
import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.config.ModInfo;

public class GeneratorItemModels extends ItemModelProvider {
	public GeneratorItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, ModInfo.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		for (Material material : Resources.MATERIALS) {
			if (material.moltenFluid == null) {
				continue;
			}

			bucketModel(material.moltenFluid);
		}
	}

	public void bucketModel(Fluid fluid) {
		RegistryObject<BucketItem> reg = fluid.FLUID_BUCKET;
		assert reg != null;
		ModelBuilder<ItemModelBuilder> builder = getBuilder(reg.getId().getPath()).parent(getExistingFile(new ResourceLocation(ModInfo.MOD_ID, fluid.isHot() ? "item/bucket_hot_fluid" : "item/bucket_fluid")));

		// Shamelessly copied from Materialis
		builder.customLoader((t, u) -> new CustomLoaderBuilder(t.getLocation(), t, u) {
			public JsonObject toJson(JsonObject json) {
				json.addProperty("loader", "forge:bucket");
				json.addProperty("fluid", reg.get().getFluid().getRegistryName().toString());
				return json;
			}
		});
	}
}
