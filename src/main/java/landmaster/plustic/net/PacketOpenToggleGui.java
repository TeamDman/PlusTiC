package landmaster.plustic.net;

import io.netty.buffer.ByteBuf;
import landmaster.plustic.api.Toggle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.Collection;

public class PacketOpenToggleGui implements IMessage {
    @SidedProxy(clientSide = "landmaster.plustic.net.PacketOpenToggleGui$ProxyClient", serverSide = "landmaster.plustic.net.PacketOpenToggleGui$Proxy")
    public static Proxy proxy;
    private Collection<String> abilities;

    public PacketOpenToggleGui() {
    }

    public PacketOpenToggleGui(Collection<String> abilities) {
        this.abilities = abilities;
    }

    public static IMessage onMessage(PacketOpenToggleGui message, MessageContext ctx) {
        proxy.handle(message, ctx);
        return null;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int sz = buf.readInt();
        abilities = new ArrayList<>(sz);
        for (int i = 0; i < sz; ++i) abilities.add(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(abilities.size());
        for (String ability : abilities) ByteBufUtils.writeUTF8String(buf, ability);
    }

    public static class Proxy {
        public void handle(PacketOpenToggleGui message, MessageContext ctx) {
        }
    }

    public static class ProxyClient extends Proxy {
        @Override
        public void handle(PacketOpenToggleGui message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Minecraft.getMinecraft().displayGuiScreen(new Toggle.Gui(Minecraft.getMinecraft().player, message.abilities));
            });
        }
    }
}
