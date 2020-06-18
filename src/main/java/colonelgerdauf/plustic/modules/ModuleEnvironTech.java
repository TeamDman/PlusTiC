package colonelgerdauf.plustic.modules;

import static slimeknights.tconstruct.library.materials.MaterialTypes.*;
import static slimeknights.tconstruct.library.utils.HarvestLevels.*;
import static slimeknights.tconstruct.tools.TinkerTraits.*;

import colonelgerdauf.plustic.*;
import colonelgerdauf.plustic.config.*;
import colonelgerdauf.plustic.traits.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

public class ModuleEnvironTech implements IModule {
	@GameRegistry.ObjectHolder("environmentaltech:mica")
	public static final Item micaItem = null;
	
	public void init() {
		if (Config.environTech && Loader.isModLoaded("environmentaltech")) {
			Material mica = new Material("mica", 0xBBBBBB);
			mica.addTrait(Slashing.slashing);
			mica.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(mica, 0xBBBBBB);
			TinkerRegistry.addMaterialStats(mica, new HeadMaterialStats(680, 5.5f, 6, OBSIDIAN),
					new HandleMaterialStats(0.9f, -5),
					new ExtraMaterialStats(90),
					PlusTiC.justWhy);
			PlusTiC.materials.put("mica", mica);
			
			Material litherite = new Material("litherite", 0x078E51);
			litherite.addTrait(stonebound, HEAD);
			litherite.addTrait(crude2);
			litherite.addTrait(petramor);
			litherite.addItem("crystalLitherite", 1, Material.VALUE_Ingot);
			litherite.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(mica, 0x078E51);
			TinkerRegistry.addMaterialStats(litherite, new HeadMaterialStats(700, 7.7f, 6, OBSIDIAN),
					new HandleMaterialStats(0.9f, -5),
					new ExtraMaterialStats(111),
					PlusTiC.justWhy);
			PlusTiC.materials.put("litherite", litherite);
			PlusTiC.materialOreDicts.put("litherite", "crystalLitherite");
			
			Material erodium = new Material("erodium", 0xBB56FF);
			erodium.addTrait(Vindictive.vindictive);
			erodium.addTrait(NaturesWrath.natureswrath);
			erodium.addItem("crystalErodium", 1, Material.VALUE_Ingot);
			erodium.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(erodium, 0xBB56FF);
			TinkerRegistry.addMaterialStats(erodium, new HeadMaterialStats(1000, 9f, 7.5f, OBSIDIAN),
					new HandleMaterialStats(0.9f, -5),
					new ExtraMaterialStats(140),
					PlusTiC.justWhy);
			PlusTiC.materials.put("erodium", erodium);
			PlusTiC.materialOreDicts.put("erodium", "crystalErodium");
			
			Material kyronite = new Material("kyronite", 0x77007f);
			kyronite.addTrait(FruitSalad.fruitsalad, HEAD);
			kyronite.addTrait(NaturesBlessing.naturesblessing);
			kyronite.addItem("crystalKyronite", 1, Material.VALUE_Ingot);
			kyronite.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(kyronite, 0x77007f);
			TinkerRegistry.addMaterialStats(kyronite, new HeadMaterialStats(1300, 10.3f, 9, COBALT),
					new HandleMaterialStats(0.9f, -5),
					new ExtraMaterialStats(169),
					new BowMaterialStats(0.6f, 1.6f, 5));
			PlusTiC.materials.put("kyronite", kyronite);
			PlusTiC.materialOreDicts.put("kyronite", "crystalKyronite");
			
			Material pladium = new Material("pladium", 0x070070);
			pladium.addTrait(StopBeingSelfish.stopbeingselfish);
			pladium.addItem("crystalPladium", 1, Material.VALUE_Ingot);
			pladium.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(pladium, 0x070070);
			TinkerRegistry.addMaterialStats(pladium, new HeadMaterialStats(1600, 11.6f, 10.5f, COBALT),
					new HandleMaterialStats(0.9f, -5),
					new ExtraMaterialStats(198),
					new BowMaterialStats(0.49f, 2.2f, 6.5f));
			PlusTiC.materials.put("pladium", pladium);
			PlusTiC.materialOreDicts.put("pladium", "crystalPladium");
			
			Material ionite = new Material("ionite", 0x6df5ff);
			ionite.addTrait(ChadThunder.chadthunder, HEAD);
			ionite.addTrait(Illuminati.illuminati);
			ionite.addItem("crystalIonite", 1, Material.VALUE_Ingot);
			ionite.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(ionite, 0x6df5ff);
			TinkerRegistry.addMaterialStats(ionite, new HeadMaterialStats(1900, 12.9f, 12, 5),
					new HandleMaterialStats(0.9f, -5),
					new ExtraMaterialStats(227),
					new BowMaterialStats(0.38f, 3f, 8));
			PlusTiC.materials.put("ionite", ionite);
			PlusTiC.materialOreDicts.put("ionite", "crystalIonite");
			
			Material aethium = new Material("aethium", 0x000000);
			aethium.addTrait(MusicOfTheSpheres.musicofthespheres);
			aethium.addItem("crystalAethium", 1, Material.VALUE_Ingot);
			aethium.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(aethium, 0x000000);
			TinkerRegistry.addMaterialStats(aethium, new HeadMaterialStats(2200, 14.2f, 13.5f, 6),
					new HandleMaterialStats(0.9f, -5),
					new ExtraMaterialStats(227),
					new BowMaterialStats(0.38f, 3f, 12));
			PlusTiC.materials.put("aethium", aethium);
			PlusTiC.materialOreDicts.put("aethium", "crystalAethium");
		}
	}
	
	public void init2() {
		final Material mica = PlusTiC.materials.get("mica");
		if (mica != null) {
			mica.addItem(micaItem, 1, Material.VALUE_Ingot);
			mica.setRepresentativeItem(micaItem);
		}
	}
}
