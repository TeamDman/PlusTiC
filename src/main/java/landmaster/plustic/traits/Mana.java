package landmaster.plustic.traits;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import landmaster.plustic.api.Toggle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.modifiers.ModReinforced;
import vazkii.botania.api.mana.*;

public class Mana extends AbstractTrait {
    public static final int MANA_DRAW = 100;
    public static final Mana mana = new Mana();

    public Mana() {
        super("mana", 0x54E5FF);
        Toggle.addToggleable(identifier);
    }

    private static boolean drawMana(EntityPlayer ent) {
        IItemHandler handler = ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        for (int i = 0; i < handler.getSlots(); ++i) {
            if (ManaItemHandler.requestManaExactForTool(handler.getStackInSlot(i), ent, MANA_DRAW, true)) {
                return true;
            }
        }

        IBaublesItemHandler ib = BaublesApi.getBaublesHandler(ent);
        for (int i = 0; i < ib.getSlots(); ++i) {
            if (ManaItemHandler.requestManaExactForTool(ib.getStackInSlot(i), ent, MANA_DRAW, true)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (TagUtil.getTagSafe(tool).getBoolean(ModReinforced.TAG_UNBREAKABLE)) {
            return;
        }
        if (!world.isRemote
                && entity instanceof EntityPlayer
                && ToolHelper.getCurrentDurability(tool) < ToolHelper.getMaxDurability(tool)
                && Toggle.getToggleState(tool, identifier)
                && drawMana((EntityPlayer) entity)) {
            ToolHelper.unbreakTool(tool);
            ToolHelper.healTool(tool, 1, (EntityPlayer) entity);
        }
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        if (TagUtil.getTagSafe(tool).getBoolean(ModReinforced.TAG_UNBREAKABLE)) {
            return 0;
        }
        if (!entity.getEntityWorld().isRemote
                && entity instanceof EntityPlayer
                && Toggle.getToggleState(tool, identifier)
                && drawMana((EntityPlayer) entity)) {
            --newDamage;
        }
        return super.onToolDamage(tool, damage, newDamage, entity);
    }
}
