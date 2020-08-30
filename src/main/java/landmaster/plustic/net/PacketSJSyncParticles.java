package landmaster.plustic.net;

import io.netty.buffer.ByteBuf;
import landmaster.plustic.modifiers.armor.JetpackPancakeHippos;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tonius.simplyjetpacks.setup.ParticleType;

public class PacketSJSyncParticles implements IMessage {
    private int entityId;
    private int particleId;

    public PacketSJSyncParticles() {
    }

    public PacketSJSyncParticles(int entityId, int particleId) {
        this.entityId = entityId;
        this.particleId = particleId;
    }

    public static IMessage onMessage(PacketSJSyncParticles packet, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            Entity entity = Minecraft.getMinecraft().world.getEntityByID(packet.entityId);
            if (entity instanceof EntityLivingBase && entity != Minecraft.getMinecraft().player) {
                JetpackPancakeHippos.processJetpackUpdate(packet.entityId, packet.particleId >= 0 ? ParticleType.values()[packet.particleId] : null);
            }
        });
        return null;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.particleId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeInt(this.particleId);
    }
}
