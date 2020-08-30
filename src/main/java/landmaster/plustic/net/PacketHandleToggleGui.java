package landmaster.plustic.net;

import io.netty.buffer.ByteBuf;
import landmaster.plustic.api.Toggle;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import slimeknights.tconstruct.library.utils.TagUtil;

import java.util.Set;

public class PacketHandleToggleGui implements IMessage {
    @CapabilityInject(Toggle.IToggleArmor.class)
    private static Capability<Toggle.IToggleArmor> TOGGLE_ARMOR = null;

    private String identifier;

    public PacketHandleToggleGui() {
    }

    public PacketHandleToggleGui(String identifier) {
        this.identifier = identifier;
    }

    public static IMessage onMessage(PacketHandleToggleGui message, MessageContext ctx) {
        IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.getEntityWorld();
        mainThread.addScheduledTask(() -> {
            EntityPlayerMP ep = ctx.getServerHandler().player;
            if (ep.getEntityWorld().isRemote) {
                return;
            }
            boolean newState;
            if (message.identifier.startsWith(Toggle.ARMOR_FLAG) && ep.hasCapability(TOGGLE_ARMOR, null)) {
                String rawIdentifier = Toggle.rawIdentifier(message.identifier);
                Toggle.IToggleArmor cap = ep.getCapability(TOGGLE_ARMOR, null);
                Set<String> disabled = cap.getDisabled();
                newState = disabled.contains(rawIdentifier);
                if (newState) {
                    disabled.remove(rawIdentifier);
                } else {
                    disabled.add(rawIdentifier);
                }
            } else {
                NBTTagCompound nbt = TagUtil.getTagSafe(ep.getHeldItemMainhand());
                newState = !Toggle.getToggleState(nbt, message.identifier);
                Toggle.setToggleState(nbt, message.identifier, newState);
                ep.getHeldItemMainhand().setTagCompound(nbt);
                ep.inventory.markDirty();
            }
            PacketHandler.INSTANCE.sendTo(new PacketUpdateToggleGui(message.identifier, newState), ep);
        });
        return null;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        identifier = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, identifier);
    }
}
