package landmaster.plustic.traits;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.api.Sounds;
import landmaster.plustic.net.PacketHandler;
import landmaster.plustic.net.PacketReleaseEntity;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Portly extends AbstractTrait {
    public static final Portly portly = new Portly();

    public Portly() {
        super("portly", 0x00443B);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (isSelected && FMLCommonHandler.instance().getSide().isClient()) {
            if (PlusTiC.proxy.isControlPressed("release_entity")) {
                PacketHandler.INSTANCE.sendToServer(new PacketReleaseEntity());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void captureEntity(PlayerInteractEvent.EntityInteract event) {
        NBTTagCompound nbt = TagUtil.getTagSafe(event.getItemStack());
        if (event.getWorld().isRemote
                || !event.getEntityPlayer().isSneaking()
                || event.getItemStack() == null
                || !TinkerUtil.hasTrait(nbt, getIdentifier())
                || ToolHelper.getCurrentDurability(event.getItemStack()) < durabilityCost(event.getTarget())
                || nbt.hasKey("portlyGentleman", 10)
                || event.getTarget() instanceof EntityPlayer)
            return;
        synchronized (event.getTarget()) {
            nbt.setTag("portlyGentleman", event.getTarget().serializeNBT());
            event.getItemStack().setTagCompound(nbt);
            event.getTarget().setDropItemsWhenDead(false);
            event.getWorld().removeEntity(event.getTarget());
        }
        ToolHelper.damageTool(event.getItemStack(), durabilityCost(event.getTarget()), event.getEntityLiving());
        Sounds.playSoundToAll(event.getEntityPlayer(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
        event.getEntityPlayer().swingArm(event.getHand());
        event.getEntityPlayer().sendMessage(new TextComponentTranslation("msg.plustic.portlymodifier.set", nbt.getCompoundTag("portlyGentleman").getString("id")));
        event.setCanceled(true);
        event.setCancellationResult(EnumActionResult.SUCCESS);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void tooltip(ItemTooltipEvent event) {
        NBTTagCompound nbt = TagUtil.getTagSafe(event.getItemStack());
        if (event.getItemStack() == null || !TinkerUtil.hasTrait(nbt, getIdentifier())) return;
        if (nbt.hasKey("portlyGentleman", 10)) {
            event.getToolTip().add(I18n.format("tooltip.plustic.portlymodifier.info", nbt.getCompoundTag("portlyGentleman").getString("id")));
        }
    }

    private int durabilityCost(Entity entity) {
        return Math.max(15, entity instanceof EntityLivingBase ? (int) ((EntityLivingBase) entity).getHealth() : 15);
    }
}
