package landmaster.plustic.modules;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import landmaster.plustic.fluids.FluidMolten;
import landmaster.plustic.tools.stats.LaserMediumMaterialStats;
import landmaster.plustic.traits.Apocalypse;
import landmaster.plustic.traits.BrownMagic;
import landmaster.plustic.traits.Global;
import landmaster.plustic.util.Utils;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

public class ModuleAvaritia implements IModule {
    public void init() {
        if (Config.avaritia && Loader.isModLoaded("avaritia")) {
            Material infinity = new Material("infinity_avaritia_plustic", TextFormatting.LIGHT_PURPLE);
            infinity.addTrait(Global.global);
            infinity.addTrait(BrownMagic.brownmagic);
            infinity.addTrait(Apocalypse.apocalypse);
            infinity.addItem("ingotInfinity", 1, Material.VALUE_Ingot);
            infinity.setCraftable(false).setCastable(true);
            PlusTiC.proxy.setRenderInfo(infinity, 0x82FF96, 0x7C92FF, 0xEA84FF);

            FluidMolten infinityFluid = Utils.fluidMetal("infinity", 0xaaaaFF);
            infinityFluid.setTemperature(1400);
            infinityFluid.setRarity(EnumRarity.EPIC);
            Utils.initFluidMetal(infinityFluid);
            infinity.setFluid(infinityFluid);

            TinkerRegistry.addMaterialStats(infinity,
                    new HeadMaterialStats(7000, 18, 40, 10),
                    new HandleMaterialStats(2.0f, 0),
                    new ExtraMaterialStats(1400),
                    new BowMaterialStats(2.0f, 2.8f, 16),
                    new LaserMediumMaterialStats(65, 100));

            PlusTiC.materials.put("infinity", infinity);
        }
    }

    public void init2() {
        // SAME HERE, AVARITIA?
        Utils.setDispItem(PlusTiC.materials.get("infinity"), "ingotInfinity");
    }
}
