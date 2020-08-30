package landmaster.plustic.gui.handler;

import landmaster.plustic.gui.GuiFruitSalad;
import landmaster.plustic.gui.GuiMOTS;
import landmaster.plustic.gui.GuiTECentrifugeCore;
import landmaster.plustic.gui.container.ContainerFruitSalad;
import landmaster.plustic.gui.container.ContainerMOTS;
import landmaster.plustic.gui.container.ContainerTECentrifugeCore;
import landmaster.plustic.tile.TECentrifugeCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

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
                TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
                if (te instanceof TECentrifugeCore) {
                    return new ContainerTECentrifugeCore(player, (TECentrifugeCore) te);
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
                return new GuiTECentrifugeCore((ContainerTECentrifugeCore) getServerGuiElement(ID, player, world, x, y, z), player.inventory);
            default:
                return null;
        }
    }
}
