package landmaster.plustic.tools.nbt;

import java.util.*;

import landmaster.plustic.tools.stats.*;
import net.minecraft.nbt.*;

public class LaserNBT extends ToolEnergyNBT {
	public static final String TagPOWER = "LaserGunPower";
	public static final String TagRANGE = "LaserGunRange";
	
	public float power, range;
	
	public LaserNBT() {
		power = 0;
	}
	
	public LaserNBT(NBTTagCompound nbt) {
		super(nbt);
	}
	
	/**
	 * Call this after {@link slimeknights.tconstruct.library.tools.ToolNBT#head(slimeknights.tconstruct.library.materials.HeadMaterialStats...)}
	 * @param stats the laser medium stats
	 * @return this object
	 */
	public LaserNBT laserMedium(LaserMediumMaterialStats... stats) {
		this.power = (float)Arrays.stream(stats).filter(stat -> stat!=null).mapToDouble(stat -> stat.power).sum() / stats.length;
		this.range = (float)Arrays.stream(stats).filter(stat -> stat!=null).mapToDouble(stat -> stat.range).sum() / stats.length;
		this.attack = this.power;
		return this;
	}
	
	@Override
	public void read(NBTTagCompound tag) {
		super.read(tag);
		this.power = tag.getFloat(TagPOWER);
		this.range = tag.getFloat(TagRANGE);
	}
	
	@Override
	public void write(NBTTagCompound tag) {
		super.write(tag);
		tag.setFloat(TagPOWER, this.power);
		tag.setFloat(TagRANGE, this.range);
	}
}
