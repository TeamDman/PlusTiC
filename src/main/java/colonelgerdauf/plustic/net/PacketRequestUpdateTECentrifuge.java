package landmaster.plustic.net;

import io.netty.buffer.*;
import landmaster.plustic.tile.*;
import landmaster.plustic.util.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.energy.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class PacketRequestUpdateTECentrifuge implements IMessage {
	private Coord4D coord;
	
	public PacketRequestUpdateTECentrifuge() {}
	public PacketRequestUpdateTECentrifuge(Coord4D coord) { this.coord = coord; }
	
	
	public static IMessage onMessage(PacketRequestUpdateTECentrifuge packet, MessageContext ctx) {
		TileEntity te = packet.coord.TE();
		if (te instanceof TECentrifugeCore) {
			PacketHandler.INSTANCE.sendTo(new PacketUpdateTECentrifugeCoreEnergy(packet.coord, te.getCapability(CapabilityEnergy.ENERGY, null).getEnergyStored()), ctx.getServerHandler().player);
		}
		if (te instanceof TECentrifuge) {
			PacketHandler.INSTANCE.sendTo(new PacketUpdateTECentrifugeLiquid(packet.coord, ((TECentrifuge) te).getTank().getFluid()), ctx.getServerHandler().player);
		}
		return null;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		coord = Coord4D.fromByteBuf(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		coord.toByteBuf(buf);
	}
	
}
