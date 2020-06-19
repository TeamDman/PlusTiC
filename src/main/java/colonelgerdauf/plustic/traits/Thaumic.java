package colonelgerdauf.plustic.traits;

import java.util.*;

import net.minecraft.nbt.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.utils.*;
//import slimeknights.tconstruct.tools.ranged.item.*;

public class Thaumic extends AbstractTrait {
	public static final Thaumic thaumic = new Thaumic();
	
	public Thaumic() {
		super("thaumic", 0x1E0066);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onToolBuilding(TinkerEvent.OnItemBuilding event) {
		int count = 0;
		
		List<PartMaterialType> reqComponents = event.tool.getRequiredComponents();
		
		int size = reqComponents.size();
		
		if (event.materials.size() < size) {
			size = event.materials.size();
		}
		
		for (int i = 0; i < size; i++) {
			PartMaterialType required = reqComponents.get(i);
			Material material = event.materials.get(i);
			
			boolean hasTrait = false;
			
			for (ITrait trait: required.getApplicableTraitsForMaterial(material)) {
				if (identifier.equals(trait.getIdentifier())) {
					hasTrait = true;
					break;
				}
			}
			
			if (hasTrait) ++count;
		}
		
		if (count > 0) {
			increaseFreeModifiers(event.tag);
		}
		if (count >= Math.min(3, event.tool.getRequiredComponents().size())) {
			/*if (event.tool instanceof ShortBow) {
				System.out.println("COUNT: "+count+"; MIN COMPONENTS: "+event.tool.getRequiredComponents().size());
			}*/
			increaseFreeModifiers(event.tag);
		}
	}
	
	protected void increaseFreeModifiers(NBTTagCompound rootCompound) {
		NBTTagCompound toolTag = TagUtil.getToolTag(rootCompound);
	    int modifiers = toolTag.getInteger(Tags.FREE_MODIFIERS)+1;
	    toolTag.setInteger(Tags.FREE_MODIFIERS, Math.max(0, modifiers));
	    TagUtil.setToolTag(rootCompound, toolTag);
	}
}
