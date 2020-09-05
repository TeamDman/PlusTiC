package landmaster.plustic.tools.stats;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.resources.I18n;
import slimeknights.tconstruct.library.materials.AbstractMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

import java.util.List;

public class BatteryCellMaterialStats extends AbstractMaterialStats {
    public static final String TYPE = "battery_cell";

    static {
        Material.UNKNOWN.addStats(new BatteryCellMaterialStats(0));
    }

    public final int energy;

    public BatteryCellMaterialStats(int energy) {
        super(TYPE);
        this.energy = energy;
    }

    @Override
    public List<String> getLocalizedInfo() {
        return ImmutableList.of(I18n.format("stat.battery_cell.energy.name", energy));
    }

    @Override
    public List<String> getLocalizedDesc() {
        return ImmutableList.of(I18n.format("stat.battery_cell.energy.desc"));
    }

}
