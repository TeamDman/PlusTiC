package landmaster.plustic.modules;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import landmaster.plustic.fluids.FluidMolten;
import landmaster.plustic.traits.BrownMagic;
import landmaster.plustic.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import static slimeknights.tconstruct.library.utils.HarvestLevels.COBALT;
import static slimeknights.tconstruct.tools.TinkerTraits.alien;
import static slimeknights.tconstruct.tools.TinkerTraits.enderference;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public class ModuleGalacticraft implements IModule {
    @GameRegistry.ObjectHolder(value = "galacticraftplanets:item_basic_mars")
    public static final Item item_basic_mars = null;

    private static Material desh;

    public void init() {
        if (Config.galacticraft && Loader.isModLoaded("galacticraftcore")) {
            if (Loader.isModLoaded("galacticraftplanets")) {
                desh = new Material("desh", 0x161616);
                desh.addTrait(alien);
                desh.addTrait(enderference);
                desh.addTrait(BrownMagic.brownmagic);
                desh.setCraftable(false).setCastable(true);
                desh.addItem("ingotDesh", 1, Material.VALUE_Ingot);
                PlusTiC.proxy.setRenderInfo(desh, 0x161616);

                FluidMolten deshFluid = Utils.fluidMetal("desh", 0x161616);
                deshFluid.setTemperature(821); // {821,823,827,829} are quadruplet primes
                Utils.initFluidMetal(deshFluid);
                desh.setFluid(deshFluid);

                TinkerRegistry.addMaterialStats(desh,
                        new HeadMaterialStats(1729/* 1729 is a taxicab number */, (float) (Math.E * Math.PI), 8, COBALT),
                        new HandleMaterialStats((float) (Math.sqrt(5) + 1) / 2/* the golden ratio */, 0),
                        new ExtraMaterialStats(257),
                        new BowMaterialStats(1 / (float) Math.E, (float) Math.E, 12));

                PlusTiC.materials.put("desh", desh);
            }
        }
    }

    public void init2() {
        if (Config.galacticraft && Loader.isModLoaded("galacticraftcore")) {
            if (Loader.isModLoaded("galacticraftplanets")) {
                TinkerRegistry.registerMelting(new ItemStack(item_basic_mars), FluidRegistry.getFluid("desh"), Material.VALUE_Ingot);
                OreDictionary.registerOre("ingotDesh", new ItemStack(item_basic_mars, 1, 2)); // see init3
            }
        }
    }

    public void init3() {
        if (Config.galacticraft && Loader.isModLoaded("galacticraftcore")) {
            if (Loader.isModLoaded("galacticraftplanets")) {
                // Seriously? Registering oredicts *this* late? -_-
                Utils.setDispItem(desh, "ingotDesh");
            }
        }
    }
}
