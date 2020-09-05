package landmaster.plustic.tools.parts;

import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.tools.TinkerMaterials;

/**
 * Class for tool parts such that stone parts can be created for casting <em>only</em>.
 *
 * @author Landmaster
 */
public class ToolPartWithStoneMold extends ToolPart {
    public ToolPartWithStoneMold(int cost) {
        super(cost);
    }

    @Override
    public boolean canUseMaterial(Material mat) {
        return mat == TinkerMaterials.stone || super.canUseMaterial(mat);
    }
}
