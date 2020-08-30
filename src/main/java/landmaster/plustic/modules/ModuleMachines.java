package landmaster.plustic.modules;

import landmaster.plustic.ModInfo;
import landmaster.plustic.PlusTiC;
import landmaster.plustic.block.BlockCentrifuge;
import landmaster.plustic.config.Config;
import landmaster.plustic.item.ItemBlockMeta;
import landmaster.plustic.tile.TECentrifugeCore;
import landmaster.plustic.tile.TECentrifugeTank;
import landmaster.plustic.tile.render.RenderTECentrifuge;
import landmaster.plustic.util.RunnableDefaultNoop;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleMachines implements IModule {
    public static final BlockCentrifuge centrifuge = new BlockCentrifuge();

    static {
        MinecraftForge.EVENT_BUS.register(ModuleMachines.class);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        if (Config.machines) {
            event.getRegistry().register(centrifuge);
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        if (Config.machines) {
            event.getRegistry().register(new ItemBlockMeta(centrifuge).setRegistryName(centrifuge.getRegistryName()));
            PlusTiC.proxy.registerItemRenderer(Item.getItemFromBlock(centrifuge), 0, "centrifuge", "core=false");
            PlusTiC.proxy.registerItemRenderer(Item.getItemFromBlock(centrifuge), 1, "centrifuge", "core=true");
        }
    }

    @Override
    public void init() {
        if (Config.machines) {
            GameRegistry.registerTileEntity(TECentrifugeCore.class, new ResourceLocation(ModInfo.MODID, "centrifuge_core"));
            GameRegistry.registerTileEntity(TECentrifugeTank.class, new ResourceLocation(ModInfo.MODID, "centrifuge_tank"));

            PlusTiC.proxy.runOnClient(new RunnableDefaultNoop() {
                @SideOnly(Side.CLIENT)
                @Override
                public void run() {
                    RenderTECentrifuge centrifugeRender = new RenderTECentrifuge();
                    ClientRegistry.bindTileEntitySpecialRenderer(TECentrifugeCore.class, centrifugeRender);
                    ClientRegistry.bindTileEntitySpecialRenderer(TECentrifugeTank.class, centrifugeRender);
                }
            });
        }
    }
}
