package colonelgerdauf.plustic.gui.handler;

import colonelgerdauf.plustic.gui.*;
import colonelgerdauf.plustic.gui.container.*;
import colonelgerdauf.plustic.tile.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.network.*;

public class PTGuiHandler implements IGuiHandler {
	public static final int FRUITSALAD = 0;
	public static final int MOTS = 1;
	public static final int CENTRIFUGE_CORE = 2;
	
	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case FRUITSALAD:
			return new ContainerFruitSalad(player);
		case MOTS:
			return new ContainerMOTS(player);
		case CENTRIFUGE_CORE:
			TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
			if (te instanceof TECentrifugeCore) {
				return new ContainerTECentrifugeCore(player, (TECentrifugeCore)te);
			}
			return null;
		default:
			return null;
		}
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case FRUITSALAD:
			return new GuiFruitSalad(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		case MOTS:
			return new GuiMOTS(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		case CENTRIFUGE_CORE:
			return new GuiTECentrifugeCore((ContainerTECentrifugeCore)getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		default:
			return null;
		}
	}
	
}
