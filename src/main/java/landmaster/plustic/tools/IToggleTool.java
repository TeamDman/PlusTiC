package landmaster.plustic.tools;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.net.PacketHandler;
import landmaster.plustic.net.PacketUpdateToolModeServer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = ModInfo.MODID, value = Side.CLIENT)
public interface IToggleTool<T extends Enum<T>> {
    public static Enum<?> rotateMode(ItemStack is) {
        if (!(is.getItem() instanceof IToggleTool)) {
            return null;
        }
        final Enum<?>[] vals = ((IToggleTool<?>) is.getItem()).clazz().getEnumConstants();
        final String tag = ((IToggleTool<?>) is.getItem()).getTag();
        int newIdx = (is.getTagCompound().getInteger(tag) + 1) % vals.length;
        is.getTagCompound().setInteger(tag, newIdx);
        return vals[newIdx];
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T rotateMode(ItemStack is, Class<T> clazz) {
        if (!(is.getItem() instanceof IToggleTool && ((IToggleTool<?>) is.getItem()).clazz() == clazz)) {
            return null;
        }
        return (T) rotateMode(is);
    }

    public static <T extends Enum<T>> T getMode(ItemStack is, Class<T> clazz) {
        if (!(is.getItem() instanceof IToggleTool && ((IToggleTool<?>) is.getItem()).clazz() == clazz)) {
            return null;
        }
        return clazz.getEnumConstants()[is.getTagCompound().getInteger(((IToggleTool<?>) is.getItem()).getTag())];
    }

    public static Enum<?> rotateHeldItemMode(EntityLivingBase elb) {
        for (EnumHand hand : EnumHand.values()) {
            final Enum<?> enm = rotateMode(elb.getHeldItem(hand));
            if (enm != null) return enm;
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onInput(InputEvent.KeyInputEvent event) {
        if (PlusTiC.proxy.isControlPressed("toggle_tool") && Arrays.stream(EnumHand.values())
                .map(Minecraft.getMinecraft().player::getHeldItem)
                .anyMatch(is -> is.getItem() instanceof IToggleTool)) {
            PacketHandler.INSTANCE.sendToServer(new PacketUpdateToolModeServer());
        }
    }

    Class<T> clazz();

    String getTag();
}
