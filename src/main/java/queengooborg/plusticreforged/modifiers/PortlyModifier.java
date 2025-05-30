package queengooborg.plusticreforged.modifiers;

import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;

import java.awt.*;

public class PortlyModifier extends Modifier {
	public PortlyModifier() {
		super("portly", "Portly", new Description("Crouch and use an entity to capture it (costs durability based on entity health). Press the \"release entity\" key (default \"0\") in order to release it."), new Color(0, 68, 59));
	}

	// XXX Convert me!

//	public Portly() {
//		super("portly", 0x00443B);
//		MinecraftForge.EVENT_BUS.register(this);
//	}
//
//	@Override
//	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
//		if (isSelected && FMLCommonHandler.instance().getSide().isClient()) {
//			if (PlusTiC.proxy.isControlPressed("release_entity")) {
//				PacketHandler.INSTANCE.sendToServer(new PacketReleaseEntity());
//			}
//		}
//	}
//
//	@SubscribeEvent(priority = EventPriority.LOWEST)
//	public void captureEntity(PlayerInteractEvent.EntityInteract event) {
//		NBTTagCompound nbt = TagUtil.getTagSafe(event.getItemStack());
//		if (event.getWorld().isRemote
//				|| !event.getEntityPlayer().isSneaking()
//				|| event.getItemStack() == null
//				|| !TinkerUtil.hasTrait(nbt, getIdentifier())
//				|| ToolHelper.getCurrentDurability(event.getItemStack()) < durabilityCost(event.getTarget())
//				|| nbt.hasKey("portlyGentleman", 10)
//				|| event.getTarget() instanceof EntityPlayer)
//			return;
//		synchronized (event.getTarget()) {
//			nbt.setTag("portlyGentleman", event.getTarget().serializeNBT());
//			event.getItemStack().setTagCompound(nbt);
//			event.getTarget().setDropItemsWhenDead(false);
//			event.getWorld().removeEntity(event.getTarget());
//		}
//		ToolHelper.damageTool(event.getItemStack(), durabilityCost(event.getTarget()), event.getEntityLiving());
//		Sounds.playSoundToAll(event.getEntityPlayer(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
//		event.getEntityPlayer().swingArm(event.getHand());
//		event.getEntityPlayer().sendMessage(new TextComponentTranslation(
//				"msg.plustic.portlymodifier.set", nbt.getCompoundTag("portlyGentleman").getString("id")));
//		event.setCanceled(true);
//		event.setCancellationResult(EnumActionResult.SUCCESS);
//	}
//	@SideOnly(Side.CLIENT)
//	@SubscribeEvent
//	public void tooltip(ItemTooltipEvent event) {
//		NBTTagCompound nbt = TagUtil.getTagSafe(event.getItemStack());
//		if (event.getItemStack() == null
//				|| !TinkerUtil.hasTrait(nbt, getIdentifier())) return;
//		if (nbt.hasKey("portlyGentleman", 10)) {
//			event.getToolTip().add(I18n.format("tooltip.plustic.portlymodifier.info",
//					nbt.getCompoundTag("portlyGentleman").getString("id")));
//		}
//	}
//	private int durabilityCost(Entity entity) {
//		return Math.max(15, entity instanceof EntityLivingBase ?
//				(int)((EntityLivingBase)entity).getHealth() : 15);
//	}
}
