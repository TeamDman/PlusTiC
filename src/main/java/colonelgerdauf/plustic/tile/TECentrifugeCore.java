package landmaster.plustic.tile;

import java.util.*;

import javax.annotation.*;

import landmaster.plustic.api.*;
import landmaster.plustic.config.*;
import landmaster.plustic.net.*;
import landmaster.plustic.util.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.energy.*;
import net.minecraftforge.fml.common.network.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.smeltery.*;

public class TECentrifugeCore extends TECentrifuge implements ITickable {
	protected EnergyStorage energyStorage;
	protected int oldEnergy;
	
	public static final int ENERGY_BUFFER_SZ = 80000;
	
	{
		setEnergy(0);
		tank.setCanDrain(false);
	}
	
	public List<TECentrifugeTank> getOutputTanks() {
		List<TECentrifugeTank> result = new ArrayList<>();
		for (BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos(pos.getX(), pos.getY()+1, pos.getZ());
				world.getTileEntity(checkPos) instanceof TECentrifugeTank;
				checkPos.move(EnumFacing.UP)) {
			result.add((TECentrifugeTank)world.getTileEntity(checkPos));
		}
		return result;
	}
	
	private AlloyRecipe cachedRecipe = null;
	
	@Override
	public void update() {
		if (world.isRemote) return;
		
		if (oldEnergy != energyStorage.getEnergyStored()) {
			markDirty();
			oldEnergy = energyStorage.getEnergyStored();
			PacketHandler.INSTANCE.sendToAllAround(new PacketUpdateTECentrifugeCoreEnergy(new Coord4D(pos, world), energyStorage.getEnergyStored()),
					new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
		}
		
		if (this.tank.getFluid() == null || this.tank.getFluid().amount <= 0) return;
		
		if (cachedRecipe == null || CentrifugeRecipes.matches(cachedRecipe, this.tank.getFluid()) <= 0) {
			// recalculate
			cachedRecipe = TinkerRegistry.getAlloys().stream()
			.filter(recipe -> CentrifugeRecipes.matches(recipe, this.tank.getFluid()) > 0)
			.sorted(Comparator.<AlloyRecipe>comparingInt(recipe -> recipe.getFluids().size())
					.thenComparingInt(recipe -> recipe.getResult().amount))
			.findFirst().orElse(null);
		}
		if (cachedRecipe == null) return;
		
		List<TECentrifugeTank> tanks = getOutputTanks();
		if (tanks.size() < cachedRecipe.getFluids().size()) {
			return;
		}
		int numTimesToApply = Math.min(CentrifugeRecipes.matches(cachedRecipe, this.tank.getFluid()),
				CentrifugeRecipes.APPLY_PER_TICK);
		int energyToExtract = Config.centrifugeEnergyPerMB * cachedRecipe.getResult().amount;
		
		allApplications:
		for (int i=0; i<numTimesToApply; ++i) {
			if (energyStorage.extractEnergy(energyToExtract, true)
					< energyToExtract) {
				break allApplications;
			}
			for (int j=0; j<cachedRecipe.getFluids().size(); ++j) {
				if (tanks.get(j).tank.fillInternal(cachedRecipe.getFluids().get(j), false)
						< cachedRecipe.getFluids().get(j).amount) {
					break allApplications;
				}
			}
			
			tank.drainInternal(cachedRecipe.getResult(), true);
			energyStorage.extractEnergy(energyToExtract, false);
			for (int j=0; j<cachedRecipe.getFluids().size(); ++j) {
				tanks.get(j).tank.fillInternal(cachedRecipe.getFluids().get(j), true);
			}
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY
				|| super.hasCapability(capability, facing);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY
				? (T)energyStorage : super.getCapability(capability, facing);
	}
	
	public void setEnergy(int val) {
		energyStorage = new EnergyStorage(ENERGY_BUFFER_SZ, ENERGY_BUFFER_SZ, ENERGY_BUFFER_SZ, val);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		setEnergy(compound.getInteger("Energy"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		compound.setInteger("Energy", energyStorage.getEnergyStored());
		return compound;
	}
}
