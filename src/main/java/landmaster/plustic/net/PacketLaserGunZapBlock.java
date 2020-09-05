package landmaster.plustic.net;

import io.netty.buffer.ByteBuf;
import landmaster.plustic.tools.ToolLaserGun;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PacketLaserGunZapBlock implements IMessage {
    private UUID playerUUID;
    private Vec3d hitVec;

    public PacketLaserGunZapBlock() {
        this(Vec3d.ZERO, null);
    }

    public PacketLaserGunZapBlock(Vec3d hitVec, UUID playerUUID) {
        this.hitVec = hitVec;
        this.playerUUID = playerUUID;
    }

    public static IMessage onMessage(PacketLaserGunZapBlock packet, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            ToolLaserGun.proxy.addToZapBlockRendering(Minecraft.getMinecraft().world.getPlayerEntityByUUID(packet.playerUUID), packet.hitVec);
        });
        return null;
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
