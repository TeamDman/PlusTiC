package landmaster.plustic.net;

import io.netty.buffer.ByteBuf;
import landmaster.plustic.tile.TECentrifuge;
import landmaster.plustic.tile.TECentrifugeCore;
import landmaster.plustic.util.Coord4D;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateTECentrifuge implements IMessage {
    private Coord4D coord;

    public PacketRequestUpdateTECentrifuge() {
    }

    public PacketRequestUpdateTECentrifuge(Coord4D coord) {
        this.coord = coord;
    }


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
