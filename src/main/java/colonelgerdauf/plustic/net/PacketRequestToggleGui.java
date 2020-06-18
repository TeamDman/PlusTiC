package landmaster.plustic.net;

import java.util.*;
import java.util.stream.*;

import io.netty.buffer.*;
import landmaster.plustic.api.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class PacketRequestToggleGui implements IMessage {
	public static IMessage onMessage(PacketRequestToggleGui message, MessageContext ctx) {
		IThreadListener mainThread = (WorldServer)ctx.getServerHandler().player.getEntityWorld();
		mainThread.addScheduledTask(() -> {
			List<String> toggleableArmor = Toggle.getToggleableArmor(ctx.getServerHandler().player).collect(Collectors.toList());
			if (Toggle.canToggle(ctx.getServerHandler().player.getHeldItemMainhand()) || !toggleableArmor.isEmpty()) {
				PacketHandler.INSTANCE.sendTo(new PacketOpenToggleGui(toggleableArmor), ctx.getServerHandler().player);
			}
		});
		return null;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {}
	
	@Override
	public void toBytes(ByteBuf buf) {}
	
}
