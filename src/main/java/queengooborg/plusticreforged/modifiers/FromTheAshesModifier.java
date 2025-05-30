package queengooborg.plusticreforged.modifiers;

import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;

import java.awt.*;

public class FromTheAshesModifier extends Modifier {
	public FromTheAshesModifier() {
		super("fromtheashes", "From the Ashes", new Description("Like the legend of the phoenix...", "Press the \"set portal\" key (default \"N\") to set a virtual portal on the block you are pointing at. If you are on the brink of death, you have %d phoenixite gems to spare, and the portal has enough space above, then the gems are consumed, you are teleported to the virtual portal, and you are spared. (The tool must be in your hand.)"), new Color(255, 69, 17));
	}

	// XXX Convert me!

	// public class GetLucky extends DeathSaveTrait {
	//	public static final GetLucky getlucky = new GetLucky();
	//
	//	public GetLucky() {
	// 		// super(String identifier, int color, int cost, Predicate<ItemStack> stackMatcher, String unlocSaveMessage)
	//		super("getlucky", 0xFF4511, 8,
	//				stack -> !stack.isEmpty()
	//				&& ArrayUtils.contains(OreDictionary.getOreIDs(stack), OreDictionary.getOreID("gemPhoenixite")),
	//				"msg.plustic.getlucky.use");
	//              // MSG: "I'm up all night to get lucky!"
	//	}
	//}
}
