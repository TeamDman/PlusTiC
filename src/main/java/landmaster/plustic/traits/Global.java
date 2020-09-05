package landmaster.plustic.traits;

import landmaster.plustic.api.Toggle;
import landmaster.plustic.util.Coord4D;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import slimeknights.tconstruct.library.tools.DualToolHarvestUtils;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.tools.Kama;

import java.util.Collections;
import java.util.ListIterator;
import java.util.Set;
import java.util.WeakHashMap;

public class Global extends AbstractTrait {
    public static final Global global = new Global();

    private static final Set<Block> warnedBlocks = Collections.newSetFromMap(new WeakHashMap<>());

    public Global() {
        super("global", 0xFFE0F1);
        MinecraftForge.EVENT_BUS.register(this);
        Toggle.addToggleable(identifier);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void blockDrops(BlockEvent.HarvestDropsEvent event) {
        if (event.getWorld().isRemote
                || event.getHarvester() == null) return;
        ItemStack tool = DualToolHarvestUtils.getItemstackToUse(event.getHarvester(), event.getState());
        if (!TinkerUtil.hasTrait(TagUtil.getTagSafe(tool), getIdentifier())) return;
        __blockHarvestDrops(tool, event);
    }

    private void __blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event) {
        if (!Toggle.getToggleState(tool, identifier)) return;
        NBTTagCompound nbt0 = TagUtil.getTagSafe(tool);
        if (nbt0.hasKey("global", 10) && ToolHelper.isToolEffective2(tool, event.getState())) {
            //System.out.println(event.getDrops());
            //new Exception().printStackTrace();

            NBTTagCompound nbt = nbt0.getCompoundTag("global");
            Coord4D coord = Coord4D.fromNBT(nbt);
            if (coord.pos().equals(event.getPos())) return; // prevent self-linking
            TileEntity te = coord.TE();
            if (te == null) return;
            IItemHandler ih = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                    EnumFacing.VALUES[nbt.getByte("facing")]);
            if (ih == null) return;

            // *cough* Extra Utilities *cough*
            try {
                ListIterator<ItemStack> dummy = event.getDrops().listIterator();

                if (dummy.hasNext()) {
                    ItemStack is = dummy.next();
                    dummy.set(is); // This simply sets the 1st element of the list to itself, leaving the list unchanged. If the list is immutable, then this will throw an UnsupportedOperationException.
                }
            } catch (UnsupportedOperationException e) {
                if (!warnedBlocks.contains(event.getState().getBlock())) {
                    FMLLog.bigWarning("Block " + event.getState().getBlock() + " implements block drops incorrectly. "
                            + "It appears that it overrides the OFFICIALLY DEPRECATED method "
                            + "getDrops(IBlockAccess, BlockPos, IBlockState, int) instead of the correct method "
                            + "getDrops(NonNullList, IBlockAccess, BlockPos, IBlockState, int). This prevents "
                            + "features such as PlusTiC's Global Traveler from working properly with these blocks.\n"
                            + "USERS: This is a BUG in the mod " + event.getState().getBlock().getRegistryName().getNamespace() + "; report this to them!");
                    warnedBlocks.add(event.getState().getBlock());
                }

                return;
            }

            ListIterator<ItemStack> it = event.getDrops().listIterator();
            ItemStack keptSeed = ItemStack.EMPTY;
            while (it.hasNext()) {
                ItemStack stk = it.next();
                if (event.getWorld().rand.nextFloat() > event.getDropChance()) {
                    it.remove();
                } else {
                    if (tool.getItem() instanceof Kama
                            && stk.getItem() instanceof IPlantable
                            && keptSeed.isEmpty()) {
                        keptSeed = stk.splitStack(1);
                    }
                }
            }

            if (!(tool.getItem() instanceof Kama) || !keptSeed.isEmpty()) {
                it = event.getDrops().listIterator();
                while (it.hasNext()) {
                    ItemStack stk = it.next();
                    for (int j = 0; j < ih.getSlots(); ++j) {
                        ItemStack res = ih.insertItem(j, stk, false);
                        if (!res.isEmpty()) {
                            it.set(res);
                            stk = res;
                        } else {
                            it.remove();
                            break;
                        }
                    }
                }
            }
            if (!keptSeed.isEmpty()) event.getDrops().add(keptSeed);

            event.setDropChance(1); // already accounted for in this function

            te.markDirty();
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void dropEvent(LivingDropsEvent event) {
        World world0 = event.getEntity().getEntityWorld();
        if (world0.isRemote
                || event.getEntityLiving().getHealth() > 0) return;
        ItemStack weapon = getWeapon(event.getSource());
        NBTTagCompound nbt0 = TagUtil.getTagSafe(weapon);
        if (TinkerUtil.hasTrait(nbt0, getIdentifier())) {
            if (!Toggle.getToggleState(weapon, identifier)) return;
            if (nbt0.hasKey("global", 10)) {
                NBTTagCompound nbt = nbt0.getCompoundTag("global");
                Coord4D coord = Coord4D.fromNBT(nbt);
                TileEntity te = coord.TE();
                if (te == null) return;
                IItemHandler ih = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                        EnumFacing.VALUES[nbt.getByte("facing")]);
                if (ih == null) return;
                ListIterator<EntityItem> it = event.getDrops().listIterator();
                while (it.hasNext()) {
                    EntityItem enti = it.next();
                    ItemStack stk = enti.getItem();
                    for (int j = 0; j < ih.getSlots(); ++j) {
                        ItemStack res = ih.insertItem(j, stk, false);
                        if (!res.isEmpty()) {
                            enti.setItem(res);
                            stk = res;
                        } else {
                            it.remove();
                            break;
                        }
                    }
                }
                te.markDirty();
            }
        }
    }

    @SubscribeEvent
    public void onPlayerUse(PlayerInteractEvent.RightClickBlock event) {
        NBTTagCompound nbt = TagUtil.getTagSafe(event.getItemStack());
        if (event.getWorld().isRemote
                || event.isCanceled()
                || !event.getEntityPlayer().isSneaking()
                || event.getItemStack() == null
                || event.getFace() == null
                || !TinkerUtil.hasTrait(nbt, getIdentifier()))
            return;
        TileEntity te = event.getWorld().getTileEntity(event.getPos());
        if (te == null || te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                event.getFace()) == null) return;
        NBTTagCompound global = new NBTTagCompound();
        Coord4D coord = new Coord4D(event.getPos(), event.getWorld());
        coord.toNBT(global);
        global.setByte("facing", (byte) event.getFace().ordinal());
        nbt.setTag("global", global);
        event.getItemStack().setTagCompound(nbt);
        event.getEntityPlayer().sendMessage(new TextComponentTranslation(
                "msg.plustic.globalmodifier.set",
                coord.xCoord,
                coord.yCoord,
                coord.zCoord,
                coord.dimensionId));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void tooltip(ItemTooltipEvent event) {
        NBTTagCompound nbt0 = TagUtil.getTagSafe(event.getItemStack());
        if (event.isCanceled()
                || event.getItemStack() == null
                || !TinkerUtil.hasTrait(nbt0, getIdentifier())) return;
        if (nbt0.hasKey("global", 10)) {
            NBTTagCompound nbt = nbt0.getCompoundTag("global");
            event.getToolTip().add(I18n.format("tooltip.plustic.globalmodifier.info",
                    nbt.getInteger("x"),
                    nbt.getInteger("y"),
                    nbt.getInteger("z"),
                    nbt.getInteger("dim")));
        }
    }

    private ItemStack getWeapon(DamageSource source) {
        if (source instanceof EntityDamageSource) {
            Entity entity = ((EntityDamageSource) source).getTrueSource();
            if (entity instanceof EntityLivingBase)
                return ((EntityLivingBase) entity).getHeldItemMainhand();
        }
        return null;
    }
}
