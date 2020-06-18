package colonelgerdauf.plustic.modules;

import colonelgerdauf.plustic.*;
import colonelgerdauf.plustic.api.*;
import colonelgerdauf.plustic.block.*;
import colonelgerdauf.plustic.config.*;
import colonelgerdauf.plustic.item.*;
import colonelgerdauf.plustic.tile.*;
import colonelgerdauf.plustic.tile.render.*;
import colonelgerdauf.plustic.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.client.registry.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraftforge.fml.relauncher.*;

public class ModuleMachines implements IModule {
	public static final BlockCentrifuge centrifuge = new BlockCentrifuge();
	
	static {
		MinecraftForge.EVENT_BUS.register(ModuleMachines.class);
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
}
