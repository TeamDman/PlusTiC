package landmaster.plustic.modules;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import landmaster.plustic.tools.ToolKatana;
import landmaster.plustic.tools.ToolLaserGun;
import landmaster.plustic.tools.parts.ToolPartWithStoneMold;
import landmaster.plustic.tools.stats.BatteryCellMaterialStats;
import landmaster.plustic.tools.stats.LaserMediumMaterialStats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public class ModuleTools implements IModule {
    private static final List<ToolCore> tools = new ArrayList<>();
    private static final List<IToolPart> toolParts = new ArrayList<>();
    public static ToolKatana katana;
    public static ToolLaserGun laserGun;
    public static ToolPart pipe_piece;
    public static ToolPart laser_medium;
    public static ToolPart battery_cell;

    @SubscribeEvent
    public static void initItems(RegistryEvent.Register<Item> event) {
        pipe_piece = new ToolPartWithStoneMold(Material.VALUE_Ingot * 4);
        pipe_piece.setTranslationKey("pipe_piece").setRegistryName("pipe_piece");
        event.getRegistry().register(pipe_piece);
        TinkerRegistry.registerToolPart(pipe_piece);
        PlusTiC.proxy.registerToolPartModel(pipe_piece);
        toolParts.add(pipe_piece);

        laser_medium = new ToolPartWithStoneMold(Material.VALUE_Ingot * 3);
        laser_medium.setTranslationKey("laser_medium").setRegistryName("laser_medium");
        event.getRegistry().register(laser_medium);
        TinkerRegistry.registerToolPart(laser_medium);
        PlusTiC.proxy.registerToolPartModel(laser_medium);
        toolParts.add(laser_medium);

        battery_cell = new ToolPartWithStoneMold(Material.VALUE_Ingot * 3);
        battery_cell.setTranslationKey("battery_cell").setRegistryName("battery_cell");
        event.getRegistry().register(battery_cell);
        TinkerRegistry.registerToolPart(battery_cell);
        PlusTiC.proxy.registerToolPartModel(battery_cell);
        toolParts.add(battery_cell);

        if (Config.laserGun) {
            laserGun = new ToolLaserGun();
            event.getRegistry().register(laserGun);
            TinkerRegistry.registerToolForgeCrafting(laserGun);
            PlusTiC.proxy.registerToolModel(laserGun);

            tools.add(laserGun);

            // Vanilla Tinkers Laser material stats
            TinkerRegistry.addMaterialStats(TinkerMaterials.prismarine, new LaserMediumMaterialStats(2.5f, 20));
            TinkerRegistry.addMaterialStats(TinkerMaterials.blaze, new BatteryCellMaterialStats(85000),
                    new LaserMediumMaterialStats(3.2f, 17));
            TinkerRegistry.addMaterialStats(TinkerMaterials.endrod, new BatteryCellMaterialStats(260000),
                    new LaserMediumMaterialStats(8.6f, 38));
            TinkerRegistry.addMaterialStats(TinkerMaterials.copper, new BatteryCellMaterialStats(55000));
            TinkerRegistry.addMaterialStats(TinkerMaterials.silver, new BatteryCellMaterialStats(75000));
            TinkerRegistry.addMaterialStats(TinkerMaterials.manyullyn, new BatteryCellMaterialStats(120000));
        }

        if (Config.katana) {
            katana = new ToolKatana();
            event.getRegistry().register(katana);
            TinkerRegistry.registerToolForgeCrafting(katana);
            PlusTiC.proxy.registerToolModel(katana);

            tools.add(katana);
        }

        for (final IToolPart part : getPlusTiCToolParts()) {
            for (final ToolCore tool : getPlusTiCTools()) {
                for (final PartMaterialType pmt : tool.getRequiredComponents()) {
                    if (pmt.getPossibleParts().contains(part)) {
                        TinkerRegistry.registerStencilTableCrafting(Pattern.setTagForPart(new ItemStack(TinkerTools.pattern), (Item) part));
                    }
                }
            }
        }

        // for added PlusTiC tools
        // TODO add more modifier jsons
        for (IModifier modifier : new IModifier[]{
                TinkerModifiers.modBaneOfArthopods,
                TinkerModifiers.modBeheading,
                TinkerModifiers.modDiamond,
                TinkerModifiers.modEmerald,
                TinkerModifiers.modGlowing,
                TinkerModifiers.modHaste,
                TinkerModifiers.modKnockback,
                TinkerModifiers.modLuck,
                TinkerModifiers.modMendingMoss,
                TinkerModifiers.modNecrotic,
                TinkerModifiers.modReinforced,
                TinkerModifiers.modSharpness,
                TinkerModifiers.modShulking,
                TinkerModifiers.modSilktouch,
                TinkerModifiers.modSmite,
                TinkerModifiers.modSoulbound,
                TinkerModifiers.modWebbed,
        }) {
            PlusTiC.proxy.registerModifierModel(modifier,
                    new ResourceLocation(ModInfo.MODID, "models/item/modifiers/" + modifier.getIdentifier()));
        }
    }

    public static List<ToolCore> getPlusTiCTools() {
        return Collections.unmodifiableList(tools);
    }

    public static List<IToolPart> getPlusTiCToolParts() {
        return Collections.unmodifiableList(toolParts);
    }
}
