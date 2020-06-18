package landmaster.plustic.gui.container;

import landmaster.plustic.tile.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class ContainerTECentrifugeCore extends Container {
	protected final EntityPlayer player;
	protected final TECentrifugeCore te;
	
	public TECentrifugeCore getTE() {
		return te;
	}

	public ContainerTECentrifugeCore(EntityPlayer player, TECentrifugeCore te) {
		super();
		this.player = player;
		this.te = te;
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
	
		for (int k = 0; k < 9; k++) {
			addSlotToContainer(new Slot(player.inventory, k, 8 + k * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistanceSq(
				this.te.getPos().getX() + 0.5,
				this.te.getPos().getY() + 0.5,
				this.te.getPos().getZ() + 0.5) <= 64;
	}
}
