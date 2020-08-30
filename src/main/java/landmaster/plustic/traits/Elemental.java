package landmaster.plustic.traits;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Elemental extends AbstractTrait {
    public static final Elemental elemental = new Elemental();

    public Elemental() {
        super("elemental", 0xF66AFD);
    }

    @Override
    public void beforeBlockBreak(ItemStack tool, BlockEvent.BreakEvent event) {
        destroyColumn(tool, event.getWorld(), event.getState(), event.getPos(), event.getPlayer());
    }

    private void destroyColumn(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityPlayer player) {
        if (!world.isRemote && state.getBlock() instanceof BlockFalling && tool.canHarvestBlock(state)) {
            BlockPos posUp = pos, posDown = pos;
            while (world.getBlockState(posUp = posUp.add(0, 1, 0)).getBlock() == state.getBlock())
                ; // nothing to do
            while (world.getBlockState(posDown = posDown.add(0, -1, 0)).getBlock() == state.getBlock())
                ; // nothing to do
            while (!(posUp = posUp.add(0, -1, 0)).equals(posDown)) {
                if (posUp.equals(pos)) continue;
                if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool) > 0 && state.getBlock().canSilkHarvest(world, posUp, state, player)) {
                    world.destroyBlock(posUp, false);
                    if (!player.capabilities.isCreativeMode) {
                        EntityItem ie = new EntityItem(world, posUp.getX(), posUp.getY(), posUp.getZ(), new ItemStack(Item.getItemFromBlock(state.getBlock())));
                        world.spawnEntity(ie);
                    }
                } else {
                    world.destroyBlock(posUp, false);
                    if (!player.capabilities.isCreativeMode) {
                        state.getBlock().dropBlockAsItem(world, posUp, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool));
                    }
                }
            }
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (wasHit && random.nextFloat() < 0.22f) {
            int rand = random.nextInt(4);
            switch (rand) {
                case 0: // Water
                    player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 80));
                    player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 1200));
                    break;
                case 1: // Wind
                    player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 80));
                    player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 80));
                    break;
                case 2: // Earth
                    player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 80));
                    player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 80));
                    break;
                case 3: // Fire
                    player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 80));
                    player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 80));
                    break;
            }
        }
    }
}
