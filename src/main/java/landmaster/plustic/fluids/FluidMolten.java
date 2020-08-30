package landmaster.plustic.fluids;

import landmaster.plustic.ModInfo;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.fluid.FluidColored;

public class FluidMolten extends FluidColored {

    public static ResourceLocation ICON_MetalStill = new ResourceLocation(ModInfo.MODID, "blocks/fluids/molten_metal");
    public static ResourceLocation ICON_MetalFlowing = new ResourceLocation(ModInfo.MODID, "blocks/fluids/molten_metal_flow");

    public FluidMolten(String fluidName, int color) {
        this(fluidName, color, ICON_MetalStill, ICON_MetalFlowing);
    }

    public FluidMolten(String fluidName, int color, ResourceLocation still, ResourceLocation flow) {
        super(fluidName, color, still, flow);

        this.setDensity(2000); // thicker than a bowl of oatmeal
        this.setViscosity(10000); // slow moving
        this.setTemperature(1000); // not exactly lava, but still hot. Should depend on the material
        this.setLuminosity(10); // glowy by default!

        // rare by default
        setRarity(EnumRarity.UNCOMMON);
    }

    @Override
    public String getUnlocalizedName() {
        return super.getUnlocalizedName() + ".name";
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getLocalizedName(FluidStack stack) {
        String s = this.getUnlocalizedName();
        return s == null ? "" : I18n.translateToLocal(s);
    }
}
