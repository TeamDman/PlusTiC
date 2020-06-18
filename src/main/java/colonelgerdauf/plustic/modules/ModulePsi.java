package landmaster.plustic.modules;

import static slimeknights.tconstruct.library.utils.HarvestLevels.*;

import java.util.stream.*;

import landmaster.plustic.*;
import landmaster.plustic.config.*;
import landmaster.plustic.fluids.*;
import landmaster.plustic.tools.stats.*;
import landmaster.plustic.traits.*;
import landmaster.plustic.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.oredict.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.materials.*;

public class ModulePsi implements IModule {
	static {
		MinecraftForge.EVENT_BUS.register(ModulePsi.class);
	}

	public void init() {
		if (Config.psi && (Loader.isModLoaded("Psi") || Loader.isModLoaded("psi"))) {
			Material psimetal = new Material("psimetal", 0x6D9EFF);
			psimetal.addTrait(Psicological.psicological);
			psimetal.addTrait(Global.global);
			psimetal.addItem("ingotPsi", 1, Material.VALUE_Ingot);
			psimetal.setCraftable(false).setCastable(true);
			Utils.setDispItem(psimetal, "ingotPsi");
			PlusTiC.proxy.setRenderInfo(psimetal, 0x6D9EFF);
			
			FluidMolten psimetalFluid = Utils.fluidMetal("psimetal", 0x6D9EFF);
			psimetalFluid.setTemperature(696);
			Utils.initFluidMetal(psimetalFluid);
			psimetal.setFluid(psimetalFluid);
			
			TinkerRegistry.addMaterialStats(psimetal,
					new HeadMaterialStats(620, 7f, 5, OBSIDIAN),
					new HandleMaterialStats(1.3f, -10),
					new ExtraMaterialStats(30),
					new BowMaterialStats(1, 1.6f, 4));
			
			PlusTiC.materials.put("psi", psimetal);
			
			Material psigem = new Material("psigem", 0x0843A3);
			psigem.addTrait(Psicological.psicological);
			psigem.addTrait(Portly.portly);
			psigem.addItem("gemPsi", 1, Material.VALUE_Ingot);
			psigem.setCraftable(true);
			new OreRegisterPromise("gemPsi").thenAccept(psigem::setRepresentativeItem);
			PlusTiC.proxy.setRenderInfo(psigem, 0x0843A3);
			
			TinkerRegistry.addMaterialStats(psigem,
					new HeadMaterialStats(620, 7f, 5, OBSIDIAN),
					new HandleMaterialStats(1.3f, -10),
					new ExtraMaterialStats(30),
					new BowMaterialStats(1, 1.6f, 4),
					new LaserMediumMaterialStats(5.4f, 23));
			
			PlusTiC.materials.put("psiGem", psigem);
		}
	}
	
	@SubscribeEvent
	public static void meltingRecipeRegister(TinkerRegisterEvent.MeltingRegisterEvent event) {
		if (Config.psi && (Loader.isModLoaded("Psi") || Loader.isModLoaded("psi"))) {
			if (Stream.concat(OreDictionary.getOres("dustPsi", false).stream(),
					OreDictionary.getOres("blockPsiDust", false).stream())
					.anyMatch(event.getRecipe()::matches)
					&& Loader.instance().activeModContainer() != null
					&& Loader.instance().activeModContainer().getModId().equalsIgnoreCase("tconstruct")) {
				event.setCanceled(true); // remove dust recipes
			}
		}
	}
}
