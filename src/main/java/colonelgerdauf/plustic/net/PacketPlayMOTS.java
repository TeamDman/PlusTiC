package landmaster.plustic.net;

import java.util.*;

import io.netty.buffer.*;
import landmaster.plustic.traits.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.common.registry.*;

public class PacketPlayMOTS implements IMessage {
	@CapabilityInject(MusicOfTheSpheres.IMOTSItemHandler.class)
	private static Capability<MusicOfTheSpheres.IMOTSItemHandler> MOTS_ITEM_CAP = null;
	
	private EntityPlayer player;
	private ResourceLocation soundLoc;
	
	public PacketPlayMOTS() {}
	public PacketPlayMOTS(EntityPlayer player, ResourceLocation soundLoc) {
		this.player = player;
		this.soundLoc = soundLoc;
	}
	
	public static IMessage onMessage(PacketPlayMOTS packet, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			if (packet.player != null && packet.player.getHeldItemMainhand().hasCapability(MOTS_ITEM_CAP, null)) {
				MusicOfTheSpheres.IMOTSItemHandler cap = packet.player.getHeldItemMainhand()
						.getCapability(MOTS_ITEM_CAP, null);
				cap.play(packet.player, ForgeRegistries.SOUND_EVENTS.getValue(packet.soundLoc));
			}
		});
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.player = Minecraft.getMinecraft().world.getPlayerEntityByUUID(new UUID(buf.readLong(), buf.readLong()));
		this.soundLoc = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(this.player.getPersistentID().getMostSignificantBits());
		buf.writeLong(this.player.getPersistentID().getLeastSignificantBits());
		ByteBufUtils.writeUTF8String(buf, this.soundLoc.toString());
	}
	
}
