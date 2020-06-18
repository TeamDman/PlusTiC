package landmaster.plustic.tools;

import java.util.*;

import landmaster.plustic.*;
import landmaster.plustic.api.*;
import landmaster.plustic.net.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.relauncher.*;

@Mod.EventBusSubscriber(modid = ModInfo.MODID, value = Side.CLIENT)
public interface IToggleTool<T extends Enum<T>> {
	Class<T> clazz();
	
	String getTag();
	
	public static Enum<?> rotateMode(ItemStack is) {
		if (!(is.getItem() instanceof IToggleTool)) {
			return null;
		}
		final Enum<?>[] vals = ((IToggleTool<?>)is.getItem()).clazz().getEnumConstants();
		final String tag = ((IToggleTool<?>)is.getItem()).getTag();
		int newIdx = (is.getTagCompound().getInteger(tag)+1) % vals.length;
		is.getTagCompound().setInteger(tag, newIdx);
		return vals[newIdx];
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>> T rotateMode(ItemStack is, Class<T> clazz) {
		if (!(is.getItem() instanceof IToggleTool && ((IToggleTool<?>)is.getItem()).clazz() == clazz)) {
			return null;
		}
		return (T)rotateMode(is);
	}
	
	public static <T extends Enum<T>> T getMode(ItemStack is, Class<T> clazz) {
		if (!(is.getItem() instanceof IToggleTool && ((IToggleTool<?>)is.getItem()).clazz() == clazz)) {
			return null;
		}
		return clazz.getEnumConstants()[is.getTagCompound().getInteger(((IToggleTool<?>)is.getItem()).getTag())];
	}
	
	public static Enum<?> rotateHeldItemMode(EntityLivingBase elb) {
		for (EnumHand hand: EnumHand.values()) {
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
}
