package landmaster.plustic.traits;

import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.utils.*;

public class Light extends AbstractTrait {
	public static final Light light = new Light();
	
	public Light() {
		super("light", 0xFFFFFF);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public boolean canApplyTogether(IToolMod otherModifier) {
		return !otherModifier.getIdentifier().equals(Heavy.heavy.getIdentifier());
	}
	
	@SubscribeEvent
	public void onToolBuilding(TinkerEvent.OnItemBuilding event) {
		if (TinkerUtil.hasTrait(event.tag, this.getIdentifier())) {
			ToolNBT data = TagUtil.getToolStats(event.tag);
			data.attackSpeedMultiplier *= 1.2;
			data.speed *= 1.2;
			TagUtil.setToolTag(event.tag, data.get());
		}
	}
}
