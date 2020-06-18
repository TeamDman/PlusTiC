package landmaster.plustic.traits;

import org.apache.commons.lang3.*;

import net.minecraftforge.oredict.*;

public class NickOfTime extends DeathSaveTrait {
	public static final NickOfTime nickOfTime = new NickOfTime();
	
	public NickOfTime() {
		super("nickoftime", 0xFFF98E, 8, stack -> !stack.isEmpty()
				&& ArrayUtils.contains(OreDictionary.getOreIDs(stack), OreDictionary.getOreID("enderpearl")),
				"msg.plustic.nickmodifier.use");
	}
}
