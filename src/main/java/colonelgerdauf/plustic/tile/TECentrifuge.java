package landmaster.plustic.tile;

import javax.annotation.*;

import landmaster.plustic.net.*;
import landmaster.plustic.util.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.fml.common.network.*;
import slimeknights.tconstruct.library.materials.*;

public abstract class TECentrifuge extends TileEntity {
	protected final FluidTank tank = new FluidTank(Material.VALUE_Block * 8) {
		@Override
		protected void onContentsChanged() {
			sendTankUpdates();
		}
	};
	
	protected void sendTankUpdates() {
		if (world != null && !world.isRemote) {
			markDirty();
			PacketHandler.INSTANCE.sendToAllAround(new PacketUpdateTECentrifugeLiquid(new Coord4D(pos, world), tank.getFluid()), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
		}
	}
	
	@Override
	public void onLoad() {
		if (world.isRemote) {
			PacketHandler.INSTANCE.sendToServer(new PacketRequestUpdateTECentrifuge(new Coord4D(pos, world)));
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("Tank", 10)) {
			tank.setFluid(FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Tank")));
			sendTankUpdates();
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		if (tank.getFluid() != null) {
			compound.setTag("Tank", tank.getFluid().writeToNBT(new NBTTagCompound()));
		}
		return compound;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
				|| super.hasCapability(capability, facing);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
				? (T)tank : super.getCapability(capability, facing);
	}
	
	public FluidTank getTank() { return tank; }
}