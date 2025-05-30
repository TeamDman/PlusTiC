package queengooborg.plusticreforged.modifiers;

import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;

import java.awt.*;

public class ManaModifier extends Modifier {
	public ManaModifier() {
		super("mana", "Mana", new Description("Rechargeable... with flowers!", "Mana (from Botania; more mods' mana may be added in the future) can be used in place of durability, and can heal the tool over time."), new Color(84, 229, 255));
	}
}

// public class Mana extends AbstractTrait {
//	public static final int MANA_DRAW = 100;
//	public static final Mana mana = new Mana();
//
//	public Mana() {
//		super("mana", 0x54E5FF);
//		Toggle.addToggleable(identifier);
//	}
//
//	private static boolean drawMana(EntityPlayer ent) {
//		IItemHandler handler = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
//		for (int i = 0; i < handler.getSlots(); ++i) {
//			if (ManaItemHandler.requestManaExactForTool(handler.getStackInSlot(i), ent, MANA_DRAW, true)) {
//				return true;
//			}
//		}
//
//		IBaublesItemHandler ib = BaublesApi.getBaublesHandler(ent);
//		for (int i = 0; i < ib.getSlots(); ++i) {
//			if (ManaItemHandler.requestManaExactForTool(ib.getStackInSlot(i), ent, MANA_DRAW, true)) {
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	@Override
//	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
//		if (TagUtil.getTagSafe(tool).getBoolean(ModReinforced.TAG_UNBREAKABLE)) {
//			return;
//		}
//		if (!world.isRemote
//				&& entity instanceof EntityPlayer
//				&& ToolHelper.getCurrentDurability(tool) < ToolHelper.getMaxDurability(tool)
//				&& Toggle.getToggleState(tool, identifier)
//				&& drawMana((EntityPlayer) entity)) {
//			ToolHelper.unbreakTool(tool);
//			ToolHelper.healTool(tool, 1, (EntityPlayer) entity);
//		}
//	}
//
//	@Override
//	public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
//		if (TagUtil.getTagSafe(tool).getBoolean(ModReinforced.TAG_UNBREAKABLE)) {
//			return 0;
//		}
//		if (!entity.getEntityWorld().isRemote
//				&& entity instanceof EntityPlayer
//				&& Toggle.getToggleState(tool, identifier)
//				&& drawMana((EntityPlayer) entity)) {
//			--newDamage;
//		}
//		return super.onToolDamage(tool, damage, newDamage, entity);
//	}
//}
