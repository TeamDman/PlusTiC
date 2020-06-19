package colonelgerdauf.plustic.proxy;

import java.util.*;

import javax.annotation.*;
import colonelgerdauf.plustic.api.*;
import colonelgerdauf.plustic.api.Sounds;
import colonelgerdauf.plustic.entity.*;
import colonelgerdauf.plustic.entity.render.*;
import colonelgerdauf.plustic.modules.*;
import colonelgerdauf.plustic.traits.*;
import colonelgerdauf.plustic.util.*;
import net.minecraft.client.*;
import net.minecraft.client.audio.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.client.settings.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.client.registry.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.settings.*;
import net.minecraftforge.common.capabilities.*;

import org.lwjgl.input.*;

import com.google.common.collect.*;

import slimeknights.tconstruct.common.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.tools.*;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	private static Map<String, KeyBinding> keyBindings;
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
	    registerItemRenderer(item, meta, id, "inventory");
	}
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id, String variant) {
		ModelResourceLocation rl = new ModelResourceLocation(ModInfo.MODID + ":" + id, variant);
		if (meta >= 0)  {
			ModelLoader.setCustomModelResourceLocation(item, meta, rl);
		} else {
			ModelLoader.setCustomMeshDefinition(item, stack -> rl);
			ModelBakery.registerItemVariants(item, rl);
		}
	}
	
	@Override
	public void setRenderInfo(Material mat, int color) {
		mat.setRenderInfo(color);
	}
	
	@Override
	public void setRenderInfo(Material mat, int lo, int mid, int hi) {
		mat.setRenderInfo(new MaterialRenderInfo.MultiColor(lo, mid, hi));
	}
	
	@Override
	public void registerFluidModels(Fluid fluid) {
		if (fluid == null) return;
		Block block = fluid.getBlock();
		if (block != null) {
			Item item = Item.getItemFromBlock(block);
			FluidStateMapper mapper = new FluidStateMapper(fluid);
			if (item != null) {
				ModelBakery.registerItemVariants(item);
				ModelLoader.setCustomMeshDefinition(item, mapper);
			}
			ModelLoader.setCustomStateMapper(block, mapper);
		}
	}
	
	@Override
	public void registerKeyBindings() {
		keyBindings = ImmutableMap.<String, KeyBinding>builder()
				.put("release_entity", new KeyBinding("key.plustic_release_entity.desc", KeyConflictContext.IN_GAME, Keyboard.KEY_0, "key.categories.plustic"))
				.put("toggle_gui", new KeyBinding("key.plustic_toggle_gui.desc", KeyConflictContext.IN_GAME, Keyboard.KEY_I, "key.categories.plustic"))
				.put("set_portal", new KeyBinding("key.plustic_set_portal.desc", KeyConflictContext.IN_GAME, Keyboard.KEY_N, "key.categories.plustic"))
				.put("brown_magic", new KeyBinding("key.plustic_brown_magic.desc", KeyConflictContext.IN_GAME, Keyboard.KEY_O, "key.categories.plustic"))
				.put("toggle_tool", new KeyBinding("key.plustic_toggle_tool.desc", KeyConflictContext.IN_GAME, KeyModifier.ALT, Keyboard.KEY_COMMA, "key.categories.plustic"))
				.put("fruit_salad", new KeyBinding("key.plustic_fruit_salad.desc", KeyConflictContext.IN_GAME, KeyModifier.ALT, Keyboard.KEY_MINUS, "key.categories.plustic"))
				.put("mots", new KeyBinding("key.plustic_mots.desc", KeyConflictContext.IN_GAME, KeyModifier.ALT, Keyboard.KEY_EQUALS, "key.categories.plustic"))
				.build();
		for (KeyBinding kb: keyBindings.values()) ClientRegistry.registerKeyBinding(kb);
	}
	
	@Override
	public void registerToolModel(ToolCore tc) {
		ModelRegisterUtil.registerToolModel(tc);
	}
	
	@Override
	public void registerModifierModel(IModifier mod, ResourceLocation rl) {
		ModelRegisterUtil.registerModifierModel(mod, rl);
	}
	
	@Override
	public <T extends Item & IToolPart> void registerToolPartModel(T part) {
		ModelRegisterUtil.registerPartModel(part);
	}
	
	@Override
	public void initEntities() {
		super.initEntities();
		RenderingRegistry.registerEntityRenderingHandler(EntityBlindBandit.class, RenderBlindBandit::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySupremeLeader.class, RenderSupremeLeader::new);
	}
	
	@Override
	public void initToolGuis() {
		if (ModuleTools.katana != null) {
			ToolBuildGuiInfo katanaInfo = new ToolBuildGuiInfo(ModuleTools.katana);
			katanaInfo.addSlotPosition(33, 42 + 18); // handle
			katanaInfo.addSlotPosition(33 + 20, 42 - 20); // 1st blade
			katanaInfo.addSlotPosition(33, 42); // 2nd blade
			katanaInfo.addSlotPosition(33 - 18, 42 + 18); // binding
			TinkerRegistryClient.addToolBuilding(katanaInfo);
		}
		
		if (ModuleTools.laserGun != null) {
			ToolBuildGuiInfo laserGunInfo = new ToolBuildGuiInfo(ModuleTools.laserGun);
			laserGunInfo.addSlotPosition(7, 64);
			laserGunInfo.addSlotPosition(25, 38);
			laserGunInfo.addSlotPosition(49, 38);
			laserGunInfo.addSlotPosition(7, 38);
			TinkerRegistryClient.addToolBuilding(laserGunInfo);
		}
	}
	
	@Override
	public boolean isControlPressed(String control) {
		return keyBindings.get(control).isPressed();
	}
	
	@CapabilityInject(MusicOfTheSpheres.IMOTSItemHandler.class)
	private static Capability<MusicOfTheSpheres.IMOTSItemHandler> MOTS_ITEM_CAP = null;
	
	@Override
	public Object setAndPlaySound(EntityPlayer player, SoundEvent sndEv) {
		Sounds.MOTSSound sound = new Sounds.MOTSSound(player, sndEv, SoundCategory.MUSIC);
		//System.out.println("TEEHEE! "+sound.getSound());
		Minecraft.getMinecraft().getSoundHandler().playSound(sound);
		return sound;
	}
	
	@Override
	public void stopSound(Object sound) {
		if (sound instanceof ISound) {
			Minecraft.getMinecraft().getSoundHandler().stopSound((ISound)sound);
		}
	}
	
	@Override
	public boolean isSoundPlaying(Object sound) {
		return sound instanceof ISound && Minecraft.getMinecraft().getSoundHandler().isSoundPlaying((ISound)sound);
	}
	
	@Override
	public void runOnClient(RunnableDefaultNoop runnable) {
		runnable.run();
	}
	
	@Override
	public <T> T runOnClient(SupplierDefaultNoop<T> supplier) {
		return supplier.get();
	}
	
	public static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {
		public final Fluid fluid;
		public final ModelResourceLocation location;
		
		public FluidStateMapper(Fluid fluid) {
			this.fluid = fluid;
			this.location = new ModelResourceLocation(new ResourceLocation(ModInfo.MODID, "fluid_block"),
					fluid.getName());
		}
		
		@Nonnull
		@Override
		protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
			return location;
		}
		
		@Nonnull
		@Override
		public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
			return location;
		}
	}
}
