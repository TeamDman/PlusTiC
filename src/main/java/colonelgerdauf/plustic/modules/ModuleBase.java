package colonelgerdauf.plustic.modules;

import static slimeknights.tconstruct.library.materials.MaterialTypes.*;
import static slimeknights.tconstruct.library.utils.HarvestLevels.*;
import static slimeknights.tconstruct.tools.TinkerTraits.*;

import java.util.*;
import java.util.concurrent.*;

import colonelgerdauf.plustic.*;
import colonelgerdauf.plustic.api.*;
import colonelgerdauf.plustic.config.*;
import colonelgerdauf.plustic.fluids.*;
import colonelgerdauf.plustic.tools.stats.*;
import colonelgerdauf.plustic.traits.*;
import colonelgerdauf.plustic.util.*;
import net.minecraft.init.*;
import net.minecraft.item.Item;
import net.minecraft.util.text.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.smeltery.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.shared.*;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public class ModuleBase implements IModule {
	private static CompletableFuture<?> emeraldStage = new CompletableFuture<>();
	
	private static boolean usePlusTiCInvarFluid = false;

	public void init() {
		if (Config.base) {
			Material tnt = new Material("tnt", TextFormatting.RED);
			tnt.addTrait(Explosive.explosive);
			tnt.addItem(Blocks.TNT, Material.VALUE_Ingot);
			tnt.setRepresentativeItem(Blocks.TNT);
			tnt.setCraftable(true);
			PlusTiC.proxy.setRenderInfo(tnt, 0xFF4F4F);
			TinkerRegistry.addMaterialStats(tnt, new ArrowShaftMaterialStats(0.95f, 0));
			PlusTiC.materials.put("tnt", tnt);
			
			Material emerald = new Material("emerald_plustic", TextFormatting.GREEN);
			emerald.addTrait(Terrafirma.terrafirma.get(0));
			emerald.addTrait(Elemental.elemental, HEAD);
			emerald.addItem("gemEmerald", 1, Material.VALUE_Ingot);
			emerald.setRepresentativeItem(Items.EMERALD);
			emerald.setCraftable(false).setCastable(true);
			PlusTiC.proxy.setRenderInfo(emerald, 0x13DB52);
			CompletableFuture<?> emeraldStage1 = emeraldStage.thenRun(() -> emerald.setFluid(TinkerFluids.emerald));
			TinkerRegistry.addMaterialStats(emerald, new HeadMaterialStats(1222, 7, 7, COBALT),
					new HandleMaterialStats(1.1f, 0),
					new ExtraMaterialStats(70),
					new BowMaterialStats(1.1f, 1, 0.9f));
			PlusTiC.materials.put("emerald", emerald);
			PlusTiC.materialIntegrationStages.put("emerald", emeraldStage1);
			
			// alumite is back! (with some changes)
			Utils.ItemMatGroup alumiteGroup = Utils.registerMatGroup("alumite");
			
			Material alumite = new Material("alumite", TextFormatting.RED);
			alumite.addTrait(Global.global);
			alumite.addItem("ingotAlumite", 1, Material.VALUE_Ingot);
			alumite.setCraftable(false).setCastable(true);
			alumite.setRepresentativeItem(alumiteGroup.ingot);
			PlusTiC.proxy.setRenderInfo(alumite, 0xFFE0F1);
			
			FluidMolten alumiteFluid = Utils.fluidMetal("alumite", 0xFFE0F1);
			alumiteFluid.setTemperature(890);
			Utils.initFluidMetal(alumiteFluid);
			alumite.setFluid(alumiteFluid);
			
			TinkerRegistry.addMaterialStats(alumite, new HeadMaterialStats(700, 6.8f, 5.5f, COBALT),
					new HandleMaterialStats(1.10f, 70), new ExtraMaterialStats(80),
					new BowMaterialStats(0.65f, 1.6f, 7f));
			
			PlusTiC.materials.put("alumite", alumite);
			
			if (TinkerRegistry.getMaterial("nickel") == Material.UNKNOWN) {
				Material nickel = new Material("nickel", TextFormatting.YELLOW);
				nickel.addTrait(NickOfTime.nickOfTime, HEAD);
				nickel.addTrait(magnetic);
				nickel.addItem("ingotNickel", 1, Material.VALUE_Ingot);
				nickel.setCraftable(false).setCastable(true);
				new OreRegisterPromise("ingotNickel").thenAccept(nickel::setRepresentativeItem);
				PlusTiC.proxy.setRenderInfo(nickel, 0xFFF98E);
				
				nickel.setFluid(TinkerFluids.nickel);
				
				TinkerRegistry.addMaterialStats(nickel, new HeadMaterialStats(460, 6, 4.5f, OBSIDIAN),
						new HandleMaterialStats(1, -5), new ExtraMaterialStats(70), PlusTiC.justWhy,
						new FletchingMaterialStats(0.95f, 1.05f), new BatteryCellMaterialStats(75000));
				
				PlusTiC.materials.put("nickel", nickel);
			}
			
			if (TinkerRegistry.getMaterial("invar") == Material.UNKNOWN) {
				Utils.ItemMatGroup invarGroup = Utils.registerMatGroup("invar");
				
				Material invar = new Material("invar", 0xD6D6D6);
				invar.addTrait(DevilsStrength.devilsstrength);
				invar.addTrait(magnetic);
				invar.addItem("ingotInvar", 1, Material.VALUE_Ingot);
				invar.setCraftable(false).setCastable(true);
				invar.setRepresentativeItem(invarGroup.ingot);
				PlusTiC.proxy.setRenderInfo(invar, 0xD6D6D6);
				
				if (!FluidRegistry.isFluidRegistered("invar")) {
					usePlusTiCInvarFluid = true;
					FluidMolten invarFluid = Utils.fluidMetal("invar", 0xD6D6D6);
					invarFluid.setTemperature(1000);
					Utils.initFluidMetal(invarFluid);
				}
				invar.setFluid(FluidRegistry.getFluid("invar"));
				
				TinkerRegistry.addMaterialStats(invar,
						new HeadMaterialStats(600, 6, 5f, OBSIDIAN),
						new HandleMaterialStats(1.3f, 0),
						new ExtraMaterialStats(100),
						PlusTiC.justWhy,
						new FletchingMaterialStats(1, 1.15f));
				
				PlusTiC.materials.put("invar", invar);
			}
			
			if (TinkerRegistry.getMaterial("iridium") == Material.UNKNOWN) {
				Material iridium = new Material("iridium", TextFormatting.GRAY);
				iridium.addTrait(dense);
				iridium.addTrait(alien, HEAD);
				iridium.addItem("ingotIridium", 1, Material.VALUE_Ingot);
				iridium.setCraftable(false).setCastable(true);
				new OreRegisterPromise("ingotIridium").thenAccept(iridium::setRepresentativeItem);
				PlusTiC.proxy.setRenderInfo(iridium, 0xE5E5E5);
				
				if (!FluidRegistry.isFluidRegistered("iridium")) {
					FluidMolten iridiumFluid = Utils.fluidMetal("iridium", 0xE5E5E5);
					iridiumFluid.setTemperature(810);
					Utils.initFluidMetal(iridiumFluid);
				}
				iridium.setFluid(FluidRegistry.getFluid("iridium"));
				
				TinkerRegistry.addMaterialStats(iridium, new HeadMaterialStats(520, 6, 5.8f, DIAMOND));
				TinkerRegistry.addMaterialStats(iridium, new HandleMaterialStats(1.15f, -20));
				TinkerRegistry.addMaterialStats(iridium, new ExtraMaterialStats(60));
				TinkerRegistry.addMaterialStats(iridium, PlusTiC.justWhy);
				
				PlusTiC.materials.put("iridium", iridium);
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void itemRegEvent(RegistryEvent.Register<Item> event) {
		emeraldStage.complete(null);
	}
	
	public void init2() {
		Optional.ofNullable(PlusTiC.materials.get("alumite"))
		.map(Material::getFluid)
		.ifPresent(alumiteFluid -> {
			if (FluidRegistry.isFluidRegistered(TinkerFluids.aluminum)) {
				TinkerRegistry.registerAlloy(new FluidStack(alumiteFluid, 3), new FluidStack(TinkerFluids.aluminum, 5),
						new FluidStack(TinkerFluids.iron, 2), new FluidStack(TinkerFluids.obsidian, 2));
			}
		});
		
		Optional.ofNullable(PlusTiC.materials.get("invar"))
		.map(Material::getFluid)
		.ifPresent(invarFluid -> {
			if (usePlusTiCInvarFluid && FluidRegistry.isFluidRegistered(TinkerFluids.nickel)) {
				TinkerRegistry.registerAlloy(new FluidStack(invarFluid, 3),
						new FluidStack(TinkerFluids.iron, 2),
						new FluidStack(TinkerFluids.nickel, 1));
			}
		});
		
		if (PlusTiC.materials.containsKey("emerald")) {
			TinkerRegistry.registerTableCasting(EmeraldBoltCoreCastingRecipe.INSTANCE);
		}
	}
	
	@SubscribeEvent
	public static void onCastingRecipeRegister(TinkerRegisterEvent.TableCastingRegisterEvent event) {
		if (event.getRecipe() instanceof CastingRecipe
				&& ((CastingRecipe)event.getRecipe()).getResult().getItem() instanceof IMaterialItem
				&& ((CastingRecipe)event.getRecipe()).getFluid().getFluid().equals(TinkerFluids.emerald)) {
			FluidStack fs = ((CastingRecipe)event.getRecipe()).getFluid();
			// heuristic to make sure we don't apply this correction more than once
			if (fs.amount % Material.VALUE_Shard != 0) return;
			fs.amount = (fs.amount * Material.VALUE_Gem) / Material.VALUE_Ingot;
		}
	}
	
	@SubscribeEvent
	public static void onMeltingRecipeRegister(TinkerRegisterEvent.MeltingRegisterEvent event) {
		if (event.getRecipe().input.getInputs()
				.stream().anyMatch(stack -> stack.getItem() instanceof IMaterialItem)
				&& event.getRecipe().output.getFluid().equals(TinkerFluids.emerald)) {
			FluidStack fs = event.getRecipe().output;
			if (fs.amount % Material.VALUE_Shard != 0) return;
			fs.amount = (fs.amount * Material.VALUE_Gem) / Material.VALUE_Ingot;
		}
	}
}
