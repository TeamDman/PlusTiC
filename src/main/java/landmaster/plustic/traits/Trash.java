package landmaster.plustic.traits;

import landmaster.plustic.api.Toggle;
import landmaster.plustic.config.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

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
                    for (int i = 0; i < handler.getSlots(); ++i) {
                        thing = handler.insertItem(i, thing, false);
                        if (thing.isEmpty()) {
                            EntityLivingBase entityLiving = entity instanceof EntityLivingBase ?
                                    (EntityLivingBase) entity : FakePlayerFactory.getMinecraft((WorldServer) world);
                            ToolHelper.damageTool(tool, 1, entityLiving);
                            break;
                        }
                    }
                }
            }
        }
    }
}
