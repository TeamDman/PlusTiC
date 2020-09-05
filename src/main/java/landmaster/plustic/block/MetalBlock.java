package landmaster.plustic.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import slimeknights.tconstruct.library.TinkerRegistry;

public class MetalBlock extends Block {
    public MetalBlock(String name) {
        super(Material.IRON);
        this.setHarvestLevel("pickaxe", -1);
        this.setHardness(5);
        this.setTranslationKey(name).setRegistryName(name);
        this.setCreativeTab(TinkerRegistry.tabGeneral);
    }

    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
        return true;
    }
}
