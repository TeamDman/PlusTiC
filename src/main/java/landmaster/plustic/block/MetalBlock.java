package landmaster.plustic.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import slimeknights.tconstruct.library.TinkerRegistry;

import javax.annotation.Nonnull;

public class MetalBlock extends Block {

    public MetalBlock(String name) {
        super(Material.IRON);
        this.setHarvestLevel("pickaxe", -1);
        this.setHardness(5);
        this.setUnlocalizedName(name).setRegistryName(name);
        this.setCreativeTab(TinkerRegistry.tabGeneral);
    }

    @Override
    public boolean isBeaconBase(@Nonnull IBlockAccess worldObj, @Nonnull BlockPos pos, @Nonnull BlockPos beacon) {
        return true;
    }
}
