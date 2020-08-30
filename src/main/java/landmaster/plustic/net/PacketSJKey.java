package landmaster.plustic.net;

import io.netty.buffer.ByteBuf;
import landmaster.plustic.modifiers.armor.JetpackPancakeHippos;
import landmaster.plustic.util.Utils;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSJKey implements IMessage {
    private JetpackPancakeHippos.JetpackSettings setting;

    public PacketSJKey() {
    }

    public PacketSJKey(JetpackPancakeHippos.JetpackSettings setting) {
        this.setting = setting;
    }

    public static IMessage onMessage(PacketSJKey packet, MessageContext ctx) {
        IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.getEntityWorld();
        mainThread.addScheduledTask(() -> {
            ItemStack stack = ctx.getServerHandler().player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            Utils.getModifiers(stack).stream()
                    .filter(trait -> trait instanceof JetpackPancakeHippos)
                    .findAny().ifPresent(trait -> packet.setting.setOff(stack, !packet.setting.isOff(stack)));
        });
        return null;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        setting = JetpackPancakeHippos.JetpackSettings.values()[buf.readInt()];
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(setting.ordinal());
    }
}
