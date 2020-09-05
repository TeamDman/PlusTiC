package landmaster.plustic.modules;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import landmaster.plustic.fluids.FluidMolten;
import landmaster.plustic.traits.Anticorrosion;
import landmaster.plustic.traits.Light;
import landmaster.plustic.util.Utils;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;
import static slimeknights.tconstruct.library.utils.HarvestLevels.OBSIDIAN;

public class ModuleAdvRocketry implements IModule {

    public void init() {
        if (Config.advancedRocketry &&
                (Loader.isModLoaded("libVulpes") || Loader.isModLoaded("libvulpes"))) {
            if (TinkerRegistry.getMaterial("titanium") == Material.UNKNOWN) {
                Material titanium = new Material("titanium", TextFormatting.WHITE);
                titanium.addTrait(Light.light);
                titanium.addTrait(Anticorrosion.anticorrosion, HEAD);
                titanium.addItem("ingotTitanium", 1, Material.VALUE_Ingot);
                titanium.setCraftable(false).setCastable(true);
                Utils.setDispItem(titanium, "libvulpes", "productingot", 7);
                PlusTiC.proxy.setRenderInfo(titanium, 0xDCE1EA);

                FluidMolten titaniumFluid = Utils.fluidMetal("titanium", 0xDCE1EA);
                titaniumFluid.setTemperature(790);
                Utils.initFluidMetal(titaniumFluid);
                titanium.setFluid(titaniumFluid);

                TinkerRegistry.addMaterialStats(titanium, new HeadMaterialStats(560, 6, 6, OBSIDIAN));
                TinkerRegistry.addMaterialStats(titanium, new HandleMaterialStats(1.4f, 0));
                TinkerRegistry.addMaterialStats(titanium, new ExtraMaterialStats(40));
                TinkerRegistry.addMaterialStats(titanium, new BowMaterialStats(1.15f, 1.3f, 6.6f));
                TinkerRegistry.addMaterialStats(titanium, new FletchingMaterialStats(1.0f, 1.3f));

                PlusTiC.materials.put("titanium", titanium);
            }
        }
    }

}
