package landmaster.plustic.modules;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import landmaster.plustic.tools.stats.LaserMediumMaterialStats;
import landmaster.plustic.traits.DPRK;
import landmaster.plustic.traits.DarkTraveler;
import landmaster.plustic.traits.Ignoble;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import java.util.concurrent.CompletableFuture;

import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;
import static slimeknights.tconstruct.library.utils.HarvestLevels.COBALT;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public class ModuleProjectE implements IModule {

    private static final CompletableFuture<?> regPromise = new CompletableFuture<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRegItems(RegistryEvent.Register<Item> event) {
        regPromise.complete(null);
    }

    public void init() {
        if (Config.projectE && Loader.isModLoaded("projecte")) {
            Material darkMatter = new Material("darkMatter", 0x270133);
            Material redMatter = new Material("redMatter", 0xE30000);

            final CompletableFuture<?> integrationPromise = regPromise.thenRun(() -> {
                final Item matter = Item.REGISTRY.getObject(new ResourceLocation("projecte", "item.pe_matter"));

                darkMatter.addTrait(Ignoble.ignoble, HEAD);
                darkMatter.addTrait(DarkTraveler.darktraveler);
                darkMatter.addItem(matter, 1, Material.VALUE_Ingot);
                darkMatter.setCraftable(true);
                darkMatter.setRepresentativeItem(matter);
                PlusTiC.proxy.setRenderInfo(darkMatter, 0x270133);

                TinkerRegistry.addMaterialStats(darkMatter,
                        new HeadMaterialStats(1729, 10, 10.5f, COBALT),
                        new HandleMaterialStats(1.7f, 289),
                        new ExtraMaterialStats(111),
                        new BowMaterialStats(1.0f, 1.1f, 10),
                        new LaserMediumMaterialStats(6, 130));

                final ItemStack redMatterStack = new ItemStack(matter, 1, 1);

                redMatter.addTrait(DPRK.dprk);
                redMatter.addItem(redMatterStack, 1, Material.VALUE_Ingot);
                redMatter.setCraftable(true);
                redMatter.setRepresentativeItem(redMatterStack);
                PlusTiC.proxy.setRenderInfo(redMatter, 0xE30000);

                TinkerRegistry.addMaterialStats(redMatter,
                        new HeadMaterialStats(2017, 14, 15, COBALT),
                        new HandleMaterialStats(2, -53),
                        new ExtraMaterialStats(105),
                        new BowMaterialStats(1.0f, 2.0f, 13.7f));
            });

            PlusTiC.materials.put("darkMatter", darkMatter);
            PlusTiC.materials.put("redMatter", redMatter);

            PlusTiC.materialIntegrationStages.put("darkMatter", integrationPromise);
            PlusTiC.materialIntegrationStages.put("redMatter", integrationPromise);
        }
    }
}
