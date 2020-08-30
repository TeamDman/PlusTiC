package landmaster.plustic.net;

import landmaster.plustic.ModInfo;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MODID);

    public static void init() {
        INSTANCE.registerMessage(PacketReleaseEntity::onMessage, PacketReleaseEntity.class, 0, Side.SERVER);
        INSTANCE.registerMessage(PacketHandleToggleGui::onMessage, PacketHandleToggleGui.class, 1, Side.SERVER);
        INSTANCE.registerMessage(PacketUpdateToggleGui::onMessage, PacketUpdateToggleGui.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(PacketSetPortal::onMessage, PacketSetPortal.class, 3, Side.SERVER);
        INSTANCE.registerMessage(PacketBrownAbracadabra::onMessage, PacketBrownAbracadabra.class, 4, Side.SERVER);
        INSTANCE.registerMessage(PacketUpdateToolModeServer::onMessage, PacketUpdateToolModeServer.class, 5, Side.SERVER);
        INSTANCE.registerMessage(PacketLaserGunZapBlock::onMessage, PacketLaserGunZapBlock.class, 6, Side.CLIENT);
        INSTANCE.registerMessage(PacketOpenFSGui::onMessage, PacketOpenFSGui.class, 7, Side.SERVER);
        INSTANCE.registerMessage(PacketOpenMOTSGui::onMessage, PacketOpenMOTSGui.class, 8, Side.SERVER);
        INSTANCE.registerMessage(PacketPlayMOTS::onMessage, PacketPlayMOTS.class, 9, Side.CLIENT);
        INSTANCE.registerMessage(PacketStopMOTS::onMessage, PacketStopMOTS.class, 10, Side.CLIENT);
        INSTANCE.registerMessage(PacketSJKey::onMessage, PacketSJKey.class, 11, Side.SERVER);
        INSTANCE.registerMessage(PacketRequestToggleGui::onMessage, PacketRequestToggleGui.class, 12, Side.SERVER);
        INSTANCE.registerMessage(PacketOpenToggleGui::onMessage, PacketOpenToggleGui.class, 13, Side.CLIENT);
        INSTANCE.registerMessage(PacketSJSyncParticles::onMessage, PacketSJSyncParticles.class, 14, Side.CLIENT);
        INSTANCE.registerMessage(PacketUpdateTECentrifugeLiquid::onMessage, PacketUpdateTECentrifugeLiquid.class, 15, Side.CLIENT);
        INSTANCE.registerMessage(PacketUpdateTECentrifugeCoreEnergy::onMessage, PacketUpdateTECentrifugeCoreEnergy.class, 16, Side.CLIENT);
        INSTANCE.registerMessage(PacketRequestUpdateTECentrifuge::onMessage, PacketRequestUpdateTECentrifuge.class, 17, Side.SERVER);

        // TODO: add more messages when needed.
    }
}
