package queengooborg.plusticreforged;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import queengooborg.plusticreforged.config.ModInfo;
import queengooborg.plusticreforged.generator.*;
import slimeknights.tconstruct.library.client.data.material.MaterialPartTextureGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ModInfo.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlusTiCReforged {
	public PlusTiCReforged() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		// Load the materials and modifiers before registering the registries
		new Resources();

		// Register all the resource registries
		Registries.FLUIDS.register(modEventBus);
		Registries.ITEMS.register(modEventBus);
		Registries.BLOCKS.register(modEventBus);
		Registries.MODIFIERS.register(modEventBus);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	// Gather data to be generated
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();

		if (event.includeClient()) {
			// Generate client-side data

			// Generate language file
			gen.addProvider(new GeneratorLang(gen));

			// Generate models and textures
			gen.addProvider(new GeneratorItemModels(gen, event.getExistingFileHelper()));
			gen.addProvider(new GeneratorBlockStates(gen, event.getExistingFileHelper()));
			GeneratorMaterialTextures materialSprites = new GeneratorMaterialTextures();
			gen.addProvider(new GeneratorRenderInfo(gen, materialSprites));

			// Generate parts with our new materials
			gen.addProvider(new MaterialPartTextureGenerator(gen, event.getExistingFileHelper(), new TinkerPartSpriteProvider(), materialSprites));
		}

		if (event.includeServer()) {
			// Generate server-side data

			// Generate material information
			AbstractMaterialDataProvider materialData = new GeneratorMaterials(gen);
			gen.addProvider(materialData);
			gen.addProvider(new GeneratorMaterialStats(gen, materialData));
			gen.addProvider(new GeneratorMaterialTraits(gen, materialData));
			gen.addProvider(new GeneratorRecipes(gen));

			// Generate tags
			gen.addProvider(new GeneratorFluidTags(gen, event.getExistingFileHelper()));
		}
	}
}
