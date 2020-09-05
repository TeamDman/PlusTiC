package landmaster.plustic.net;

import io.netty.buffer.ByteBuf;
import landmaster.plustic.api.Portal;
import landmaster.plustic.api.Sounds;
import landmaster.plustic.traits.BrownMagic;
import landmaster.plustic.util.Coord4D;
import landmaster.plustic.util.Utils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class PacketBrownAbracadabra implements IMessage {

    public static IMessage onMessage(PacketBrownAbracadabra message, MessageContext ctx) {
        IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.getEntityWorld();
        mainThread.addScheduledTask(() -> {
            EntityPlayerMP ep = ctx.getServerHandler().player;
            if (ep.getEntityWorld().isRemote)
                return;
            NBTTagCompound nbt = TagUtil.getTagSafe(ep.getHeldItemMainhand());
            Coord4D coord = Coord4D.fromNBT(nbt.getCompoundTag(Portal.PORTAL_NBT));
            if (TinkerUtil.hasTrait(nbt, BrownMagic.brownmagic.identifier)
                    && Utils.canTeleportTo(ep, coord)) {
                Sounds.playSoundToAll(ep, SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
                ep.fallDistance = 0;
                Utils.teleportPlayerTo(ep, coord);
                ep.sendMessage(new TextComponentTranslation("msg.plustic.brownmagic.use"));
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
