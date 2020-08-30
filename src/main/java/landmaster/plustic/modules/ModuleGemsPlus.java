package landmaster.plustic.modules;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import landmaster.plustic.tools.stats.BatteryCellMaterialStats;
import landmaster.plustic.tools.stats.LaserMediumMaterialStats;
import landmaster.plustic.traits.GetLucky;
import landmaster.plustic.util.Utils;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import static slimeknights.tconstruct.library.utils.HarvestLevels.COBALT;

public class ModuleGemsPlus implements IModule {

    public void init() {
        if (Config.gemsplus && Loader.isModLoaded("gemsplus")) {
            Material phoenixite = new Material("phoenixite", 0xFF4511);
            phoenixite.addTrait(GetLucky.getlucky);
            phoenixite.addItem("gemPhoenixite", 1, Material.VALUE_Ingot);
            phoenixite.setCraftable(true);
            PlusTiC.proxy.setRenderInfo(phoenixite, 0xFF4511);

            TinkerRegistry.addMaterialStats(phoenixite,
                    new HeadMaterialStats(1300, 9, 5, COBALT),
                    new HandleMaterialStats(1.4f, 130),
                    new ExtraMaterialStats(77),
                    new BowMaterialStats(1.3f, 1.3f, 5.5f),
                    new LaserMediumMaterialStats(11, 20),
                    new BatteryCellMaterialStats(80000));

            PlusTiC.materials.put("phoenixite", phoenixite);
        }
    }

    public void init2() {
        Utils.setDispItem(PlusTiC.materials.get("phoenixite"), "gemPhoenixite");
    }

}
