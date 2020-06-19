package colonelgerdauf.plustic.traits;

import colonelgerdauf.plustic.api.*;
import colonelgerdauf.plustic.config.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.utils.*;

public class Trash extends AbstractTrait {
	public static final Trash trash = new Trash();
	
	public Trash() {
		super("trash", 0x005500);
		Toggle.addToggleable(identifier);
	}
	
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (!world.isRemote && world instanceof WorldServer
				&& ToolHelper.getCurrentDurability(tool) >= 1 && isSelected
				&& Toggle.getToggleState(tool, identifier) && random.nextFloat() < 0.01f) {
			IItemHandler handler = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			if (handler != null) {
				ItemStack thing = Config.fetchThing(random);
				if (!thing.isEmpty()) {
					thing = thing.copy();
					for (int i=0; i<handler.getSlots(); ++i) {
						thing = handler.insertItem(i, thing, false);
						if (thing.isEmpty()) {
							EntityLivingBase entityLiving = entity instanceof EntityLivingBase ?
									(EntityLivingBase)entity : FakePlayerFactory.getMinecraft((WorldServer)world);
							ToolHelper.damageTool(tool, 1, entityLiving);
							break;
						}
					}
				}
			}
		}
	}
}
