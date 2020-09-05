package landmaster.plustic.modules;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import landmaster.plustic.fluids.FluidMolten;
import landmaster.plustic.tools.stats.LaserMediumMaterialStats;
import landmaster.plustic.traits.Illuminati;
import landmaster.plustic.traits.ImASuperstar;
import landmaster.plustic.util.Utils;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;
import static slimeknights.tconstruct.library.utils.HarvestLevels.DIAMOND;

public class ModuleAstralSorcery implements IModule {
    @Override
    public void init() {
        if (Loader.isModLoaded("astralsorcery") && Config.astralSorcery) {
            Material starmetal = new Material("starmetal", 0x000b56);
            starmetal.addTrait(ImASuperstar.imasuperstar, HEAD);
            starmetal.addTrait(ImASuperstar.imasuperstar, LaserMediumMaterialStats.TYPE);
            starmetal.addTrait(Illuminati.illuminati);
            starmetal.addItem("ingotAstralStarmetal", 1, Material.VALUE_Ingot);
            starmetal.setCraftable(false).setCastable(true);
            PlusTiC.proxy.setRenderInfo(starmetal, 0x000b56);

            FluidMolten starmetalFluid = Utils.fluidMetal("starmetal", 0x000b56);
            starmetalFluid.setTemperature(999);
            Utils.initFluidMetal(starmetalFluid);
            starmetal.setFluid(starmetalFluid);

            TinkerRegistry.addMaterialStats(starmetal,
                    new HeadMaterialStats(204, 6.00f, 4.00f, DIAMOND),
                    new HandleMaterialStats(1.0f, 60),
                    new ExtraMaterialStats(100),
                    new BowMaterialStats(0.5f, 1.5f, 7f),
                    new LaserMediumMaterialStats(4.6f, 70));

            PlusTiC.materials.put("astralStarmetal", starmetal);
        }
    }
}
