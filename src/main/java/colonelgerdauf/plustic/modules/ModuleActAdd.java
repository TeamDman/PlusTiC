package landmaster.plustic.modules;

import static slimeknights.tconstruct.library.materials.MaterialTypes.*;
import static slimeknights.tconstruct.library.utils.HarvestLevels.*;
import static slimeknights.tconstruct.tools.TinkerTraits.*;

import de.ellpeck.actuallyadditions.mod.items.InitItems;
import landmaster.plustic.*;
import landmaster.plustic.config.*;
import landmaster.plustic.tools.stats.*;
import landmaster.plustic.traits.*;
import landmaster.plustic.util.*;
import net.minecraft.item.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

public class ModuleActAdd implements IModule {

	public void init() {
		if (Config.actuallyAdditions && Loader.isModLoaded("actuallyadditions")) {
			Material blackQuartz = new Material("blackquartz_plustic", TextFormatting.BLACK);
			blackQuartz.addTrait(DevilsStrength.devilsstrength);
			blackQuartz.addTrait(crude2);
			blackQuartz.addItem("gemQuartzBlack", 1, Material.VALUE_Ingot);
			blackQuartz.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(blackQuartz, 0x000000);
			TinkerRegistry.addMaterialStats(blackQuartz, new HeadMaterialStats(380, 6, 4.5f, DIAMOND),
					new HandleMaterialStats(0.8f, 0),
					new ExtraMaterialStats(50),
					PlusTiC.justWhy);
			PlusTiC.materials.put("blackquartz", blackQuartz);
			
			Material Void = new Material("void_actadd_plustic", TextFormatting.BLACK);
			Void.addTrait(Unnamed.unnamed, HEAD);
			Void.addTrait(crude, HEAD);
			Void.addTrait(crude);
			Void.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(Void, 0x222222);
			TinkerRegistry.addMaterialStats(Void, new HeadMaterialStats(480, 7, 4.4f, OBSIDIAN),
					new HandleMaterialStats(1, 0),
					new ExtraMaterialStats(140),
					new BowMaterialStats(1, 1.3f, 3.5f));
			PlusTiC.materials.put("Void", Void);
			
			Material enori = new Material("enori_actadd_plustic", TextFormatting.WHITE);
			enori.addTrait(Starfishy.starfishy, HEAD);
			enori.addTrait(Anticorrosion.anticorrosion);
			enori.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(enori, 0xF2F7FF);
			TinkerRegistry.addMaterialStats(enori, new HeadMaterialStats(600, 7, 5.2f, OBSIDIAN),
					new HandleMaterialStats(1.2f, -5),
					new ExtraMaterialStats(75),
					new BowMaterialStats(1.2f, 1.2f, 5.1f),
					new BatteryCellMaterialStats(72000));
			PlusTiC.materials.put("enori", enori);
			
			Material palis = new Material("palis_actadd_plustic", TextFormatting.DARK_BLUE);
			palis.addTrait(Spades.spades);
			palis.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(palis, 0x0000D3);
			TinkerRegistry.addMaterialStats(palis, new HeadMaterialStats(800, 7.5f, 5.8f, COBALT),
					new HandleMaterialStats(1.3f, 7),
					new ExtraMaterialStats(100),
					PlusTiC.justWhy,
					new LaserMediumMaterialStats(9, 17));
			PlusTiC.materials.put("palis", palis);
			
			Material restonia = new Material("restonia_actadd_plustic", TextFormatting.DARK_RED);
			restonia.addTrait(Hearts.hearts);
			restonia.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(restonia, 0xEF0000);
			TinkerRegistry.addMaterialStats(restonia, new HeadMaterialStats(640, 8.8f, 6.1f, OBSIDIAN),
					new HandleMaterialStats(1.1f, 0),
					new ExtraMaterialStats(90),
					new BowMaterialStats(1.4f, 1.7f, 5.7f),
					new BatteryCellMaterialStats(80000));
			PlusTiC.materials.put("restonia", restonia);
			
			Material emeradic = new Material("emeradic_actadd_plustic", TextFormatting.DARK_GREEN);
			emeradic.addTrait(Vindictive.vindictive);
			emeradic.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(emeradic, 0x00C12D);
			TinkerRegistry.addMaterialStats(emeradic, new HeadMaterialStats(1400, 8, 7.7f, COBALT),
					new HandleMaterialStats(1.1f, 0),
					new ExtraMaterialStats(77),
					new BowMaterialStats(1.1f, 2.0f, 7.0f),
					new LaserMediumMaterialStats(10, 24));
			PlusTiC.materials.put("emeradic", emeradic);
			
			Material diamatine = new Material("diamatine_actadd_plustic", TextFormatting.BLUE);
			diamatine.addTrait(MorganLeFay.morganlefay);
			diamatine.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(diamatine, 0x609DFF);
			TinkerRegistry.addMaterialStats(diamatine, new HeadMaterialStats(1700, 8, 6.3f, COBALT),
					new HandleMaterialStats(1.2f, 10),
					new ExtraMaterialStats(88),
					new BowMaterialStats(0.7f, 2.0f, 11.0f),
					new LaserMediumMaterialStats(10, 24));
			PlusTiC.materials.put("diamatine", diamatine);
		}
	}
	
	public void init2() {
		final Material Void = PlusTiC.materials.get("Void");
		if (Void != null) {
			ItemStack voidStack = new ItemStack(InitItems.itemCrystal, 1, 3);
			Void.addItem(voidStack, 1, Material.VALUE_Ingot);
			Void.setRepresentativeItem(voidStack);
		}
		
		final Material enori = PlusTiC.materials.get("enori");
		if (enori != null) {
			ItemStack enoriStack = new ItemStack(InitItems.itemCrystal, 1, 5);
			enori.addItem(enoriStack, 1, Material.VALUE_Ingot);
			enori.setRepresentativeItem(enoriStack);
		}
		
		final Material palis = PlusTiC.materials.get("palis");
		if (palis != null) {
			ItemStack palisStack = new ItemStack(InitItems.itemCrystal, 1, 1);
			palis.addItem(palisStack, 1, Material.VALUE_Ingot);
			palis.setRepresentativeItem(palisStack);
		}
		
		final Material restonia = PlusTiC.materials.get("restonia");
		if (restonia != null) {
			restonia.addItem(InitItems.itemCrystal, 1, Material.VALUE_Ingot);
			restonia.setRepresentativeItem(InitItems.itemCrystal);
		}
		
		final Material emeradic = PlusTiC.materials.get("emeradic");
		if (emeradic != null) {
			ItemStack emeradicStack = new ItemStack(InitItems.itemCrystal, 1, 4);
			emeradic.addItem(emeradicStack, 1, Material.VALUE_Ingot);
			emeradic.setRepresentativeItem(emeradicStack);
		}
		
		final Material diamatine = PlusTiC.materials.get("diamatine");
		if (diamatine != null) {
			ItemStack diamatineStack = new ItemStack(InitItems.itemCrystal, 1, 2);
			diamatine.addItem(diamatineStack, 1, Material.VALUE_Ingot);
			diamatine.setRepresentativeItem(diamatineStack);
		}
		
		// YOU TOO, ACTUALLY ADDITIONS?
		Utils.setDispItem(PlusTiC.materials.get("blackquartz"), "gemQuartzBlack");
	}
	
}
