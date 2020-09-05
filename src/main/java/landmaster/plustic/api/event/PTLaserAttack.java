package landmaster.plustic.api.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PTLaserAttack extends PlayerEvent {
    public final boolean didHit;
    private final Entity target;
    private final ItemStack tool;

    public PTLaserAttack(EntityPlayer player, Entity target, ItemStack tool, boolean didHit) {
        super(player);
        this.target = target;
        this.tool = tool;
        this.didHit = didHit;
    }

    public Entity getTarget() {
        return target;
    }

    public ItemStack getTool() {
        return tool;
    }
}
