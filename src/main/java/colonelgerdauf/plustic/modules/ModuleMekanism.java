package colonelgerdauf.plustic.modules;

import static slimeknights.tconstruct.library.materials.MaterialTypes.*;
import static slimeknights.tconstruct.library.utils.HarvestLevels.*;
import static slimeknights.tconstruct.tools.TinkerTraits.*;

import java.util.*;

import colonelgerdauf.plustic.*;
import colonelgerdauf.plustic.config.*;
import colonelgerdauf.plustic.fluids.*;
import colonelgerdauf.plustic.tools.stats.*;
import colonelgerdauf.plustic.traits.*;
import colonelgerdauf.plustic.util.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

public class ModuleMekanism implements IModule {
	@Override
	public void init() {
		if (Config.mekanism && (Loader.isModLoaded("Mekanism") || Loader.isModLoaded("mekanism"))) {
			Material osmium = TinkerRegistry.getMaterial("osmium");
			if (osmium == Material.UNKNOWN) {
				osmium = new Material("osmium", TextFormatting.BLUE);
				osmium.addTrait(dense);
				osmium.addTrait(established);
				osmium.addItem("ingotOsmium", 1, Material.VALUE_Ingot);
				osmium.setCraftable(false).setCastable(true);
				PlusTiC.proxy.setRenderInfo(osmium, 0xBFD0FF);
				
				FluidMolten osmiumFluid = Utils.fluidMetal("osmium", 0xBFD0FF);
				osmiumFluid.setTemperature(820);
				Utils.initFluidMetal(osmiumFluid);
				osmium.setFluid(osmiumFluid);
				
				TinkerRegistry.addMaterialStats(osmium,
						new HeadMaterialStats(500, 6, 5.8f, DIAMOND),
						new HandleMaterialStats(1.2f, 45),
						new ExtraMaterialStats(40),
						new BowMaterialStats(0.65f, 1.3f, 5.7f),
						new BatteryCellMaterialStats(80000));
				
				PlusTiC.materials.put("osmium", osmium);
			}
			
			Material refinedObsidian = new Material("refinedObsidian", TextFormatting.LIGHT_PURPLE);
			refinedObsidian.addTrait(dense);
			refinedObsidian.addTrait(duritos);
			refinedObsidian.addItem("ingotRefinedObsidian", 1, Material.VALUE_Ingot);
			refinedObsidian.setCraftable(false).setCastable(true);
			PlusTiC.proxy.setRenderInfo(refinedObsidian, 0x5D00FF);
			
			FluidMolten refinedObsidianFluid = Utils.fluidMetal("refinedObsidian", 0x5D00FF);
			refinedObsidianFluid.setTemperature(860);
			Utils.initFluidMetal(refinedObsidianFluid);
			refinedObsidian.setFluid(refinedObsidianFluid);
			
			TinkerRegistry.addMaterialStats(refinedObsidian,
					new HeadMaterialStats(2500, 7, 11, COBALT),
					new HandleMaterialStats(1.5f, -100),
					new ExtraMaterialStats(160),
					PlusTiC.justWhy,
					new LaserMediumMaterialStats(6.0f, 55));
			
			PlusTiC.materials.put("refinedObsidian", refinedObsidian);
			
			Material refinedGlowstone = new Material("refinedGlowstone", TextFormatting.YELLOW);
			refinedGlowstone.addTrait(Sassy.sassy);
			refinedGlowstone.addTrait(Illuminati.illuminati);
			refinedGlowstone.addItem("ingotRefinedGlowstone", 1, Material.VALUE_Ingot);
			refinedGlowstone.setCraftable(false).setCastable(true);
			PlusTiC.proxy.setRenderInfo(refinedGlowstone, 0xFFFF00);
			
			FluidMolten refinedGlowstoneFluid = Utils.fluidMetal("refinedGlowstone", 0xFFFF00);
			refinedGlowstoneFluid.setTemperature(1111);
			Utils.initFluidMetal(refinedGlowstoneFluid);
			refinedGlowstone.setFluid(refinedGlowstoneFluid);
			
			TinkerRegistry.addMaterialStats(refinedGlowstone,
					new HeadMaterialStats(450, 9, 10, COBALT),
					new HandleMaterialStats(0.9f, 0),
					new ExtraMaterialStats(100),
					PlusTiC.justWhy,
					new LaserMediumMaterialStats(10.0f, 40));
			
			PlusTiC.materials.put("refinedGlowstone", refinedGlowstone);
			
			if (osmium.hasFluid()) {
				// osgloglas
				Utils.ItemMatGroup osgloglasGroup = Utils.registerMatGroup("osgloglas");
				
				Material osgloglas = new Material("osgloglas", 0x72FF68);
				osgloglas.addTrait(BrownMagic.brownmagic);
				osgloglas.addTrait(Sassy.sassy);
				osgloglas.addTrait(Global.global);
				osgloglas.addItem("ingotOsgloglas", 1, Material.VALUE_Ingot);
				osgloglas.setCraftable(false).setCastable(true);
				osgloglas.setRepresentativeItem(osgloglasGroup.ingot);
				PlusTiC.proxy.setRenderInfo(osgloglas, 0x72FF68);
				
				FluidMolten osgloglasFluid = Utils.fluidMetal("osgloglas", 0x72FF68);
				osgloglasFluid.setTemperature(1300);
				Utils.initFluidMetal(osgloglasFluid);
				osgloglas.setFluid(osgloglasFluid);
				TinkerRegistry.registerAlloy(new FluidStack(osgloglasFluid, 1),
						new FluidStack(osmium.getFluid(), 1),
						new FluidStack(refinedObsidianFluid, 1),
						new FluidStack(refinedGlowstoneFluid, 1));
				
				TinkerRegistry.addMaterialStats(osgloglas, new HeadMaterialStats(2800, 7.5f, 11, 5),
						new HandleMaterialStats(1.5f, -50),
						new ExtraMaterialStats(100),
						new BowMaterialStats(0.6f, 1.8f, 8.2f));
				
				PlusTiC.materials.put("osgloglas", osgloglas);
			}
			
			Material iridium = Optional.ofNullable(PlusTiC.materials.get("iridium"))
					.orElse(TinkerRegistry.getMaterial("iridium"));
			
			if (iridium.hasFluid() && osmium.hasFluid()) {
				// osmiridium
				Utils.ItemMatGroup osmiridiumGroup = Utils.registerMatGroup("osmiridium");
				
				Material osmiridium = new Material("osmiridium", TextFormatting.LIGHT_PURPLE);
				osmiridium.addTrait(DevilsStrength.devilsstrength);
				osmiridium.addTrait(Anticorrosion.anticorrosion, HEAD);
				osmiridium.addItem("ingotOsmiridium", 1, Material.VALUE_Ingot);
				osmiridium.setCraftable(false).setCastable(true);
				osmiridium.setRepresentativeItem(osmiridiumGroup.ingot);
				PlusTiC.proxy.setRenderInfo(osmiridium, 0x666DFF);
				
				FluidMolten osmiridiumFluid = Utils.fluidMetal("osmiridium", 0x666DFF);
				osmiridiumFluid.setTemperature(840);
				Utils.initFluidMetal(osmiridiumFluid);
				osmiridium.setFluid(osmiridiumFluid);
				TinkerRegistry.registerAlloy(new FluidStack(osmiridiumFluid, 2),
						new FluidStack(osmium.getFluid(), 1), new FluidStack(iridium.getFluid(), 1));
				
				TinkerRegistry.addMaterialStats(osmiridium, new HeadMaterialStats(1300, 6.8f, 8, COBALT));
				TinkerRegistry.addMaterialStats(osmiridium, new HandleMaterialStats(1.5f, 30));
				TinkerRegistry.addMaterialStats(osmiridium, new ExtraMaterialStats(80));
				TinkerRegistry.addMaterialStats(osmiridium, new BowMaterialStats(0.38f, 2.05f, 10));
				
				PlusTiC.materials.put("osmiridium", osmiridium);
			}
		}
	}
	
	@Override
	public void init2() {
		Utils.setDispItem(PlusTiC.materials.get("refinedObsidian"), "ingotRefinedObsidian");
		Utils.setDispItem(PlusTiC.materials.get("osmium"), "ingotOsmium");
		Utils.setDispItem(PlusTiC.materials.get("refinedGlowstone"), "ingotRefinedGlowstone");
	}
	
}
