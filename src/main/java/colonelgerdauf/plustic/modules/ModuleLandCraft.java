package landmaster.plustic.modules;

import static slimeknights.tconstruct.library.utils.HarvestLevels.*;
//import static slimeknights.tconstruct.tools.TinkerTraits.*;

import java.util.*;

import org.apache.commons.lang3.*;
import org.apache.commons.lang3.tuple.*;

import it.unimi.dsi.fastutil.objects.*;
import landmaster.plustic.*;
import landmaster.plustic.config.*;
import landmaster.plustic.fluids.*;
import landmaster.plustic.traits.*;
import landmaster.plustic.util.*;
import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

public class ModuleLandCraft implements IModule {
	public void init() {
		if (Config.landCraft && Loader.isModLoaded("landcraft")) {
			List<Triple<String, Integer, Integer>> matData = Arrays.asList(
					Triple.of("kelline", 0x7BFF00, 430),
					Triple.of("garfax", 0x0000FF, 700),
					Triple.of("morganine", 0xFF00FF, 1200),
					Triple.of("racheline", 0xD37DDC, 1400),
					Triple.of("friscion", 0xD2FF1C, 400)
					);
			Map<String, Material> mats = new Object2ObjectOpenHashMap<>();
			matData.forEach(matDatum -> {
				Material mat = new Material(matDatum.getLeft(), matDatum.getMiddle());
				String ingot = "ingot"+StringUtils.capitalize(matDatum.getLeft());
				mat.addItem(ingot, 1, Material.VALUE_Ingot);
				mat.setCraftable(false).setCastable(true);
				//Utils.setDispItem(mat, ingot);
				
				FluidMolten matFluid = Utils.fluidMetal(matDatum.getLeft(), matDatum.getMiddle());
				matFluid.setTemperature(matDatum.getRight());
				Utils.initFluidMetal(matFluid);
				mat.setFluid(matFluid);
				
				mats.put(matDatum.getLeft(), mat);
			});
			
			{
				Material kelline = mats.get("kelline");
				kelline.addTrait(HailHydra.hailhydra);
				PlusTiC.proxy.setRenderInfo(kelline, 0x00FF00, 0xFFFF00, 0xFF0000);
				
				TinkerRegistry.addMaterialStats(kelline,
						new HeadMaterialStats(2500, 9.0f, 9.5f, 8),
						new HandleMaterialStats(1.3f, -20),
						new ExtraMaterialStats(250),
						new BowMaterialStats(0.8f, 1.0f, 14));
			}
			
			{
				Material garfax = mats.get("garfax");
				garfax.addTrait(Barrett.barrett);
				PlusTiC.proxy.setRenderInfo(garfax, 0x00FF55, 0x0000FF, 0x0000FF);
				
				TinkerRegistry.addMaterialStats(garfax,
						new HeadMaterialStats(1300, 7.3f, 6.5f, 5),
						new HandleMaterialStats(0.9f, 10),
						new ExtraMaterialStats(40),
						PlusTiC.justWhy);
			}
			
			{
				Material morganine = mats.get("morganine");
				morganine.addTrait(MorganLeFay.morganlefay);
				PlusTiC.proxy.setRenderInfo(morganine, 0xFF00FF, 0xEE42F4, 0x62008C);
				
				TinkerRegistry.addMaterialStats(morganine,
						new HeadMaterialStats(1600, 8.6f, 7, 6),
						new HandleMaterialStats(1.2f, 100),
						new ExtraMaterialStats(400),
						new BowMaterialStats(1.2f, 1.2f, 6));
			}
			
			{
				Material racheline = mats.get("racheline");
				racheline.addTrait(BlindBandit.blindbandit);
				PlusTiC.proxy.setRenderInfo(racheline, 0xA5FFFF, 0xD37DDC, 0xFF00BA);
				
				TinkerRegistry.addMaterialStats(racheline,
						new HeadMaterialStats(2000, 8.3f, 8, 7),
						new HandleMaterialStats(1.0f, 80),
						new ExtraMaterialStats(260),
						new BowMaterialStats(1.3f, 0.7f, 15));
			}
			
			{
				Material friscion = mats.get("friscion");
				friscion.addTrait(Trash.trash);
				PlusTiC.proxy.setRenderInfo(friscion, 0x00FF26, 0x00FFE1, 0xD9FF00);
				
				TinkerRegistry.addMaterialStats(friscion,
						new HeadMaterialStats(1100, 7, 6, COBALT),
						new HandleMaterialStats(0.8f, -10),
						new ExtraMaterialStats(68),
						PlusTiC.justWhy);
			}
			
			PlusTiC.materials.putAll(mats);
		}
	}
}
