package landmaster.plustic.net;

import java.util.UUID;

import io.netty.buffer.*;
import landmaster.plustic.traits.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class PacketStopMOTS implements IMessage {
	@CapabilityInject(MusicOfTheSpheres.IMOTSItemHandler.class)
	private static Capability<MusicOfTheSpheres.IMOTSItemHandler> MOTS_ITEM_CAP = null;
	
	private EntityPlayer player;
	
	public PacketStopMOTS() {}
	public PacketStopMOTS(EntityPlayer player) {
		this.player = player;
	}
	
	public static IMessage onMessage(PacketStopMOTS packet, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			if (packet.player != null && packet.player.getHeldItemMainhand().hasCapability(MOTS_ITEM_CAP, null)) {
				MusicOfTheSpheres.IMOTSItemHandler cap = packet.player.getHeldItemMainhand()
						.getCapability(MOTS_ITEM_CAP, null);
				cap.stop(packet.player);
			}
		});
		return null;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.player = Minecraft.getMinecraft().world.getPlayerEntityByUUID(new UUID(buf.readLong(), buf.readLong()));
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(this.player.getPersistentID().getMostSignificantBits());
		buf.writeLong(this.player.getPersistentID().getLeastSignificantBits());
	}
	
}
