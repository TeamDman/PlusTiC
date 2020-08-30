package landmaster.plustic.net;

import io.netty.buffer.ByteBuf;
import landmaster.plustic.api.Sounds;
import landmaster.plustic.traits.Portly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class PacketReleaseEntity implements IMessage {
    public static IMessage onMessage(PacketReleaseEntity message, MessageContext ctx) {
        IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.getEntityWorld();
        mainThread.addScheduledTask(() -> {
            EntityPlayer ep = ctx.getServerHandler().player;
            Vec3d eye = ep.getPositionVector().add(new Vec3d(0, ep.getEyeHeight(), 0));
            Vec3d look = ep.getLookVec().scale(5);
            RayTraceResult rtr = ep.getEntityWorld().rayTraceBlocks(eye, eye.add(look));
            NBTTagCompound nbt = TagUtil.getTagSafe(ep.getHeldItemMainhand());
            if (ep.getEntityWorld().isRemote
                    || rtr == null || rtr.sideHit == null
                    || ep.getHeldItemMainhand() == null
                    || !TinkerUtil.hasTrait(nbt, Portly.portly.getIdentifier())
                    || !nbt.hasKey("portlyGentleman", 10)) {
                return;
            }

            Entity ent = EntityList.createEntityFromNBT(nbt.getCompoundTag("portlyGentleman"), ep.getEntityWorld());
            if (ent == null) return;

            int offsetX = rtr.sideHit.getFrontOffsetX();
            int offsetY = rtr.sideHit == EnumFacing.DOWN ? -1 : 0;
            int offsetZ = rtr.sideHit.getFrontOffsetZ();
            AxisAlignedBB bb = ent.getEntityBoundingBox();
            ent.setLocationAndAngles(
                    rtr.hitVec.x + (bb.maxX - bb.minX) * 0.5 * offsetX,
                    rtr.hitVec.y + (bb.maxY - bb.minY) * 0.5 * offsetY,
                    rtr.hitVec.z + (bb.maxZ - bb.minZ) * 0.5 * offsetZ,
                    ep.getRNG().nextFloat() * 360, 0);

            if (!ep.getEntityWorld().spawnEntity(ent)) return;
            if (ent instanceof EntityLiving) ((EntityLiving) ent).playLivingSound();
            String id = nbt.getCompoundTag("portlyGentleman").getString("id");
            nbt.removeTag("portlyGentleman");
            ep.getHeldItemMainhand().setTagCompound(nbt);
            Sounds.playSoundToAll(ep, SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
            ep.swingArm(EnumHand.MAIN_HAND);
            ep.sendMessage(new TextComponentTranslation("msg.plustic.portlymodifier.unset", id));
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
