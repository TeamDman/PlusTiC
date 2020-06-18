package landmaster.plustic.modules;

import static slimeknights.tconstruct.library.materials.MaterialTypes.*;
import static slimeknights.tconstruct.library.utils.HarvestLevels.*;

import java.util.concurrent.*;

import landmaster.plustic.*;
import landmaster.plustic.api.*;
import landmaster.plustic.config.*;
import landmaster.plustic.tools.stats.*;
import landmaster.plustic.traits.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public class ModuleProjectE implements IModule {
	
	private static final CompletableFuture<?> regPromise = new CompletableFuture<>();
	
	public void init() {
		if (Config.projectE && Loader.isModLoaded("projecte")) {
			Material darkMatter = new Material("darkMatter", 0x270133);
			Material redMatter = new Material("redMatter", 0xE30000);
			
			final CompletableFuture<?> integrationPromise = regPromise.thenRun(() -> {
				final Item matter = Item.REGISTRY.getObject(new ResourceLocation("projecte", "item.pe_matter"));
				
				darkMatter.addTrait(Ignoble.ignoble, HEAD);
				darkMatter.addTrait(DarkTraveler.darktraveler);
				darkMatter.addItem(matter, 1, Material.VALUE_Ingot);
				darkMatter.setCraftable(true);
				darkMatter.setRepresentativeItem(matter);
				PlusTiC.proxy.setRenderInfo(darkMatter, 0x270133);
				
				TinkerRegistry.addMaterialStats(darkMatter,
						new HeadMaterialStats(1729, 10, 10.5f, COBALT),
						new HandleMaterialStats(1.7f, 289),
						new ExtraMaterialStats(111),
						new BowMaterialStats(1.0f, 1.1f, 10),
						new LaserMediumMaterialStats(6, 130));
				
				final ItemStack redMatterStack = new ItemStack(matter, 1, 1);
				
				redMatter.addTrait(DPRK.dprk);
				redMatter.addItem(redMatterStack, 1, Material.VALUE_Ingot);
				redMatter.setCraftable(true);
				redMatter.setRepresentativeItem(redMatterStack);
				PlusTiC.proxy.setRenderInfo(redMatter, 0xE30000);
				
				TinkerRegistry.addMaterialStats(redMatter,
						new HeadMaterialStats(2017, 14, 15, COBALT),
						new HandleMaterialStats(2, -53),
						new ExtraMaterialStats(105),
						new BowMaterialStats(1.0f, 2.0f, 13.7f));
			});
			
			PlusTiC.materials.put("darkMatter", darkMatter);
			PlusTiC.materials.put("redMatter", redMatter);
			
			PlusTiC.materialIntegrationStages.put("darkMatter", integrationPromise);
			PlusTiC.materialIntegrationStages.put("redMatter", integrationPromise);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onRegItems(RegistryEvent.Register<Item> event) {
		regPromise.complete(null);
	}
}
