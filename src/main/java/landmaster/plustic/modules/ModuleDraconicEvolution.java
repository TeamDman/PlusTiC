package landmaster.plustic.modules;

import landmaster.plustic.ModInfo;
import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import landmaster.plustic.tools.stats.LaserMediumMaterialStats;
import landmaster.plustic.traits.*;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import java.util.concurrent.CompletableFuture;

import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public class ModuleDraconicEvolution implements IModule {
    @GameRegistry.ObjectHolder(value = "draconicevolution:wyvern_core")
    public static final Item wyvern_core = null;
    @GameRegistry.ObjectHolder(value = "draconicevolution:awakened_core")
    public static final Item awakened_core = null;
    @GameRegistry.ObjectHolder(value = "draconicevolution:chaotic_core")
    public static final Item chaotic_core = null;

    private static final CompletableFuture<?> regFut = new CompletableFuture<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemReg(RegistryEvent.Register<Item> event) {
        regFut.complete(null);
    }

    public void init() {
        if (Config.draconicEvolution && Loader.isModLoaded("draconicevolution")) {
            Material wyvern = new Material("wyvern_plustic", TextFormatting.DARK_PURPLE),
                    awakened = new Material("awakened_plustic", TextFormatting.GOLD),
                    chaotic = new Material("chaotic_plustic", TextFormatting.GRAY);

            PlusTiC.materials.put("wyvern_core", wyvern);
            PlusTiC.materials.put("awakened_core", awakened);
            PlusTiC.materials.put("chaotic_core", chaotic);

            final CompletableFuture<?> draconicFut = regFut.thenRun(() -> {
                wyvern.addTrait(BrownMagic.brownmagic, HEAD);
                wyvern.addTrait(BlindBandit.blindbandit, HEAD);
                wyvern.addTrait(Portly.portly);
                wyvern.addItem(wyvern_core, 1, Material.VALUE_Ingot);
                wyvern.setCraftable(true);
                wyvern.setRepresentativeItem(wyvern_core);
                PlusTiC.proxy.setRenderInfo(wyvern, 0x7F00FF);
                TinkerRegistry.addMaterialStats(wyvern, new HeadMaterialStats(2000, 8, 15, 8));
                TinkerRegistry.addMaterialStats(wyvern, new HandleMaterialStats(1.6f, 130));
                TinkerRegistry.addMaterialStats(wyvern, new ExtraMaterialStats(240));
                TinkerRegistry.addMaterialStats(wyvern, new BowMaterialStats(1.6f, 2, 11));

                awakened.addTrait(RudeAwakening.rudeawakening, HEAD);
                awakened.addTrait(BrownMagic.brownmagic, HEAD);
                awakened.addTrait(BlindBandit.blindbandit);
                awakened.addTrait(Apocalypse.apocalypse);
                awakened.addTrait(Global.global);
                awakened.addItem(awakened_core, 1, Material.VALUE_Ingot);
                awakened.setCraftable(true);
                awakened.setRepresentativeItem(awakened_core);
                PlusTiC.proxy.setRenderInfo(awakened, 0xFFB200);
                TinkerRegistry.addMaterialStats(awakened, new HeadMaterialStats(5000, 9, 35, 10));
                TinkerRegistry.addMaterialStats(awakened, new HandleMaterialStats(1.8f, 500));
                TinkerRegistry.addMaterialStats(awakened, new ExtraMaterialStats(500));
                TinkerRegistry.addMaterialStats(awakened, new BowMaterialStats(1.9f, 2.8f, 20));

                chaotic.addTrait(HailHydra.hailhydra, HEAD);
                chaotic.addTrait(Vindictive.vindictive, HEAD);
                chaotic.addTrait(DarkTraveler.darktraveler);
                chaotic.addItem(chaotic_core, 1, Material.VALUE_Ingot);
                chaotic.setCraftable(true);
                chaotic.setRepresentativeItem(chaotic_core);
                PlusTiC.proxy.setRenderInfo(chaotic, 0x999999);
                TinkerRegistry.addMaterialStats(chaotic, new HeadMaterialStats(10000, 12, 48, 12));
                TinkerRegistry.addMaterialStats(chaotic, new HandleMaterialStats(2.3f, 700));
                TinkerRegistry.addMaterialStats(chaotic, new ExtraMaterialStats(800));
                TinkerRegistry.addMaterialStats(chaotic, new BowMaterialStats(2.3f, 4.0f, 24));
                TinkerRegistry.addMaterialStats(chaotic, new LaserMediumMaterialStats(45, 256));
            });

            PlusTiC.materialIntegrationStages.put("wyvern_core", draconicFut);
            PlusTiC.materialIntegrationStages.put("awakened_core", draconicFut);
            PlusTiC.materialIntegrationStages.put("chaotic_core", draconicFut);
        }
    }

}
