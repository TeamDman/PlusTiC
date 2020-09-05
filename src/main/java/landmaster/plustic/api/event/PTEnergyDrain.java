package landmaster.plustic.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PTEnergyDrain extends PlayerEvent {
    private final ItemStack tool;
    public int energyDrained;

    public PTEnergyDrain(ItemStack tool, EntityPlayer player, int energyDrained) {
        super(player);
        this.tool = tool;
        this.energyDrained = energyDrained;
    }

    public ItemStack getTool() {
        return tool;
    }
}
