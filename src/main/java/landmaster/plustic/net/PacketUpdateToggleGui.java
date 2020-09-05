package landmaster.plustic.net;

import io.netty.buffer.ByteBuf;
import landmaster.plustic.api.Toggle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Set;

public class PacketUpdateToggleGui implements IMessage {
    @CapabilityInject(Toggle.IToggleArmor.class)
    private static Capability<Toggle.IToggleArmor> TOGGLE_ARMOR = null;

    private String identifier;
    private boolean value;

    public PacketUpdateToggleGui() {
    }

    public PacketUpdateToggleGui(String identifier, boolean value) {
        this.identifier = identifier;
        this.value = value;
    }

    public static IMessage onMessage(PacketUpdateToggleGui message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            if (Minecraft.getMinecraft().currentScreen instanceof Toggle.Gui) {
                ((Toggle.Gui) Minecraft.getMinecraft().currentScreen).update(message.identifier, message.value);
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
