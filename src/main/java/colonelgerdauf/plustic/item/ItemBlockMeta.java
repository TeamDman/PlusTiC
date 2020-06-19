package colonelgerdauf.plustic.item;

import colonelgerdauf.plustic.block.*;
import net.minecraft.block.*;
import net.minecraft.item.*;

public class ItemBlockMeta extends ItemBlock {
	public <T extends Block & IMetaBlockName> ItemBlockMeta(T block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	@Override
	public String getTranslationKey(ItemStack stack) {
		return super.getTranslationKey(stack) + "." + ((IMetaBlockName)block).getSpecialName(stack);
	}
}
