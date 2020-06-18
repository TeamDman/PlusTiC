package landmaster.plustic.modules;

import static slimeknights.tconstruct.library.utils.HarvestLevels.*;

import landmaster.plustic.*;
import landmaster.plustic.config.*;
import landmaster.plustic.tools.stats.*;
import landmaster.plustic.traits.*;
import landmaster.plustic.util.*;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

public class ModuleAppEng2 implements IModule {
	public void init() {
		if (Config.appEng2 && Loader.isModLoaded("appliedenergistics2")) {
			Material certusQuartz = new Material("certusQuartz_plustic", 0x55D3E7FF);
			certusQuartz.addTrait(Elemental.elemental);
			certusQuartz.setCraftable(true);
			new OreRegisterPromise("crystalCertusQuartz")
			.thenAccept(quartz -> {
				certusQuartz.addItem("crystalCertusQuartz", 1, Material.VALUE_Ingot);
				certusQuartz.setRepresentativeItem(quartz);
			});
			PlusTiC.proxy.setRenderInfo(certusQuartz, 0x55D3E7FF);
			TinkerRegistry.addMaterialStats(certusQuartz, new HeadMaterialStats(250, 6.4f, 4.5f, DIAMOND),
					new HandleMaterialStats(0.8f, 80),
					new ExtraMaterialStats(70),
					PlusTiC.justWhy,
					new LaserMediumMaterialStats(5, 14));
			PlusTiC.materials.put("certusQuartz", certusQuartz);
			
			Material fluixCrystal = new Material("fluixCrystal_plustic", 0x883D0099);
			fluixCrystal.addTrait(Portly.portly);
			fluixCrystal.setCraftable(true);
			new OreRegisterPromise("crystalFluix")
			.thenAccept(crystal -> {
				fluixCrystal.addItem("crystalFluix", 1, Material.VALUE_Ingot);
				fluixCrystal.setRepresentativeItem(crystal);
			});
			PlusTiC.proxy.setRenderInfo(fluixCrystal, 0x883D0099);
			TinkerRegistry.addMaterialStats(fluixCrystal, new HeadMaterialStats(700, 7, 6.2f, OBSIDIAN),
					new HandleMaterialStats(1.0f, 0),
					new ExtraMaterialStats(75),
					PlusTiC.justWhy,
					new BatteryCellMaterialStats(80000),
					new LaserMediumMaterialStats(7, 16));
			PlusTiC.materials.put("fluixCrystal", fluixCrystal);
		}
	}
}
