package landmaster.plustic.net;

import java.util.*;

import io.netty.buffer.*;
import landmaster.plustic.tools.ToolLaserGun;
import net.minecraft.client.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class PacketLaserGunZapBlock implements IMessage {
	private UUID playerUUID;
	private Vec3d hitVec;
	
	public static IMessage onMessage(PacketLaserGunZapBlock packet, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			ToolLaserGun.proxy.addToZapBlockRendering(Minecraft.getMinecraft().world.getPlayerEntityByUUID(packet.playerUUID), packet.hitVec);
		});
		return null;
	}
	
	public PacketLaserGunZapBlock() {
		this(Vec3d.ZERO, null);
	}
	public PacketLaserGunZapBlock(Vec3d hitVec, UUID playerUUID) {
		this.hitVec = hitVec;
		this.playerUUID = playerUUID;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		hitVec = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
		playerUUID = new UUID(buf.readLong(), buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(hitVec.x).writeDouble(hitVec.y).writeDouble(hitVec.z);
		buf.writeLong(playerUUID.getMostSignificantBits()).writeLong(playerUUID.getLeastSignificantBits());
	}
	
}
