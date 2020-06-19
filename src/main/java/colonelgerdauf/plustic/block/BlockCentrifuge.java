package colonelgerdauf.plustic.block;

import java.util.*;

import javax.annotation.*;

import colonelgerdauf.plustic.*;
import colonelgerdauf.plustic.gui.handler.*;
import colonelgerdauf.plustic.tile.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.util.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.*;

public class BlockCentrifuge extends Block implements IMetaBlockName {
	public static final PropertyBool CORE = PropertyBool.create("core");
	
	public BlockCentrifuge() {
		super(Material.ROCK);
		this.setHarvestLevel("pickaxe", 0);
		this.setHardness(10F);
		this.setResistance(80F);
		this.setSoundType(SoundType.STONE);
		this.setDefaultState(blockState.getBaseState().withProperty(CORE, false));
		this.setTranslationKey("centrifuge").setRegistryName("centrifuge");
		this.setCreativeTab(TinkerRegistry.tabSmeltery);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, CORE);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(CORE, meta != 0);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(CORE) ? 1 : 0;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
	
	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		list.add(new ItemStack(this));
		list.add(new ItemStack(this, 1, 1));
	}
	
	@Override
	public String getSpecialName(ItemStack stack) {
		return stack.getMetadata() != 0 ? "core" : "tank";
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(this, 1, getMetaFromState(state));
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return state.getValue(CORE) ? new TECentrifugeCore() : new TECentrifugeTank();
	}
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		// adapted from Tinker's TileTank code
		TileEntity te = worldIn.getTileEntity(pos);
		if (te == null || !te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) {
			return false;
		}
		IFluidHandler fluidHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (FluidUtil.interactWithFluidHandler(playerIn, hand, fluidHandler)) {
			return true;
		}
		
		if (state.getValue(CORE) && !(Block.getBlockFromItem(heldItem.getItem()) instanceof BlockCentrifuge)) {
			if (!worldIn.isRemote) {
				playerIn.openGui(PlusTiC.INSTANCE, PTGuiHandler.CENTRIFUGE_CORE, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		}
		
		return FluidUtil.getFluidHandler(heldItem) != null;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		super.getDrops(drops, world, pos, state, fortune);
		for (ItemStack drop: drops) {
			if (Block.getBlockFromItem(drop.getItem()) instanceof BlockCentrifuge) {
				drop.setTagCompound(new NBTTagCompound());
				drop.getTagCompound().setTag("BlockEntityTag", world.getTileEntity(pos).serializeNBT());
			}
		}
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		if (willHarvest) return true; // see BlockFlowerPot.java
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}
	
	@Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack tool) {
		super.harvestBlock(world, player, pos, state, te, tool);
		world.setBlockToAir(pos);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		NBTTagCompound blockEntityTag = stack.getSubCompound("BlockEntityTag");
		if (blockEntityTag != null && blockEntityTag.hasKey("Tank", 10)) {
			FluidStack fs = FluidStack.loadFluidStackFromNBT(blockEntityTag.getCompoundTag("Tank"));
			tooltip.add(I18n.format("tooltip.plustic.centrifuge.fluid_info", fs.getLocalizedName(), fs.amount));
		}
	}
}
