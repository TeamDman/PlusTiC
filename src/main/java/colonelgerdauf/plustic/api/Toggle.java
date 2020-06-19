package colonelgerdauf.plustic.api;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import org.lwjgl.opengl.*;

import c4.conarm.lib.capabilities.*;
import it.unimi.dsi.fastutil.booleans.*;
import it.unimi.dsi.fastutil.objects.*;
import colonelgerdauf.plustic.*;
import colonelgerdauf.plustic.net.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.utils.*;

/**
 * 
 * Class for toggleable modifiers
 * @author colonelgerdauf
 *
 */
public class Toggle {
	public static final ResourceLocation TOGGLEARMOR_CAPLOCATION = new ResourceLocation(ModInfo.MODID, "togglearmor_cap");
	
	private static final Set<String> toggleable = new HashSet<>();
	private static final Map<String, String> toggleableAlias = new HashMap<>();
	
	@CapabilityInject(IToggleArmor.class)
	private static Capability<IToggleArmor> TOGGLE_ARMOR = null;
	
	static {
		MinecraftForge.EVENT_BUS.register(Toggle.class);
		CapabilityManager.INSTANCE.register(IToggleArmor.class, new Capability.IStorage<IToggleArmor>() {
			@Override
			public NBTBase writeNBT(Capability<IToggleArmor> capability, IToggleArmor instance, EnumFacing side) {
				NBTTagList nbt = new NBTTagList();
				for (String disabled : instance.getDisabled()) {
					nbt.appendTag(new NBTTagString(disabled));
				}
				return nbt;
			}

			@Override
			public void readNBT(Capability<IToggleArmor> capability, IToggleArmor instance, EnumFacing side,
					NBTBase nbt) {
				for (NBTBase elem: (NBTTagList)nbt) {
					instance.getDisabled().add(((NBTTagString)elem).getString());
				}
			}
		}, ToggleArmor::new);
	}
	
	public static final String ARMOR_FLAG = "\\";
	
	public static interface IToggleArmor {
		Set<String> getDisabled();
	}
	private static class ToggleArmor implements IToggleArmor {
		private Set<String> disableds = new ObjectOpenHashSet<>();
		
		@Override
		public Set<String> getDisabled() {
			return disableds;
		}
	}
	private static class ToggleArmorProvider implements ICapabilitySerializable<NBTTagList> {
		@CapabilityInject(IToggleArmor.class)
		private static Capability<IToggleArmor> TOGGLE_ARMOR = null;
		
		private final ToggleArmor cap;
		public ToggleArmorProvider() { this.cap = new ToggleArmor(); }
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == TOGGLE_ARMOR;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if (capability == TOGGLE_ARMOR) {
				return (T)cap;
			}
			return null;
		}

		@Override
		public NBTTagList serializeNBT() {
			return (NBTTagList)TOGGLE_ARMOR.writeNBT(cap, null);
		}

		@Override
		public void deserializeNBT(NBTTagList nbt) {
			TOGGLE_ARMOR.readNBT(cap, null, nbt);
		}
		
	};
	
	@SubscribeEvent
	public static void addToggleArmorCapability(AttachCapabilitiesEvent<Entity> event) {
		if (Loader.isModLoaded("conarm") && event.getObject() instanceof EntityPlayer) {
			event.addCapability(TOGGLEARMOR_CAPLOCATION, new ToggleArmorProvider());
		}
	}
	
	public static void addToggleable(String trait) {
		//System.out.println(toggleable);
		toggleable.add(trait);
	}
	public static void addToggleableAlias(String trait, String alias) {
		toggleableAlias.put(alias, trait);
	}
	public static String unwrapAlias(String alias) {
		String res = alias;
		while (toggleableAlias.containsKey(res)) {
			res = toggleableAlias.get(alias);
		}
		return res;
	}
	public static String rawIdentifier(String identifier) {
		if (identifier.startsWith(ARMOR_FLAG)) {
			return identifier.substring(ARMOR_FLAG.length());
		}
		return identifier;
	}
	
	public static class Gui extends GuiScreen {
		@CapabilityInject(IToggleArmor.class)
		private static Capability<IToggleArmor> TOGGLE_ARMOR = null;
		
		public static final int OPTIONS_PER_PAGE = 6;
		
		private final ResourceLocation background = new ResourceLocation(ModInfo.MODID, "textures/gui/toggle.png");
		
		private int page;
		private EntityPlayer player;
		private List<String> identifiers;
		private BooleanList enableds;
		
		private int xSize = 176;
		private int ySize = 128;
		private int guiLeft, guiTop;
		
		public Gui(EntityPlayer player, Collection<String> armorAbilities) {
			this.player = player;
			page = 0;
			Object2BooleanMap<String> temp = new Object2BooleanLinkedOpenHashMap<>();
			NBTTagCompound nbt = TagUtil.getTagSafe(this.player.getHeldItemMainhand());
			NBTTagList traits = TagUtil.getTraitsTagList(nbt);
			for (int i=0; i<traits.tagCount(); ++i) {
				String identifier = unwrapAlias(traits.getStringTagAt(i));
				if (toggleable.contains(identifier)) {
					temp.put(identifier, getToggleState(nbt, identifier));
				}
			}
			identifiers = new ArrayList<>(temp.keySet());
			enableds = new BooleanArrayList(temp.values());
			
			if (player.hasCapability(TOGGLE_ARMOR, null)) {
				IToggleArmor cap = player.getCapability(TOGGLE_ARMOR, null);
				Set<String> disabled = cap.getDisabled();
				for (String ability: armorAbilities) {
					identifiers.add(ARMOR_FLAG+ability);
					enableds.add(!disabled.contains(ability));
				}
			}
		}
		
		@Override
		public void initGui() {
			super.initGui();
			guiLeft = (width - xSize) / 2;
	        guiTop = (height - ySize) / 2;
		}
		
		@Override
		public void drawScreen(int mouseX, int mouseY, float partialTicks) {
			drawDefaultBackground();
			
			super.drawScreen(mouseX, mouseY, partialTicks);
			
			GlStateManager.color(1, 1, 1, 1);
			GlStateManager.enableAlpha();
			GL11.glEnable(GL11.GL_BLEND);
			
			// draw background proper
			mc.renderEngine.bindTexture(background);
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
			// draw header
			fontRenderer.drawString(I18n.format("gui.header.toggle"), guiLeft+5, guiTop+5, 0xFFFFFF);
			
			// draw list background
			mc.renderEngine.bindTexture(background);
			for (int i=0; i<Math.min(OPTIONS_PER_PAGE, identifiers.size() - page*OPTIONS_PER_PAGE); ++i) {
				drawTexturedModalRect(guiLeft+7, guiTop+18*(i+1), 0, 128, 152, 16);
			}
			
			// draw arrows
			mc.renderEngine.bindTexture(background);
			if (page > 0) drawTexturedModalRect(guiLeft+160, guiTop+18, 176, 12, 12, 21);
			if (page < identifiers.size() / OPTIONS_PER_PAGE - (identifiers.size() % OPTIONS_PER_PAGE == 0 ? 1 : 0)) drawTexturedModalRect(guiLeft+160, guiTop+100, 176+12, 12, 12, 21);
			
			// draw items
			for (int i=page*OPTIONS_PER_PAGE; i<Math.min((page+1)*OPTIONS_PER_PAGE, identifiers.size()); ++i) {
				String identifier = identifiers.get(i);
				String rawIdentifier = rawIdentifier(identifier);
				boolean isArmor = identifier.startsWith(ARMOR_FLAG);
				boolean enabled = enableds.get(i);
				String locName = I18n.format(isArmor ? "gui.toggle.armor" : "gui.toggle.tool", I18n.format("modifier."+rawIdentifier+".name"));
				fontRenderer.drawString(locName, guiLeft+10, guiTop+18*(i%OPTIONS_PER_PAGE+1)+3, 0xFFFFFF);
				mc.renderEngine.bindTexture(background);
				drawTexturedModalRect(guiLeft+130, guiTop+18*(i%OPTIONS_PER_PAGE+1)+1,176+(enabled ? 0 : 12), 0, 12, 12);
				if (isPointInRegion(7, 18*(i%OPTIONS_PER_PAGE+1), 158, 16, mouseX, mouseY)) {
					drawHoveringText(Arrays.asList(I18n.format("tooltip.plustic.toggle.info"),
							I18n.format("tooltip.plustic.toggle.state."+enabled, locName)
							), mouseX, mouseY);
				}
			}
		}
		
		@Override
		public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
			super.mouseClicked(mouseX, mouseY, mouseButton);
			for (int i=0; i<Math.min(OPTIONS_PER_PAGE, identifiers.size() - page*OPTIONS_PER_PAGE); ++i) {
				if (isPointInRegion(7, 18*(i+1), 152, 16, mouseX, mouseY)) {
					String identifier = identifiers.get(page*OPTIONS_PER_PAGE+i);
					PacketHandler.INSTANCE.sendToServer(new PacketHandleToggleGui(identifier));
					return;
				}
			}
			if (isPointInRegion(160, 18, 12, 21, mouseX, mouseY)) {
				page = Math.max(page-1, 0);
			} else if (isPointInRegion(160, 100, 12, 21, mouseX, mouseY)) {
				page = Math.min(page+1, identifiers.size() / OPTIONS_PER_PAGE - (identifiers.size() % OPTIONS_PER_PAGE == 0 ? 1 : 0));
			}
		}
		
		protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
	        int i = this.guiLeft;
	        int j = this.guiTop;
	        pointX = pointX - i;
	        pointY = pointY - j;
	        return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
		}
		
		public void update(String identifier, boolean value) {
			int ind = identifiers.indexOf(identifier);
			if (ind>=0) enableds.set(ind, value);
		}
	}
	
	@SubscribeEvent
	public static void copyOnDeath(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
		IToggleArmor oldCap = event.getOriginal().getCapability(TOGGLE_ARMOR, null);
		IToggleArmor newCap = event.getEntityPlayer().getCapability(TOGGLE_ARMOR, null);
		if (oldCap != null && newCap != null) {
			newCap.getDisabled().clear();
			newCap.getDisabled().addAll(oldCap.getDisabled());
		}
	}
	
	@SubscribeEvent
	public static void onPlayerRespawn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event) {
		syncArmorToClient(event);
	}
	
	@SubscribeEvent
	public static void onPlayerLogin(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
		syncArmorToClient(event);
	}
	
	@SubscribeEvent
	public static void changeDim(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event) {
		syncArmorToClient(event);
	}
	
	protected static void syncArmorToClient(net.minecraftforge.fml.common.gameevent.PlayerEvent event) {
		IToggleArmor cap = event.player.getCapability(TOGGLE_ARMOR, null);
		if (cap != null && !event.player.world.isRemote) {
			for (String disabled: cap.getDisabled()) {
				PacketHandler.INSTANCE.sendTo(new PacketUpdateToggleGui(ARMOR_FLAG+disabled, false), (EntityPlayerMP)event.player);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void testAndToggle(InputEvent.KeyInputEvent event) {
		if (PlusTiC.proxy.isControlPressed("toggle_gui")) {
			PacketHandler.INSTANCE.sendToServer(new PacketRequestToggleGui());
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void tooltip(ItemTooltipEvent event) {
		if (event.getItemStack() == null
				|| !(event.getItemStack().getItem() instanceof ToolCore))
			return;
		NBTTagCompound nbt = TagUtil.getTagSafe(event.getItemStack());
		NBTTagList traits = TagUtil.getTraitsTagList(nbt);
		NBTTagCompound toggle = TagUtil.getTagSafe(nbt, "PlusTiC_toggle");
		for (int i=0; i<traits.tagCount(); ++i) {
			String identifier = traits.getStringTagAt(i);
			if (toggleable.contains(identifier)) {
				boolean enabled = !toggle.hasKey(identifier);
				event.getToolTip().add(I18n.format("tooltip.plustic.toggle.state."+enabled,
									TinkerRegistry.getModifier(identifier).getLocalizedName()));
			}
		}
	}
	
	public static Stream<String> getToggleableArmor(EntityPlayer player) {
		//System.out.println(Arrays.toString(toggleable.toArray()));
		return Loader.isModLoaded("conarm") ? Optional.ofNullable(ArmorAbilityHandler.getArmorAbilitiesData(player))
				.map(ArmorAbilityHandler.IArmorAbilities::getAbilityMap)
				.map(Map::keySet)
				//.map(set -> { System.out.println(Arrays.toString(set.toArray())); return set; })
				.map(set -> set.stream().filter(str -> toggleable.contains(ARMOR_FLAG+str)))
				.orElse(Stream.empty()) : Stream.empty();
	}
	
	public static boolean canToggle(ItemStack is) {
		return canToggle(TagUtil.getTagSafe(is));
	}
	
	public static boolean canToggle(NBTTagCompound nbt) {
		NBTTagList traits = TagUtil.getTraitsTagList(nbt);
		for (int i=0; i<traits.tagCount(); ++i) {
			if (toggleable.contains(traits.getStringTagAt(i))) return true;
		}
		return false;
	}
	
	/**
	 * Check whether a modifier/trait is enabled.
	 * @param is the itemstack
	 * @param identifier the identifier for the modifier/trait
	 * @return whether the modifier/trait is enabled
	 */
	public static boolean getToggleState(ItemStack is, String identifier) {
		NBTTagCompound nbt = TagUtil.getTagSafe(is);
		return getToggleState(nbt, identifier);
	}
	
	/**
	 * Check whether a modifier/trait is enabled.
	 * @see #getToggleState(ItemStack, String)
	 */
	public static boolean getToggleState(NBTTagCompound nbt, String identifier) {
		NBTTagCompound toggle = TagUtil.getTagSafe(nbt, "PlusTiC_toggle");
		return (!toggleable.contains(identifier) || !toggle.hasKey(identifier));
	}
	
	/**
	 * Set whether the modifier/trait is enabled.
	 */
	public static void setToggleState(NBTTagCompound nbt, String identifier, boolean value) {
		if (!toggleable.contains(identifier)) return;
		NBTTagCompound toggle = TagUtil.getTagSafe(nbt, "PlusTiC_toggle");
		if (value) {
			toggle.removeTag(identifier);
		} else {
			toggle.setBoolean(identifier, false);
		}
		nbt.setTag("PlusTiC_toggle", toggle);
	}
}
