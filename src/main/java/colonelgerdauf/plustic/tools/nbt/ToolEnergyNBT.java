package landmaster.plustic.tools.nbt;

import java.util.*;

import landmaster.plustic.tools.stats.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.tools.*;

public class ToolEnergyNBT extends ToolNBT {
	public static final String TagENERGY = "LaserGunEnergy";
	
	public int energy;
	
	public ToolEnergyNBT() {
		energy = 0;
	}
	
	public ToolEnergyNBT(NBTTagCompound nbt) {
		super(nbt);
	}
	
	@Override
	public void read(NBTTagCompound tag) {
		super.read(tag);
		energy = tag.getInteger(TagENERGY);
	}
	
	@Override
	public void write(NBTTagCompound tag) {
		super.write(tag);
		tag.setInteger(TagENERGY, energy);
	}
	
	public ToolEnergyNBT batteryCell(BatteryCellMaterialStats... stats) {
		energy = Arrays.stream(stats).filter(stat -> stat!=null).mapToInt(stat -> stat.energy).sum();
		return this;
	}
}
