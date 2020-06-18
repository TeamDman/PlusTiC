package colonelgerdauf.plustic.modules;

import colonelgerdauf.plustic.config.*;
import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.shared.*;

public class ModuleSurvivalist implements IModule {
	public void init() {
		if (Config.survivalist && Loader.isModLoaded("survivalist")) {
			// add melting recipes
			TinkerRegistry.registerMelting("rockOreIron", TinkerFluids.iron, Material.VALUE_Nugget*2);
			TinkerRegistry.registerMelting("rockOreGold", TinkerFluids.gold, Material.VALUE_Nugget*2);
			TinkerRegistry.registerMelting("rockOreCopper", TinkerFluids.copper, Material.VALUE_Nugget*2);
			TinkerRegistry.registerMelting("rockOreTin", TinkerFluids.tin, Material.VALUE_Nugget*2);
			TinkerRegistry.registerMelting("rockOreLead", TinkerFluids.lead, Material.VALUE_Nugget*2);
			TinkerRegistry.registerMelting("rockOreSilver", TinkerFluids.silver, Material.VALUE_Nugget*2);
		}
	}
}
