package colonelgerdauf.plustic.net;

import io.netty.buffer.*;
import colonelgerdauf.plustic.tile.*;
import colonelgerdauf.plustic.util.*;
import net.minecraft.client.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class PacketUpdateTECentrifugeCoreEnergy implements IMessage {
	private Coord4D pos;
	private int energy;
	
	public PacketUpdateTECentrifugeCoreEnergy() {}
	public PacketUpdateTECentrifugeCoreEnergy(Coord4D pos, int energy) {
		this.pos = pos;
		this.energy = energy;
	}
	
	public static IMessage onMessage(PacketUpdateTECentrifugeCoreEnergy packet, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			if (Minecraft.getMinecraft().world.provider.getDimension() != packet.pos.dimensionId) return;
			TileEntity te = Minecraft.getMinecraft().world.getTileEntity(packet.pos.pos());
			if (te instanceof TECentrifugeCore) {
				((TECentrifugeCore)te).setEnergy(packet.energy);
			}
		});
		return null;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = Coord4D.fromByteBuf(buf);
		energy = buf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		pos.toByteBuf(buf);
		buf.writeInt(energy);
	}
	
}
