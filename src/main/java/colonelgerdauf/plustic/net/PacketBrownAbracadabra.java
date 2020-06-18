package landmaster.plustic.net;

import io.netty.buffer.*;
import landmaster.plustic.api.*;
import landmaster.plustic.traits.*;
import landmaster.plustic.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import slimeknights.tconstruct.library.utils.*;

public class PacketBrownAbracadabra implements IMessage {
	
	public static IMessage onMessage(PacketBrownAbracadabra message, MessageContext ctx) {
		IThreadListener mainThread = (WorldServer)ctx.getServerHandler().player.getEntityWorld();
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
