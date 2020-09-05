package landmaster.plustic.modules;

import c4.conarm.lib.materials.*;
import c4.conarm.lib.utils.*;
import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import landmaster.plustic.modifiers.armor.JetpackPancakeHippos;
import landmaster.plustic.traits.NickOfTime;
import landmaster.plustic.traits.Terrafirma;
import landmaster.plustic.traits.armor.*;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.TinkerRegistry;

import java.util.Optional;

import static c4.conarm.common.armor.traits.ArmorTraits.*;

public class ModuleConArm implements IModule {
    @Override
    public void init() {
        if (Config.constructsArmory && Loader.isModLoaded("conarm")) {
            Optional.ofNullable(PlusTiC.materials.get("emerald")).ifPresent(mat -> {
                ArmorMaterials.addArmorTrait(mat, GoodFridayAgreement.goodfridayagreement);
                ArmorMaterials.addArmorTrait(mat, Terrafirma.terrafirma.get(0));
                TinkerRegistry.addMaterialStats(mat,
                        new CoreMaterialStats(18, 20),
                        new PlatesMaterialStats(1, 9, 2.2f),
                        new TrimMaterialStats(13.5f));
            });
            Optional.ofNullable(PlusTiC.materials.get("alumite")).ifPresent(mat -> {
                ArmorMaterials.addArmorTrait(mat, CamDaiBay.camdaibay, ArmorMaterialType.CORE);
                ArmorMaterials.addArmorTrait(mat, DunansTransport.dunanstransport);
                TinkerRegistry.addMaterialStats(mat,
                        new CoreMaterialStats(14, 20),
                        new PlatesMaterialStats(1, 7, 2.2f),
                        new TrimMaterialStats(10f));
            });
            Optional.ofNullable(PlusTiC.materials.get("nickel")).ifPresent(mat -> {
                ArmorMaterials.addArmorTrait(mat, NickOfTime.nickOfTime, magnetic);
                TinkerRegistry.addMaterialStats(mat,
                        new CoreMaterialStats(13, 16.5f),
                        new PlatesMaterialStats(1, -0.1f, 1),
                        new TrimMaterialStats(4.25f));
            });
            Optional.ofNullable(PlusTiC.materials.get("invar")).ifPresent(mat -> {
                ArmorMaterials.addArmorTrait(mat, Invariant.invariant);
                ArmorMaterials.addArmorTrait(mat, magnetic);
                TinkerRegistry.addMaterialStats(mat,
                        new CoreMaterialStats(16.7f, 18f),
                        new PlatesMaterialStats(1.2f, 0, 5),
                        new TrimMaterialStats(7f));
            });
            Optional.ofNullable(PlusTiC.materials.get("iridium")).ifPresent(mat -> {
                ArmorMaterials.addArmorTrait(mat, alien, dense);
                TinkerRegistry.addMaterialStats(mat,
                        new CoreMaterialStats(14.6f, 17.2f),
                        new PlatesMaterialStats(1.15f, -0.5f, 2.4f),
                        new TrimMaterialStats(5f));
            });

            Optional.ofNullable(PlusTiC.materials.get("sapphire")).ifPresent(mat -> {
                ArmorMaterials.addArmorTrait(mat, aquaspeed);
                TinkerRegistry.addMaterialStats(mat,
                        new CoreMaterialStats(14, 20),
                        new PlatesMaterialStats(1, 7, 2.2f),
                        new TrimMaterialStats(10f)); // TODO refine stats
            });
            Optional.ofNullable(PlusTiC.materials.get("ruby")).ifPresent(mat -> {
                ArmorMaterials.addArmorTrait(mat, HaoransCult.haoranscult);
                TinkerRegistry.addMaterialStats(mat,
                        new CoreMaterialStats(14, 20),
                        new PlatesMaterialStats(1, 7, 2.2f),
                        new TrimMaterialStats(10f)); // TODO refine stats
            });
            Optional.ofNullable(PlusTiC.materials.get("peridot")).ifPresent(mat -> {
                ArmorMaterials.addArmorTrait(mat, TomAndJerry.tomAndJerry);
                TinkerRegistry.addMaterialStats(mat,
                        new CoreMaterialStats(14, 20),
                        new PlatesMaterialStats(1, 7, 2.2f),
                        new TrimMaterialStats(10f)); // TODO refine stats
            });

            if (Config.jetpackConarmModifier && Loader.isModLoaded("simplyjetpacks")) {
                SJ.init();
            }
        }
    }

    private static class SJ {
        public static void init() {
            JetpackPancakeHippos.jetpackpancakehippos.values().forEach(mod -> {
                //System.out.println(mod.jetpack.getStackJetpack());
                RecipeMatchHolder.addItem(mod, mod.jetpack.getStackJetpack(), 1, 1);
            });
        }
    }
}
