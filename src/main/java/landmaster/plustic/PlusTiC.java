package landmaster.plustic;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import landmaster.plustic.config.Config;
import landmaster.plustic.gui.handler.PTGuiHandler;
import landmaster.plustic.modules.*;
import landmaster.plustic.net.PacketHandler;
import landmaster.plustic.proxy.CommonProxy;
import landmaster.plustic.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerMaterials;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Mod.EventBusSubscriber
@Mod(modid = ModInfo.MODID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDS, useMetadata = true, acceptedMinecraftVersions = "[1.12, 1.13)")
public class PlusTiC {
    public static final Logger log = LogManager.getLogger(
            ModInfo.MODID.toUpperCase(Locale.US/* to avoid problems with Turkish */));
    public static final Map<String, Material> materials = new LinkedHashMap<>();
    public static final Map<String, MaterialIntegration> materialIntegrations = new Object2ObjectOpenHashMap<>();
    public static final Map<String, CompletionStage<?>> materialIntegrationStages = new Object2ObjectOpenHashMap<>();
    public static final Map<String, String> materialOreDicts = new Object2ObjectOpenHashMap<>();
    public static final BowMaterialStats justWhy = new BowMaterialStats(0.2f, 0.4f, -1f);
    private static final String[] renamesToHandle = new String[]{
            "osmium", "titanium", "iridium", "enderium", "lumium", "platinum", "signalum", "invar"};
    public static Config config;
    @Instance(ModInfo.MODID)
    public static PlusTiC INSTANCE;
    @SidedProxy(serverSide = "landmaster.plustic.proxy.CommonProxy", clientSide = "landmaster.plustic.proxy.ClientProxy")
    public static CommonProxy proxy;

    @SubscribeEvent
    public static void missingBlockMappings(RegistryEvent.MissingMappings<Block> event) {
        event.getMappings().forEach(mapping -> {
            for (String name : renamesToHandle) {
                if (mapping.key.equals(new ResourceLocation(ModInfo.MODID, ModInfo.MODID + ".molten_" + name))) {
                    Optional.ofNullable(FluidRegistry.getFluid(name))
                            .map(Fluid::getBlock)
                            .ifPresent(mapping::remap);
                }
            }

            if (mapping.key.equals(new ResourceLocation(ModInfo.MODID, ModInfo.MODID + ".molten_fiery"))) {
                Optional.ofNullable(FluidRegistry.getFluid("fierymetal"))
                        .map(Fluid::getBlock)
                        .ifPresent(mapping::remap);
            }
        });
    }

    @SubscribeEvent
    public static void missingItemMappings(RegistryEvent.MissingMappings<Item> event) {
        event.getMappings().forEach(mapping -> {
            for (String name : renamesToHandle) {
                if (mapping.key.equals(new ResourceLocation(ModInfo.MODID, ModInfo.MODID + ".molten_" + name))) {
                    Optional.ofNullable(FluidRegistry.getFluid(name))
                            .map(Fluid::getBlock)
                            .map(Item::getItemFromBlock)
                            .ifPresent(mapping::remap);
                }

                if (mapping.key.equals(new ResourceLocation(ModInfo.MODID, ModInfo.MODID + ".molten_fiery"))) {
                    Optional.ofNullable(FluidRegistry.getFluid("fierymetal"))
                            .map(Fluid::getBlock)
                            .map(Item::getItemFromBlock)
                            .ifPresent(mapping::remap);
                }
            }
        });
    }

    private static void preIntegrate() {
        materials.forEach((k, v) -> {
            if (!materialIntegrations.containsKey(k)) {
                materialIntegrationStages.getOrDefault(k, CompletableFuture.completedFuture(null)).thenRun(() -> {
                    MaterialIntegration mi;
                    if (v.getRepresentativeItem().getItem() == Items.EMERALD) {
                        mi = new MaterialIntegration(v, v.getFluid()).toolforge();
                    } else if (v.getFluid() != null) {
                        mi = new MaterialIntegration(v, v.getFluid(), StringUtils.capitalize(k)).toolforge();
                    } else {
                        mi = new MaterialIntegration(v);
                    }
                    if (materialOreDicts.containsKey(k)) {
                        mi.representativeItem = materialOreDicts.get(k);
                    }
                    TinkerRegistry.integrate(mi).preInit();
                    materialIntegrations.put(k, mi);
                });
            }
        });
    }

    private static void postIntegrate() {
        Utils.displace(TinkerMaterials.wood.getIdentifier()); // so that natura woods are prioritized
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        (config = new Config(event)).init1();

        proxy.initEntities();

        // TODO add more modules when needed

        IModule.modules.addAll(Arrays.asList(
                new ModuleBase(),

                new ModuleNatura(),
                new ModuleGems(),
                new ModuleMekanism(),
                new ModuleAdvRocketry(),
                new ModuleBotania(),
                new ModuleArmorPlus(),
                new ModuleTF(),
                new ModuleDraconicEvolution(),
                new ModuleActAdd(),
                new ModulePsi(),
                new ModuleAvaritia(),
                new ModuleLandCraft(),
                new ModuleLandCore(),
                new ModuleGalacticraft(),
                new ModuleSurvivalist(),
                new ModuleProjectE(),
                new ModuleGemsPlus(),
                new ModuleAppEng2(),
                new ModuleEnvironTech(),
                new ModuleMFR(),
                new ModuleThaumcraft(),
                new ModuleAstralSorcery(),

                new ModuleTools(),
                new ModuleModifiers(),

                new ModuleConArm(),

                new ModuleMachines()
        ));

        IModule.modules.forEach(IModule::init);

        config.init2(materials);

        config.update();

        preIntegrate();

        FMLInterModComms.sendMessage("waila", "register", "landmaster.plustic.waila.PTWailaRegistrar.wailaCallback");

        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new PTGuiHandler());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.initToolGuis();
        proxy.registerKeyBindings();
        PacketHandler.init();

        IModule.modules.forEach(IModule::init2);

        postIntegrate();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        IModule.modules.forEach(IModule::init3);
    }
}
