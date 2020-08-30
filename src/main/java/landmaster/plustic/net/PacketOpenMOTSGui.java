package landmaster.plustic.net;

import io.netty.buffer.ByteBuf;
import landmaster.plustic.PlusTiC;
import landmaster.plustic.gui.handler.PTGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenMOTSGui implements IMessage {

    public static IMessage onMessage(PacketOpenMOTSGui packet, MessageContext ctx) {
        IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.getEntityWorld();
        mainThread.addScheduledTask(() -> {
            EntityPlayer ep = ctx.getServerHandler().player;
            BlockPos bp = ep.getPosition();
            ep.openGui(PlusTiC.INSTANCE, PTGuiHandler.MOTS, ep.world,
                    bp.getX(), bp.getY(), bp.getZ());
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
