package colonelgerdauf.plustic.api.event;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.*;

public class PTLaserAttack extends PlayerEvent {
	private final Entity target;
	private final ItemStack tool;
	public final boolean didHit;
	
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
