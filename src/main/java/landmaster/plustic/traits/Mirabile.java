package landmaster.plustic.traits;

import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Mirabile extends AbstractTrait {
    public static final Mirabile mirabile = new Mirabile();

    public Mirabile() {
        super("mirabile", 0xDDFF00);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (isSelected && !world.isRemote && random.nextFloat() < 0.008f) {
            EntityLivingBase elb = entity instanceof EntityLivingBase ? (EntityLivingBase) entity : null;
            BlockPos stdpos = entity.getPosition();
            int curd;
            for (int i = 0; i < 27; ++i) {
                curd = ToolHelper.getCurrentDurability(tool);
                if (curd < 1) break;
                BlockPos pos = stdpos.add((i / 3) / 3 - 1, (i / 3) % 3 - 1, i % 3 - 1);
                IBlockState state = world.getBlockState(pos);
                if (state.getBlock() == Blocks.STONE && state.getValue(BlockStone.VARIANT) == BlockStone.EnumType.STONE) {
                    float rand = random.nextFloat();
                    if (rand < 0.003f && curd >= 5) {
                        world.setBlockState(pos, Blocks.DIAMOND_ORE.getDefaultState());
                        ToolHelper.damageTool(tool, 5, elb);
                    } else if (rand < 0.018f && curd >= 1) {
                        world.setBlockState(pos, Blocks.GOLD_ORE.getDefaultState());
                        ToolHelper.damageTool(tool, 1, elb);
                    }
                }
            }
        }
    }
}
