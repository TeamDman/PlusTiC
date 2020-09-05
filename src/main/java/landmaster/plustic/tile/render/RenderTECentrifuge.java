package landmaster.plustic.tile.render;

import landmaster.plustic.tile.TECentrifuge;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.client.RenderUtil;

public class RenderTECentrifuge extends TileEntitySpecialRenderer<TECentrifuge> {
    @Override
    public void render(TECentrifuge te, double x, double y, double z, float partialTicks, int destroyStage,
                       float alpha) {
        FluidStack fs = te.getTank().getFluid();
        if (fs != null) {
            float height = ((float) fs.amount) / te.getTank().getCapacity();
            float d = RenderUtil.FLUID_OFFSET;
            if (fs.getFluid().isGaseous(fs)) {
                RenderUtil.renderFluidCuboid(fs, te.getPos(), x, y, z, d, 1f - (d + height), d, 1d - d, 1d - d, 1d - d);
            } else {
                RenderUtil.renderFluidCuboid(fs, te.getPos(), x, y, z, d, d, d, 1d - d, height - d, 1d - d);
            }
        }
    }

}
