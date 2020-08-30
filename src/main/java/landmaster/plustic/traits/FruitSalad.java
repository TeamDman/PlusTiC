package landmaster.plustic.traits;

import landmaster.plustic.ModInfo;
import landmaster.plustic.PlusTiC;
import landmaster.plustic.config.Config;
import landmaster.plustic.net.PacketHandler;
import landmaster.plustic.net.PacketOpenFSGui;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import javax.annotation.Nonnull;

/**
 * First step, meme this trait to death…
 *
 * @author Landmaster
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class FruitSalad extends AbstractTrait {
    public static final FruitSalad fruitsalad = new FruitSalad();

    //public static final String FRUITSALAD_TAG = "PlusTiC_FruitSalad";

    public static final int FSHANDLER_SZ = 5;

    public static final ResourceLocation FRUITSALAD_CAPLOCATION = new ResourceLocation(ModInfo.MODID, "fruitsalad_cap");

    static {
        // Basically, the base item handler capability's IStorage and this IStorage are one and the same.
        CapabilityManager.INSTANCE.register(IFSItemHandler.class, (Capability.IStorage) CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage(), FSItemHandler::new);
    }

    public FruitSalad() {
        super("fruitsalad", 0x77007f);
        MinecraftForge.EVENT_BUS.register(this);
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

    public interface IFSItemHandler extends IItemHandler {
    }

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
        public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
            return capability == FS_ITEM_CAP;
        }

        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
            if (capability == FS_ITEM_CAP) {
                return (T) cap;
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
}
