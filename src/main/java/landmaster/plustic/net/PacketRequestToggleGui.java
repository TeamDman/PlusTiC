package landmaster.plustic.net;

import io.netty.buffer.ByteBuf;
import landmaster.plustic.api.Toggle;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.List;
import java.util.stream.Collectors;

public class PacketRequestToggleGui implements IMessage {
    public static IMessage onMessage(PacketRequestToggleGui message, MessageContext ctx) {
        IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.getEntityWorld();
        mainThread.addScheduledTask(() -> {
            List<String> toggleableArmor = Toggle.getToggleableArmor(ctx.getServerHandler().player).collect(Collectors.toList());
            if (Toggle.canToggle(ctx.getServerHandler().player.getHeldItemMainhand()) || !toggleableArmor.isEmpty()) {
                PacketHandler.INSTANCE.sendTo(new PacketOpenToggleGui(toggleableArmor), ctx.getServerHandler().player);
            }
        });
        return null;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }
}
