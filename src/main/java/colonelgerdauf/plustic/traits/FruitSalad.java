package colonelgerdauf.plustic.traits;

import javax.annotation.*;

import colonelgerdauf.plustic.*;
import colonelgerdauf.plustic.api.*;
import colonelgerdauf.plustic.config.Config;
import colonelgerdauf.plustic.net.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.items.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.utils.*;

/**
 * First step, meme this trait to death…
 * @author colonelgerdauf
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FruitSalad extends AbstractTrait {
	public static final FruitSalad fruitsalad = new FruitSalad();
	
	//public static final String FRUITSALAD_TAG = "PlusTiC_FruitSalad";
	
	public static final int FSHANDLER_SZ = 5;
	
	public static final ResourceLocation FRUITSALAD_CAPLOCATION = new ResourceLocation(ModInfo.MODID, "fruitsalad_cap");
	
	public FruitSalad() {
		super("fruitsalad", 0x77007f);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	static {
		// Basically, the base item handler capabilty's IStorage and this IStorage are one and the same.
		CapabilityManager.INSTANCE.register(IFSItemHandler.class, (Capability.IStorage)CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage(), FSItemHandler::new);
	}
	
	public static interface IFSItemHandler extends IItemHandler {}
	
	private static class FSItemHandler extends ItemStackHandler implements IFSItemHandler {
		public FSItemHandler() {
			super(FSHANDLER_SZ);
		}
		
		@Override
		protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
			if (Config.isFruit(stack)) {
				return super.getStackLimit(slot, stack);
			}
			return 0;
		}
	}
	
	private static class FSItemHandlerCapProvider implements ICapabilitySerializable<NBTTagCompound> {
		@CapabilityInject(IFSItemHandler.class)
		private static Capability<IFSItemHandler> FS_ITEM_CAP = null;
		
		private final FSItemHandler cap;
		
		public FSItemHandlerCapProvider() {
			cap = new FSItemHandler();
		}
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			//System.out.println("Athens, this is " + FS_ITEM_CAP);
			return capability == FS_ITEM_CAP;
		}
		
		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if (capability == FS_ITEM_CAP) {
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
			cap.deserializeNBT(nbt);
		}
	}
	
	@SubscribeEvent
	public void addFruitSaladCapability(AttachCapabilitiesEvent<ItemStack> event) {
		if (event.getObject().getItem() instanceof ToolCore) {
			event.addCapability(FRUITSALAD_CAPLOCATION, new FSItemHandlerCapProvider());
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void fruitSaladGUI(InputEvent.KeyInputEvent event) {
		if (PlusTiC.proxy.isControlPressed("fruit_salad")
				&& TinkerUtil.hasTrait(TagUtil.getTagSafe(Minecraft.getMinecraft().player.getHeldItemMainhand()), identifier)) {
			PacketHandler.INSTANCE.sendToServer(new PacketOpenFSGui());
		}
	}
}
