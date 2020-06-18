package landmaster.plustic.traits;

import org.apache.commons.lang3.*;

import net.minecraftforge.oredict.*;

public class GetLucky extends DeathSaveTrait {
	public static final GetLucky getlucky = new GetLucky();
	
	public GetLucky() {
		super("getlucky", 0xFF4511, 8,
				stack -> !stack.isEmpty()
				&& ArrayUtils.contains(OreDictionary.getOreIDs(stack), OreDictionary.getOreID("gemPhoenixite")),
				"msg.plustic.getlucky.use");
	}
}
