package landmaster.plustic.traits;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.items.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.tools.common.item.*;
import slimeknights.tconstruct.tools.modifiers.*;

public class Autorepair extends AbstractTrait {
	public static final Autorepair autorepair = new Autorepair();
	
	public Autorepair() {
		super("autorepair", 0xEF2647);
	}
	
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (!world.isRemote && ToolHelper.isBroken(tool)) {
			IItemHandler ih = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			if (ih != null) {
				//System.out.println("TIME");
				for (int i=0; i<ih.getSlots(); ++i) {
					ItemStack is = ih.getStackInSlot(i).copy();
					if (is.isEmpty()) continue;
					is.setCount(1);
					if (is.getItem() instanceof SharpeningKit) {
						//System.out.println(is);
						NonNullList<ItemStack> singleton = NonNullList.from(null, is);
						ItemStack repairRes = ToolBuilder.tryRepairTool(singleton, tool, true);
						if (!repairRes.isEmpty()) {
							// HACK: Copy over the metadata and NBT values to the main tool
							tool.setItemDamage(repairRes.getItemDamage());
							tool.setTagCompound(repairRes.getTagCompound());
							ih.extractItem(i, 1, false);
							break;
						}
					}
				}
			}
		}
	}
	
	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		super.applyEffect(rootCompound, modifierTag);
		if (rootCompound.getBoolean(ModReinforced.TAG_UNBREAKABLE)) {
			NBTTagCompound toolTag = TagUtil.getToolTag(rootCompound);
			int modifiers = toolTag.getInteger(Tags.FREE_MODIFIERS) + 1;
			toolTag.setInteger(Tags.FREE_MODIFIERS, Math.max(0, modifiers));
			TagUtil.setToolTag(rootCompound, toolTag);
		}
	}
}
