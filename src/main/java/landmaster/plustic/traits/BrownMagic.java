package landmaster.plustic.traits;

import landmaster.plustic.PlusTiC;
import landmaster.plustic.api.Portal;
import landmaster.plustic.net.PacketBrownAbracadabra;
import landmaster.plustic.net.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class BrownMagic extends AbstractTrait {
    public static final BrownMagic brownmagic = new BrownMagic();

    public BrownMagic() {
        super("brownmagic", 0x3A1B00);
        Portal.addPortalable(identifier);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (isSelected && FMLCommonHandler.instance().getSide().isClient()) {
            if (PlusTiC.proxy.isControlPressed("brown_magic")) {
                PacketHandler.INSTANCE.sendToServer(new PacketBrownAbracadabra());
            }
        }
    }
}
