package landmaster.plustic.traits;

import de.ellpeck.actuallyadditions.mod.items.InitItems;

public class Starfishy extends DeathSaveTrait {
    public static final Starfishy starfishy = new Starfishy();

    public Starfishy() {
        super("starfishy", 0xF2F7FF, 32, stack -> stack.getItem() == InitItems.itemCrystal && stack.getMetadata() == 5, "msg.plustic.starfishy.use");
    }
}
