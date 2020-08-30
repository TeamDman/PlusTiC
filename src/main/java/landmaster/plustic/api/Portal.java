package landmaster.plustic.api;

import landmaster.plustic.ModInfo;
import landmaster.plustic.PlusTiC;
import landmaster.plustic.net.PacketHandler;
import landmaster.plustic.net.PacketSetPortal;
import landmaster.plustic.util.Coord4D;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.HashSet;
import java.util.Set;

public class Portal {
    public static final ResourceLocation PORTALARMOR_CAPLOCATION = new ResourceLocation(ModInfo.MODID, "portalarmor_cap");
    public static final String PORTAL_NBT = "nickoftime";
    private static final Set<String> portalable = new HashSet<>();
    @CapabilityInject(IPortalArmor.class)
    private static Capability<IPortalArmor> PORTAL_ARMOR = null;

    static {
        MinecraftForge.EVENT_BUS.register(Portal.class);
        CapabilityManager.INSTANCE.register(IPortalArmor.class, new Capability.IStorage<IPortalArmor>() {
            @Override
            public NBTBase writeNBT(Capability<IPortalArmor> capability, IPortalArmor instance, EnumFacing side) {
                return instance.location().toNBT(new NBTTagCompound());
            }

            @Override
            public void readNBT(Capability<IPortalArmor> capability, IPortalArmor instance, EnumFacing side,
                                NBTBase nbt) {
                instance.location(Coord4D.fromNBT((NBTTagCompound) nbt));
            }
        }, PortalArmor::new);
    }

    @SubscribeEvent
    public static void addPortalArmorCapability(AttachCapabilitiesEvent<Entity> event) {
        if (Loader.isModLoaded("conarm") && event.getObject() instanceof EntityPlayer) {
            event.addCapability(PORTALARMOR_CAPLOCATION, new PortalArmorProvider());
        }
    }

    public static void addPortalable(String identifier) {
        portalable.add(identifier);
    }

    public static boolean canUse(NBTTagCompound nbt) {
        for (String identifier : portalable) {
            if (TinkerUtil.hasTrait(nbt, identifier)) {
                return true;
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void copyOnDeath(PlayerEvent.Clone event) {
        IPortalArmor oldCap = event.getOriginal().getCapability(PORTAL_ARMOR, null);
        IPortalArmor newCap = event.getEntityPlayer().getCapability(PORTAL_ARMOR, null);
        if (oldCap != null && newCap != null) {
            newCap.location(oldCap.location());
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void testSetPortal(InputEvent.KeyInputEvent event) {
        if (PlusTiC.proxy.isControlPressed("set_portal")) {
            PacketHandler.INSTANCE.sendToServer(new PacketSetPortal());
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void tooltip(ItemTooltipEvent event) {
        NBTTagCompound nbt0 = TagUtil.getTagSafe(event.getItemStack());
        if (event.isCanceled() || event.getItemStack() == null || !canUse(nbt0)) return;
        if (nbt0.hasKey(Portal.PORTAL_NBT, 10)) {
            NBTTagCompound nbt = nbt0.getCompoundTag(Portal.PORTAL_NBT);
            event.getToolTip().add(I18n.format("tooltip.plustic.portal.info",
                    nbt.getInteger("x"),
                    nbt.getInteger("y"),
                    nbt.getInteger("z"),
                    nbt.getInteger("dim")));
        }
    }

    public interface IPortalArmor {
        Coord4D location();

        void location(Coord4D loc);
    }

    private static class PortalArmor implements IPortalArmor {
        private Coord4D loc = Coord4D.NIHIL;

        @Override
        public Coord4D location() {
            return loc;
        }

        @Override
        public void location(Coord4D loc) {
            this.loc = loc;
        }
    }

    private static class PortalArmorProvider implements ICapabilitySerializable<NBTTagCompound> {
        @CapabilityInject(IPortalArmor.class)
        private static Capability<IPortalArmor> PORTAL_ARMOR = null;

        private final PortalArmor cap;

        public PortalArmorProvider() {
            this.cap = new PortalArmor();
        }

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
            return capability == PORTAL_ARMOR;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
            if (capability == PORTAL_ARMOR) {
                return (T) cap;
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return (NBTTagCompound) PORTAL_ARMOR.writeNBT(cap, null);
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            PORTAL_ARMOR.readNBT(cap, null, nbt);
        }
    }
}
