package colonelgerdauf.plustic.traits;

import java.util.*;

import javax.annotation.*;

import com.google.common.collect.*;

import colonelgerdauf.plustic.*;
import colonelgerdauf.plustic.api.*;
import colonelgerdauf.plustic.net.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.items.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.utils.*;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class MusicOfTheSpheres extends AbstractTrait {
	public static final MusicOfTheSpheres musicofthespheres = new MusicOfTheSpheres();
	
	public static final ResourceLocation MOTS_CAPLOCATION = new ResourceLocation(ModInfo.MODID, "musicofthespheres_cap");
	public static final @Deprecated ResourceLocation MOTS_OLDCAPLOCATION = new ResourceLocation(ModInfo.MODID, "");
	
	public MusicOfTheSpheres() {
		super("musicofthespheres", 0xffffff);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	static {
		// see analogous comment at FruitSalad.java
		CapabilityManager.INSTANCE.register(IMOTSItemHandler.class, (Capability.IStorage)CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage(), MOTSItemHandler::new);
	}
	
	public static interface IMOTSItemHandler extends IItemHandler {
		void play(EntityPlayer player, SoundEvent sndEv);
		void stop(EntityPlayer player);
	}
	
	private static class MOTSItemHandler extends ItemStackHandler implements IMOTSItemHandler {
		@CapabilityInject(IMOTSItemHandler.class)
		private static Capability<IMOTSItemHandler> MOTS_ITEM_CAP = null;
		
		private static Map<UUID, Object> playerToSound = new MapMaker().weakValues().makeMap();
		
		@Override
		protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
			if (stack.getItem() instanceof ItemRecord) {
				return super.getStackLimit(slot, stack);
			}
			return 0;
		}

		@Override
		public void play(EntityPlayer player, SoundEvent sndEv) {
			if (!player.world.isRemote) {
				PacketHandler.INSTANCE.sendToDimension(new PacketPlayMOTS(player, ForgeRegistries.SOUND_EVENTS.getKey(sndEv)), player.world.provider.getDimension());
			} else {
				//System.out.println("TEEHEE "+sound.get());
				if (!PlusTiC.proxy.isSoundPlaying(playerToSound.get(player.getUniqueID()))) {
					playerToSound.put(player.getUniqueID(), PlusTiC.proxy.setAndPlaySound(player, sndEv));
				}
			}
		}
		
		@Override
		public void stop(EntityPlayer player) {
			if (!player.world.isRemote) {
				PacketHandler.INSTANCE.sendToDimension(new PacketStopMOTS(player), player.world.provider.getDimension());
			} else {
				//System.out.println("HAHAHA "+sound);
				if (playerToSound.containsKey(player.getUniqueID())) {
					PlusTiC.proxy.stopSound(playerToSound.get(player.getUniqueID()));
					playerToSound.remove(player.getUniqueID());
				}
			}
		}
	}
	
	private static class MOTSItemHandlerCapProvider implements ICapabilitySerializable<NBTTagCompound> {
		@CapabilityInject(IMOTSItemHandler.class)
		private static Capability<IMOTSItemHandler> MOTS_ITEM_CAP = null;
		
		private final MOTSItemHandler cap;
		
		public MOTSItemHandlerCapProvider() {
			cap = new MOTSItemHandler();
		}
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == MOTS_ITEM_CAP;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if (capability == MOTS_ITEM_CAP) {
				return (T)cap;
			}
			return null;
		}

		@Override
		public NBTTagCompound serializeNBT() {
			return cap.serializeNBT();
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			if (!nbt.isEmpty()) {
				cap.deserializeNBT(nbt);
			}
		}
		
	}
	
	@SubscribeEvent
	public void addMOTSCapability(AttachCapabilitiesEvent<ItemStack> event) {
		if (event.getObject().getItem() instanceof ToolCore) {
			MOTSItemHandlerCapProvider provider = new MOTSItemHandlerCapProvider();
			event.addCapability(MOTS_OLDCAPLOCATION, provider);
			event.addCapability(MOTS_CAPLOCATION, provider);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void MOTSGUI(InputEvent.KeyInputEvent event) {
		if (PlusTiC.proxy.isControlPressed("mots") && TinkerUtil.hasTrait(
				TagUtil.getTagSafe(Minecraft.getMinecraft().player.getHeldItemMainhand()), identifier)) {
			PacketHandler.INSTANCE.sendToServer(new PacketOpenMOTSGui());
		}
	}
}
