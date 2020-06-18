package colonelgerdauf.plustic.modules;

import static slimeknights.tconstruct.library.utils.HarvestLevels.*;

import colonelgerdauf.plustic.*;
import colonelgerdauf.plustic.config.*;
import colonelgerdauf.plustic.tools.stats.*;
import colonelgerdauf.plustic.traits.*;
import colonelgerdauf.plustic.util.Utils;
import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

public class ModuleGemsPlus implements IModule {
	
	public void init() {
		if (Config.gemsplus && Loader.isModLoaded("gemsplus")) {
			Material phoenixite = new Material("phoenixite", 0xFF4511);
			phoenixite.addTrait(GetLucky.getlucky);
			phoenixite.addItem("gemPhoenixite", 1, Material.VALUE_Ingot);
			phoenixite.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(phoenixite, 0xFF4511);
			
			TinkerRegistry.addMaterialStats(phoenixite,
					new HeadMaterialStats(1300, 9, 5, COBALT),
					new HandleMaterialStats(1.4f, 130),
					new ExtraMaterialStats(77),
					new BowMaterialStats(1.3f, 1.3f, 5.5f),
					new LaserMediumMaterialStats(11, 20),
					new BatteryCellMaterialStats(80000));
			
			PlusTiC.materials.put("phoenixite", phoenixite);
		}
	}
	
	public void init2() {
		Utils.setDispItem(PlusTiC.materials.get("phoenixite"), "gemPhoenixite");
	}
	
}
