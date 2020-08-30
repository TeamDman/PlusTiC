package landmaster.plustic.modules;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import landmaster.plustic.fluids.FluidMolten;
import landmaster.plustic.traits.Thaumic;
import landmaster.plustic.util.Utils;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import static slimeknights.tconstruct.library.utils.HarvestLevels.OBSIDIAN;

public class ModuleThaumcraft implements IModule {
    public void init() {
        if (Config.thaumcraft && Loader.isModLoaded("thaumcraft")) {
            Material thaumium = new Material("thaumium", 0x1E0066);
            thaumium.addTrait(Thaumic.thaumic);
            thaumium.setCraftable(false).setCastable(true);
            thaumium.addItem("ingotThaumium", 1, Material.VALUE_Ingot);
            PlusTiC.proxy.setRenderInfo(thaumium, 0x1E0066);

            FluidMolten thaumiumFluid = Utils.fluidMetal("thaumium", 0x1E0066);
            thaumiumFluid.setTemperature(945);
            Utils.initFluidMetal(thaumiumFluid);
            thaumium.setFluid(thaumiumFluid);

            TinkerRegistry.addMaterialStats(thaumium,
                    new HeadMaterialStats(400, 7, 4.25f, OBSIDIAN),
                    new HandleMaterialStats(1.0f, 30),
                    new ExtraMaterialStats(111),
                    new BowMaterialStats(0.7f, 1.3f, 7));

            PlusTiC.materials.put("thaumium", thaumium);
        }
    }
}
