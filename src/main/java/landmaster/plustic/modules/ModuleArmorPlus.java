package landmaster.plustic.modules;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import landmaster.plustic.traits.Apocalypse;
import landmaster.plustic.traits.DivineShield;
import landmaster.plustic.util.Utils;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import static slimeknights.tconstruct.library.materials.MaterialTypes.HEAD;
import static slimeknights.tconstruct.library.utils.HarvestLevels.COBALT;
import static slimeknights.tconstruct.tools.TinkerTraits.aquadynamic;

public class ModuleArmorPlus implements IModule {

    public void init() {
        if (Config.armorPlus && Loader.isModLoaded("armorplus")) {
            Material witherBone = new Material("witherbone", TextFormatting.BLACK);
            witherBone.addTrait(Apocalypse.apocalypse);
            witherBone.addItem("witherBone", 1, Material.VALUE_Ingot);
            witherBone.setCraftable(true);
            PlusTiC.proxy.setRenderInfo(witherBone, 0x000000);
            TinkerRegistry.addMaterialStats(witherBone, new ArrowShaftMaterialStats(1.0f, 20));
            PlusTiC.materials.put("witherbone", witherBone);

            Material guardianScale = new Material("guardianscale", TextFormatting.AQUA);
            guardianScale.addTrait(DivineShield.divineShield, HEAD);
            guardianScale.addTrait(aquadynamic);
            guardianScale.addItem("scaleGuardian", 1, Material.VALUE_Ingot);
            guardianScale.setCraftable(true);
            PlusTiC.proxy.setRenderInfo(guardianScale, 0x00FFFF);
            TinkerRegistry.addMaterialStats(guardianScale, new HeadMaterialStats(600, 6.2f, 7, COBALT));
            TinkerRegistry.addMaterialStats(guardianScale, new HandleMaterialStats(0.9f, 40));
            TinkerRegistry.addMaterialStats(guardianScale, new ExtraMaterialStats(80));
            TinkerRegistry.addMaterialStats(guardianScale, new BowMaterialStats(0.85f, 1.2f, 5.5f));
            PlusTiC.materials.put("guardianscale", guardianScale);
        }
    }

    public void init2() {
        // NOT REGISTERING YOUR OREDICTS IN PREINIT, ARMORPLUS?
        Utils.setDispItem(PlusTiC.materials.get("witherbone"), "witherBone");
        Utils.setDispItem(PlusTiC.materials.get("guardianscale"), "scaleGuardian");
    }

}
