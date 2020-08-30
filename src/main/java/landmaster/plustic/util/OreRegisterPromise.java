package landmaster.plustic.util;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import landmaster.plustic.ModInfo;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A little solution I rolled out for handling <em>extremely</em> annoying registry event mayhem…
 *
 * @author Landmaster
 */
@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public class OreRegisterPromise extends CompletableFuture<ItemStack> {
    private static final Multimap<String, OreRegisterPromise> promises = MultimapBuilder.hashKeys().arrayListValues().build();

    private final String ore;
    private final String[] modBlacklist;

    public OreRegisterPromise(String ore, String... modBlacklist) {
        this.ore = ore;
        this.modBlacklist = modBlacklist;

        this.whenComplete((stack, ex) -> promises.remove(this.ore, this)); // deregister promises when resolved

        final List<ItemStack> ores = OreDictionary.getOres(this.ore);
        if (ores.stream().allMatch(stack -> ArrayUtils.contains(modBlacklist, stack.getItem().getRegistryName().getResourceDomain()))) {
            promises.put(this.ore, this);
        } else {
            this.complete(ores.get(0));
        }
    }

    /**
     * This does the work of resolving the promises.
     *
     * @param event
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onOreRegister(OreDictionary.OreRegisterEvent event) {
        new ArrayList<>(promises.get(event.getName())) // Have to wrap in an ArrayList to prevent comodification.
                .stream()
                .filter(orp -> !ArrayUtils.contains(orp.modBlacklist, event.getOre().getItem().getRegistryName().getResourceDomain()))
                .forEach(promise -> promise.complete(event.getOre()));
    }
}
