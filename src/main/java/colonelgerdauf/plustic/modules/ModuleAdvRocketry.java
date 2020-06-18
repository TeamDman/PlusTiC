package colonelgerdauf.plustic.modules;

import static slimeknights.tconstruct.library.materials.MaterialTypes.*;
import static slimeknights.tconstruct.library.utils.HarvestLevels.*;

import colonelgerdauf.plustic.*;
import colonelgerdauf.plustic.config.*;
import colonelgerdauf.plustic.fluids.*;
import colonelgerdauf.plustic.traits.*;
import colonelgerdauf.plustic.util.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

public class ModuleAdvRocketry implements IModule {

	public void init() {
		if (Config.advancedRocketry &&
				(Loader.isModLoaded("libVulpes") || Loader.isModLoaded("libvulpes"))) {
			if (TinkerRegistry.getMaterial("titanium") == Material.UNKNOWN) {
				Material titanium = new Material("titanium", TextFormatting.WHITE);
				titanium.addTrait(Light.light);
				titanium.addTrait(Anticorrosion.anticorrosion, HEAD);
				titanium.addItem("ingotTitanium", 1, Material.VALUE_Ingot);
				titanium.setCraftable(false).setCastable(true);
				Utils.setDispItem(titanium, "libvulpes", "productingot", 7);
				PlusTiC.proxy.setRenderInfo(titanium, 0xDCE1EA);
				
				FluidMolten titaniumFluid = Utils.fluidMetal("titanium", 0xDCE1EA);
				titaniumFluid.setTemperature(790);
				Utils.initFluidMetal(titaniumFluid);
				titanium.setFluid(titaniumFluid);
				
				TinkerRegistry.addMaterialStats(titanium, new HeadMaterialStats(560, 6, 6, OBSIDIAN));
				TinkerRegistry.addMaterialStats(titanium, new HandleMaterialStats(1.4f, 0));
				TinkerRegistry.addMaterialStats(titanium, new ExtraMaterialStats(40));
				TinkerRegistry.addMaterialStats(titanium, new BowMaterialStats(1.15f, 1.3f, 6.6f));
				TinkerRegistry.addMaterialStats(titanium, new FletchingMaterialStats(1.0f, 1.3f));
				
				PlusTiC.materials.put("titanium", titanium);
			}
		}
	}
	
}
