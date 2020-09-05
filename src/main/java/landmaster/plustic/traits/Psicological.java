package landmaster.plustic.traits;

import landmaster.plustic.api.Toggle;
import landmaster.plustic.util.PsiUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.modifiers.ModReinforced;

public class Psicological extends AbstractTrait {
    public static final int PSI_COST = 28;

    public static final Psicological psicological = new Psicological();

    public Psicological() {
        super("psicological", 0x6D9EFF);
        Toggle.addToggleable(identifier);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (TagUtil.getTagSafe(tool).getBoolean(ModReinforced.TAG_UNBREAKABLE)) {
            return;
        }
        if (entity instanceof EntityPlayer
                && Toggle.getToggleState(tool, identifier)
                && ToolHelper.getCurrentDurability(tool) < ToolHelper.getMaxDurability(tool)) {
            if (PsiUtils.extractPsiExact((EntityPlayer) entity, PSI_COST) >= PSI_COST) {
                ToolHelper.unbreakTool(tool);
                ToolHelper.healTool(tool, 1, (EntityPlayer) entity);
            }
        }
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        if (TagUtil.getTagSafe(tool).getBoolean(ModReinforced.TAG_UNBREAKABLE)) {
            return 0;
        }
        if (entity instanceof EntityPlayer
                && newDamage >= 1
                && Toggle.getToggleState(tool, identifier)) {
            if (PsiUtils.extractPsiExact((EntityPlayer) entity, PSI_COST) >= PSI_COST) {
                --newDamage;
            }
        }
        return super.onToolDamage(tool, damage, newDamage, entity);
    }
}
