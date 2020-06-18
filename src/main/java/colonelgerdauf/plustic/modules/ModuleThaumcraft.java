package landmaster.plustic.modules;

import static slimeknights.tconstruct.library.utils.HarvestLevels.*;

import landmaster.plustic.*;
import landmaster.plustic.config.*;
import landmaster.plustic.fluids.*;
import landmaster.plustic.traits.*;
import landmaster.plustic.util.*;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

public class ModuleThaumcraft implements IModule {
	public void init() {
		if (Config.thaumcraft && Loader.isModLoaded("thaumcraft")) {
			Material thaumium = new Material("thaumium", 0x1E0066);
			thaumium.addTrait(Thaumic.thaumic);
			thaumium.setCraftable(false).setCastable(true);
			thaumium.addItem("ingotThaumium", 1, Material.VALUE_Ingot);
			PlusTiC.proxy.setRenderInfo(thaumium, 0x1E0066);
			
			FluidMolten thaumiumFluid = Utils.fluidMetal("thaumium", 0x1E0066);
			thaumiumFluid.setTemperature(945);
			Utils.initFluidMetal(thaumiumFluid);
			thaumium.setFluid(thaumiumFluid);
			
			TinkerRegistry.addMaterialStats(thaumium,
					new HeadMaterialStats(400, 7, 4.25f, OBSIDIAN),
					new HandleMaterialStats(1.0f, 30),
					new ExtraMaterialStats(111),
					new BowMaterialStats(0.7f, 1.3f, 7));
			
			PlusTiC.materials.put("thaumium", thaumium);
		}
	}
}
