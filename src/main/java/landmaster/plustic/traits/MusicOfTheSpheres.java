package landmaster.plustic.traits;

import com.google.common.collect.MapMaker;
import landmaster.plustic.ModInfo;
import landmaster.plustic.PlusTiC;
import landmaster.plustic.net.PacketHandler;
import landmaster.plustic.net.PacketOpenMOTSGui;
import landmaster.plustic.net.PacketPlayMOTS;
import landmaster.plustic.net.PacketStopMOTS;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
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
import java.util.Map;
import java.util.UUID;

@SuppressWarnings({"unchecked", "rawtypes"})
public class MusicOfTheSpheres extends AbstractTrait {
    public static final MusicOfTheSpheres musicofthespheres = new MusicOfTheSpheres();

    public static final ResourceLocation MOTS_CAPLOCATION = new ResourceLocation(ModInfo.MODID, "musicofthespheres_cap");
    public static final @Deprecated
    ResourceLocation MOTS_OLDCAPLOCATION = new ResourceLocation(ModInfo.MODID, "");

    static {
        // see analogous comment at FruitSalad.java
        CapabilityManager.INSTANCE.register(IMOTSItemHandler.class, (Capability.IStorage) CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage(), MOTSItemHandler::new);
    }

    public MusicOfTheSpheres() {
        super("musicofthespheres", 0xffffff);
        MinecraftForge.EVENT_BUS.register(this);
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
        if (PlusTiC.proxy.isControlPressed("mots") && TinkerUtil.hasTrait(TagUtil.getTagSafe(Minecraft.getMinecraft().player.getHeldItemMainhand()), identifier)) {
            PacketHandler.INSTANCE.sendToServer(new PacketOpenMOTSGui());
        }
    }

    public interface IMOTSItemHandler extends IItemHandler {
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
            if (!nbt.hasNoTags()) {
                cap.deserializeNBT(nbt);
            }
        }
    }
}
