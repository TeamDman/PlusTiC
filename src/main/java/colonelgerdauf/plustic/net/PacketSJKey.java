package landmaster.plustic.net;

import io.netty.buffer.*;
import landmaster.plustic.modifiers.armor.*;
import landmaster.plustic.util.Utils;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class PacketSJKey implements IMessage {
	public PacketSJKey() {}
	public PacketSJKey(JetpackPancakeHippos.JetpackSettings setting) { this.setting = setting; }
	
	private JetpackPancakeHippos.JetpackSettings setting;
	
	public static IMessage onMessage(PacketSJKey packet, MessageContext ctx) {
		IThreadListener mainThread = (WorldServer)ctx.getServerHandler().player.getEntityWorld();
		mainThread.addScheduledTask(() -> {
			ItemStack stack = ctx.getServerHandler().player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
			Utils.getModifiers(stack).stream()
			.filter(trait -> trait instanceof JetpackPancakeHippos)
			.findAny().ifPresent(trait -> {
				packet.setting.setOff(stack, !packet.setting.isOff(stack));
			});
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
