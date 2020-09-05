package landmaster.plustic.modules;

import com.progwml6.natura.shared.*;
import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import landmaster.plustic.traits.*;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import java.util.concurrent.CompletableFuture;

import static slimeknights.tconstruct.library.utils.HarvestLevels.COBALT;
import static slimeknights.tconstruct.tools.TinkerTraits.ecological;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public class ModuleNatura implements IModule {
    private static final CompletableFuture<?> itemPromise = new CompletableFuture<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void itemRegEvent(RegistryEvent.Register<Item> event) {
        itemPromise.complete(null);
    }

    public void init() {
        if (Config.natura && Loader.isModLoaded("natura")) {
            Material darkwood = new Material("darkwood_plustic", TextFormatting.DARK_BLUE);
            darkwood.addTrait(DarkTraveler.darktraveler);
            darkwood.addTrait(ecological);
            CompletableFuture<?> darkwoodPromise = itemPromise.thenRun(() -> {
                darkwood.addItem(ModuleNaturaStuff.darkwoodPlankStack(), 1, Material.VALUE_Ingot);
                darkwood.addItem(ModuleNaturaStuff.darkwoodLogStack(), 1, 4 * Material.VALUE_Ingot);
                darkwood.addItem(NaturaCommons.darkwood_stick, 1, Material.VALUE_Shard);
                darkwood.setRepresentativeItem(ModuleNaturaStuff.darkwoodPlankStack());
            });
            darkwood.setCraftable(true);
            PlusTiC.proxy.setRenderInfo(darkwood, 0x000044);
            TinkerRegistry.addMaterialStats(darkwood,
                    new HeadMaterialStats(350, 5f, 3f, COBALT),
                    new HandleMaterialStats(1.3f, -5),
                    new ExtraMaterialStats(90),
                    new BowMaterialStats(1.2f, 1.3f, 3));
            PlusTiC.materials.put("darkwood", darkwood);
            PlusTiC.materialIntegrationStages.put("darkwood", darkwoodPromise);

            Material ghostwood = new Material("ghostwood_plustic", TextFormatting.WHITE);
            ghostwood.addTrait(Ghastly.ghastly);
            ghostwood.addTrait(ecological);
            CompletableFuture<?> ghostwoodPromise = itemPromise.thenRun(() -> {
                ghostwood.addItem(ModuleNaturaStuff.ghostwoodPlankStack(), 1, Material.VALUE_Ingot);
                ghostwood.addItem(ModuleNaturaStuff.ghostwoodLogStack(), 1, 4 * Material.VALUE_Ingot);
                ghostwood.addItem(NaturaCommons.ghostwood_stick, 1, Material.VALUE_Shard);
                ghostwood.setRepresentativeItem(ModuleNaturaStuff.ghostwoodPlankStack());
            });
            ghostwood.setCraftable(true);
            PlusTiC.proxy.setRenderInfo(ghostwood, 0xFFFFFF);
            TinkerRegistry.addMaterialStats(ghostwood,
                    new HeadMaterialStats(300, 4.9f, 2.5f, COBALT),
                    new HandleMaterialStats(1.1f, 0),
                    new ExtraMaterialStats(100),
                    new BowMaterialStats(1.6f, 1.1f, 2));
            PlusTiC.materials.put("ghostwood", ghostwood);
            PlusTiC.materialIntegrationStages.put("ghostwood", ghostwoodPromise);

            Material fusewood = new Material("fusewood_plustic", 0x00D18E);
            fusewood.addTrait(DPRK.dprk);
            fusewood.addTrait(ecological);
            CompletableFuture<?> fusewoodPromise = itemPromise.thenRun(() -> {
                fusewood.addItem(ModuleNaturaStuff.fusewoodPlankStack(), 1, Material.VALUE_Ingot);
                fusewood.addItem(ModuleNaturaStuff.fusewoodLogStack(), 1, 4 * Material.VALUE_Ingot);
                fusewood.addItem(NaturaCommons.fusewood_stick, 1, Material.VALUE_Shard);
                fusewood.setRepresentativeItem(ModuleNaturaStuff.fusewoodPlankStack());
            });
            fusewood.setCraftable(true);
            PlusTiC.proxy.setRenderInfo(fusewood, 0x00D18E);
            TinkerRegistry.addMaterialStats(fusewood,
                    new HeadMaterialStats(430, 6.0f, 4f, COBALT),
                    new HandleMaterialStats(1.0f, -20),
                    new ExtraMaterialStats(50),
                    new BowMaterialStats(0.7f, 2.0f, 7));
            PlusTiC.materials.put("fusewood", fusewood);
            PlusTiC.materialIntegrationStages.put("fusewood", fusewoodPromise);

            Material bloodwood = new Material("bloodwood_plustic", 0x600000);
            bloodwood.addTrait(BloodyMary.bloodymary);
            bloodwood.addTrait(ecological);
            CompletableFuture<?> bloodwoodPromise = itemPromise.thenRun(() -> {
                bloodwood.addItem(ModuleNaturaStuff.bloodwoodPlankStack(), 1, Material.VALUE_Ingot);
                bloodwood.addItem(ModuleNaturaStuff.bloodwoodLogStack(), 1, 4 * Material.VALUE_Ingot);
                bloodwood.addItem(NaturaCommons.bloodwood_stick, 1, Material.VALUE_Shard);
                bloodwood.setRepresentativeItem(ModuleNaturaStuff.bloodwoodPlankStack());
            });
            bloodwood.setCraftable(true);
            PlusTiC.proxy.setRenderInfo(bloodwood, 0x600000);
            TinkerRegistry.addMaterialStats(bloodwood,
                    new HeadMaterialStats(550, 7.0f, 5f, COBALT),
                    new HandleMaterialStats(1.4f, -60),
                    new ExtraMaterialStats(170),
                    new BowMaterialStats(1.6f, 1.4f, 7));
            PlusTiC.materials.put("bloodwood", bloodwood);
            PlusTiC.materialIntegrationStages.put("bloodwood", bloodwoodPromise);

            Material flamestring = new Material("flamestring_plustic", 0xFF3314);
            flamestring.addTrait(Naphtha.naphtha);
            CompletableFuture<?> flamestringPromise = itemPromise.thenRun(() -> {
                flamestring.addItem(NaturaCommons.flameString, 1, Material.VALUE_Ingot);
                flamestring.setRepresentativeItem(NaturaCommons.flameString);
            });
            flamestring.setCraftable(true);
            PlusTiC.proxy.setRenderInfo(flamestring, 0xFF3314);
            TinkerRegistry.addMaterialStats(flamestring, new BowStringMaterialStats(1.2f));
            PlusTiC.materials.put("flamestring", flamestring);
            PlusTiC.materialIntegrationStages.put("flamestring", flamestringPromise);
        }
    }
}
