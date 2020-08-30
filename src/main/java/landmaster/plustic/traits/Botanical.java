package landmaster.plustic.traits;

import com.google.common.collect.ImmutableList;
import landmaster.plustic.config.Config;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;

import java.util.List;

public class Botanical extends AbstractTraitLeveled {
    public static final int MAX_LEVELS = 3;

    public static final List<Botanical> botanical = ImmutableList.of(new Botanical(1), new Botanical(2));

    public Botanical(int levels) {
        super("botanical", 0x00FF00, MAX_LEVELS, levels);
    }

    @Override
    public void applyModifierEffect(NBTTagCompound rootCompound) {
        NBTTagCompound toolTag = TagUtil.getToolTag(rootCompound);
        int modifiers = toolTag.getInteger(Tags.FREE_MODIFIERS) + Config.getBotanAmount().get(levels - 1);
        toolTag.setInteger(Tags.FREE_MODIFIERS, Math.max(0, modifiers));
        TagUtil.setToolTag(rootCompound, toolTag);
    }
}
