package landmaster.plustic.proxy;

import landmaster.plustic.*;
import landmaster.plustic.api.*;
import landmaster.plustic.entity.*;
import landmaster.plustic.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.registry.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.tools.*;

public class CommonProxy {
	
	public void registerItemRenderer(Item item, int meta, String id) {
	}
	
	public void registerItemRenderer(Item item, int meta, String id, String variant) {
	}
	
	public void setRenderInfo(Material mat, int color) {
	}
	
	public void setRenderInfo(Material mat, int lo, int mid, int hi) {
	}
	
	public void registerFluidModels(Fluid fluid) {
	}
	
	public void registerKeyBindings() {
	}
	
	public void registerToolModel(ToolCore tc) {
	}
	
	public void registerModifierModel(IModifier mod, ResourceLocation rl) {
	}
	
	public <T extends Item & IToolPart> void registerToolPartModel(T part) {
	}
	
	public void initEntities() {
		EntityRegistry.registerModEntity(new ResourceLocation(ModInfo.MODID, "supremeleader"), EntitySupremeLeader.class, "SupremeLeader", 0, PlusTiC.INSTANCE, 96, 2, true, 0xFF0000, 0x0000FF);
		EntityRegistry.registerModEntity(new ResourceLocation(ModInfo.MODID, "blindbandit"), EntityBlindBandit.class, "BlindBandit", 1, PlusTiC.INSTANCE, 64, 3, true, 0xFF00FF, 0xFF0000);
	}
	
	public void initToolGuis() {
	}
	
	public boolean isControlPressed(String control) {
		return false;
	}
	
	public Object setAndPlaySound(EntityPlayer player, SoundEvent sndEv) {
		return null;
	}
	
	public void stopSound(Object sound) {
	}
	
	public boolean isSoundPlaying(Object sound) {
		return false;
	}
	
	public void runOnClient(RunnableDefaultNoop runnable) {
	}
	
	public <T> T runOnClient(SupplierDefaultNoop<T> supplier) {
		return null;
	}
}
