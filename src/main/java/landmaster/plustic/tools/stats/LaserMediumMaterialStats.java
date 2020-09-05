package landmaster.plustic.tools.stats;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.resources.I18n;
import slimeknights.tconstruct.library.materials.AbstractMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

import java.util.List;

public class LaserMediumMaterialStats extends AbstractMaterialStats {
    public static final String TYPE = "laser_medium";

    private static float maxRange = 20;

    static {
        Material.UNKNOWN.addStats(new LaserMediumMaterialStats(0, 0));
    }

    public final float power, range;

    public LaserMediumMaterialStats(float power, float range) {
        super(TYPE);
        this.power = power;
        this.range = range;
        maxRange = Math.max(maxRange, range);
    }

    public static float getMaxRange() {
        return maxRange;
    }

    @Override
    public List<String> getLocalizedInfo() {
        return ImmutableList.of(I18n.format("stat.laser_medium.power.name", power),
                I18n.format("stat.laser_medium.range.name", range));
    }

    @Override
    public List<String> getLocalizedDesc() {
        return ImmutableList.of(I18n.format("stat.laser_medium.power.desc"),
                I18n.format("stat.laser_medium.range.desc"));
    }
}
