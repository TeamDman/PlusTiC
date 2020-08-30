package landmaster.plustic.traits;

import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

public class NickOfTime extends DeathSaveTrait {
    public static final NickOfTime nickOfTime = new NickOfTime();

    public NickOfTime() {
        super("nickoftime", 0xFFF98E, 8, stack -> !stack.isEmpty() && ArrayUtils.contains(OreDictionary.getOreIDs(stack), OreDictionary.getOreID("enderpearl")), "msg.plustic.nickmodifier.use");
    }
}
