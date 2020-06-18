package landmaster.plustic.net;

import java.util.*;

import io.netty.buffer.*;
import landmaster.plustic.api.*;
import net.minecraft.client.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class PacketUpdateToggleGui implements IMessage {
	@CapabilityInject(Toggle.IToggleArmor.class)
	private static Capability<Toggle.IToggleArmor> TOGGLE_ARMOR = null;
	
	private String identifier;
	private boolean value;
	
	public PacketUpdateToggleGui() {}
	public PacketUpdateToggleGui(String identifier, boolean value) {
		this.identifier = identifier;
		this.value = value;
	}
	
	public static IMessage onMessage(PacketUpdateToggleGui message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			if (Minecraft.getMinecraft().currentScreen instanceof Toggle.Gui) {
				((Toggle.Gui)Minecraft.getMinecraft().currentScreen).update(message.identifier, message.value);
			}
			if (Minecraft.getMinecraft().player.hasCapability(TOGGLE_ARMOR, null)) {
				Toggle.IToggleArmor cap = Minecraft.getMinecraft().player.getCapability(TOGGLE_ARMOR, null);
				Set<String> disabled = cap.getDisabled();
				String rawIdentifier = Toggle.rawIdentifier(message.identifier);
				if (message.value) {
					disabled.remove(rawIdentifier);
				} else {
					disabled.add(rawIdentifier);
				}
			}
		});
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		identifier = ByteBufUtils.readUTF8String(buf);
		value = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, identifier);
		buf.writeBoolean(value);
	}
}
