package landmaster.plustic.modules;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import landmaster.plustic.fluids.FluidMolten;
import landmaster.plustic.tools.stats.BatteryCellMaterialStats;
import landmaster.plustic.tools.stats.LaserMediumMaterialStats;
import landmaster.plustic.traits.Apocalypse;
import landmaster.plustic.traits.Heavy;
import landmaster.plustic.traits.Portly;
import landmaster.plustic.util.Utils;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;
import static slimeknights.tconstruct.library.utils.HarvestLevels.DIAMOND;
import static slimeknights.tconstruct.library.utils.HarvestLevels.OBSIDIAN;
import static slimeknights.tconstruct.tools.TinkerTraits.flammable;

public class ModuleLandCore implements IModule {
    public void init() {
        if (Config.landCore && Loader.isModLoaded("landcore")) {
            List<Triple<String, Integer, Integer>> matData = Arrays.asList(
                    Triple.of("thorium", 0x00FFBF, 800),
                    Triple.of("tungsten", 0x5C5C5C, 1100),
                    Triple.of("landium", 0xFF0077, 1500));
            Map<String, Material> mats = new Object2ObjectOpenHashMap<>();
            matData.forEach(matDatum -> {
                Material mat = new Material(matDatum.getLeft(), matDatum.getMiddle());
                String ingot = "ingot" + StringUtils.capitalize(matDatum.getLeft());
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
                Material thorium = mats.get("thorium");
                thorium.addTrait(Apocalypse.apocalypse, HEAD);
                thorium.addTrait(flammable);
                PlusTiC.proxy.setRenderInfo(thorium, 0x00FFBF);

                TinkerRegistry.addMaterialStats(thorium,
                        new HeadMaterialStats(500, 6.5f, 4.5f, DIAMOND),
                        new HandleMaterialStats(0.7f, -50),
                        new ExtraMaterialStats(200),
                        new BatteryCellMaterialStats(100000),
                        PlusTiC.justWhy);
            }

            {
                Material tungsten = mats.get("tungsten");
                tungsten.addTrait(Heavy.heavy);
                PlusTiC.proxy.setRenderInfo(tungsten, 0x5C5C5C);

                TinkerRegistry.addMaterialStats(tungsten,
                        new HeadMaterialStats(700, 9.0f, 4.75f, DIAMOND),
                        new HandleMaterialStats(1.4f, 0),
                        new ExtraMaterialStats(400),
                        PlusTiC.justWhy);
            }

            {
                Material landium = mats.get("landium");
                landium.addTrait(Portly.portly);
                PlusTiC.proxy.setRenderInfo(landium, 0xFF0077);

                TinkerRegistry.addMaterialStats(landium,
                        new HeadMaterialStats(1500, 8.7f, 5.5f, OBSIDIAN),
                        new HandleMaterialStats(1.5f, -100),
                        new ExtraMaterialStats(380),
                        new BowMaterialStats(1.2f, 1.6f, 7),
                        new LaserMediumMaterialStats(7.0f, 30));
            }

            PlusTiC.materials.putAll(mats);
        }
    }
}
