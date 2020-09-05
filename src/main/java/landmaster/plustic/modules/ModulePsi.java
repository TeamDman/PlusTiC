package landmaster.plustic.modules;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import landmaster.plustic.fluids.FluidMolten;
import landmaster.plustic.tools.stats.LaserMediumMaterialStats;
import landmaster.plustic.traits.Global;
import landmaster.plustic.traits.Portly;
import landmaster.plustic.traits.Psicological;
import landmaster.plustic.util.OreRegisterPromise;
import landmaster.plustic.util.Utils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.TinkerRegisterEvent;
import slimeknights.tconstruct.library.materials.*;

import java.util.stream.Stream;

import static slimeknights.tconstruct.library.utils.HarvestLevels.OBSIDIAN;

public class ModulePsi implements IModule {
    static {
        MinecraftForge.EVENT_BUS.register(ModulePsi.class);
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
}
