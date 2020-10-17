package landmaster.plustic.modifiers.armor;

import com.google.common.collect.Maps;
import java.lang.invoke.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Throwables;

import baubles.api.*;
import baubles.api.cap.*;
import c4.conarm.common.armor.utils.*;
import c4.conarm.lib.armor.*;
import c4.conarm.lib.modifiers.*;
import c4.conarm.lib.tinkering.*;
import it.unimi.dsi.fastutil.ints.*;
import landmaster.plustic.api.Sounds;
import landmaster.plustic.config.*;
import landmaster.plustic.net.*;
import landmaster.plustic.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.energy.*;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.client.*;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.items.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.utils.*;
import tonius.simplyjetpacks.SimplyJetpacks;
import tonius.simplyjetpacks.client.handler.KeybindHandler;
import tonius.simplyjetpacks.client.model.PackModelType;
import tonius.simplyjetpacks.client.util.RenderUtils;
import tonius.simplyjetpacks.handler.SyncHandler;
import tonius.simplyjetpacks.item.Jetpack;
import tonius.simplyjetpacks.setup.ParticleType;
import tonius.simplyjetpacks.util.SJStringUtil;

/**
 * Adapted from SimplyJetpack2's code.
 * @author Landmaster
 *
 */
public class JetpackPancakeHippos extends ArmorModifierTrait {
	static {
		MinecraftForge.EVENT_BUS.register(JetpackPancakeHippos.class);
	}

	@SideOnly(Side.CLIENT)
	private static final class ClientOnly {

		static final Map<KeyBinding, JetpackSettings> keys = new HashMap<>();
		static {
			keys.put(KeybindHandler.JETPACK_ENGINE_KEY, JetpackSettings.ENGINE);
			keys.put(KeybindHandler.JETPACK_HOVER_KEY, JetpackSettings.HOVER);
			keys.put(KeybindHandler.JETPACK_EHOVER_KEY, JetpackSettings.EHOVER);
		}

	}
	
	public static enum JetpackSettings {
		ENGINE, HOVER, EHOVER;
		
		public String getNBTKey() {
			return "PlusTiC_SJ2_"+this.name();
		}
		
		public boolean isOff(ItemStack stack) {
			return TagUtil.getTagSafe(stack).hasKey(getNBTKey());
		}
		
		public void setOff(ItemStack stack, boolean off) {
			if (off) {
				TagUtil.getTagSafe(stack).setBoolean(getNBTKey(), false);
			} else {
				TagUtil.getTagSafe(stack).removeTag(getNBTKey());
			}
		}
		
		public String getStateMsgUnloc() {
			switch (this) {
			case EHOVER:
				return "chat.itemJetpack.emergencyHoverMode";
			case ENGINE:
				return "chat.itemJetpack.on";
			case HOVER:
				return "chat.itemJetpack.hoverMode";
			default:
				return "";
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onKeyInput(InputEvent.KeyInputEvent event) {
		EntityPlayer player = FMLClientHandler.instance().getClient().player;
		ItemStack chestStack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		
		Utils.getModifiers(chestStack).stream()
		.filter(trait -> trait instanceof JetpackPancakeHippos)
		.findAny().ifPresent(trait -> {
			ClientOnly.keys.forEach((key, setting) -> {
				if (key.isPressed()) {
					boolean oldState = setting.isOff(chestStack);
					if (tonius.simplyjetpacks.config.Config.enableStateMessages) {
						String unlocTemp = setting.getStateMsgUnloc();
						ITextComponent state = SJStringUtil
							.localizeNew(oldState ? "chat.enabled" : "chat.disabled");
						state.setStyle(new Style().setColor(oldState ? TextFormatting.GREEN : TextFormatting.RED));
						player.sendStatusMessage(SJStringUtil.localizeNew(unlocTemp, state), true);
					}
					PacketHandler.INSTANCE.sendToServer(new PacketSJKey(setting));
				}
			});
		});
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(receiveCanceled = true)
	public static void onOverlayRender(RenderGameOverlayEvent.Post event) {
		if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
			return;
		}
		if (Minecraft.getMinecraft().player != null) {
			if ((Minecraft.getMinecraft().currentScreen == null || tonius.simplyjetpacks.config.Config.showHUDWhileChatting && Minecraft.getMinecraft().currentScreen instanceof GuiChat) && !Minecraft.getMinecraft().gameSettings.hideGUI && !Minecraft.getMinecraft().gameSettings.showDebugInfo) {
				ItemStack chestplate = Minecraft.getMinecraft().player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
				Utils.getModifiers(chestplate).stream()
				.filter(trait -> trait instanceof JetpackPancakeHippos)
				.findAny().ifPresent(trait -> {
					List<String> info = new ArrayList<String>();
					((JetpackPancakeHippos)trait).addHUDInfo(info, chestplate, tonius.simplyjetpacks.config.Config.enableStateHUD);
					if (info.isEmpty()) {
						return;
					}
					
					GL11.glPushMatrix();
					Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
					GL11.glScaled(tonius.simplyjetpacks.config.Config.HUDScale, tonius.simplyjetpacks.config.Config.HUDScale, 1.0D);
					
					int i = 0;
					for (String s : info) {
						RenderUtils.drawStringAtHUDPosition(s, tonius.simplyjetpacks.config.Config.HUDPosition, Minecraft.getMinecraft().fontRenderer, tonius.simplyjetpacks.config.Config.HUDOffsetX, tonius.simplyjetpacks.config.Config.HUDOffsetY, tonius.simplyjetpacks.config.Config.HUDScale, 0xeeeeee, true, i);
						i++;
					}
					
					GL11.glPopMatrix();
				});
			}
		}
	}
	
	private static final Set<RenderLivingBase<?>> layerAdded = Collections.newSetFromMap(new WeakHashMap<>());
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void renderJetpack(RenderLivingEvent.Pre<?> event) {
		if (!layerAdded.contains(event.getRenderer())) {
			layerAdded.add(event.getRenderer());
			event.getRenderer().addLayer(new LayerBipedArmor(event.getRenderer()) {
				private Optional<Jetpack> jetpackOpt = Optional.empty();
				
				@Override
				public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
					jetpackOpt = Utils.getModifiers(event.getEntity().getItemStackFromSlot(EntityEquipmentSlot.CHEST)).stream()
							.filter(trait -> trait instanceof JetpackPancakeHippos)
							.findAny()
							.map(modifier -> ((JetpackPancakeHippos)modifier).jetpack);
					jetpackOpt.ifPresent(jetpack -> this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.CHEST));
				}
				
				@Override
				protected ModelBiped getArmorModelHook(EntityLivingBase entity, ItemStack itemStack, EntityEquipmentSlot slot, ModelBiped model) {
					if (slot == EntityEquipmentSlot.CHEST) {
						return jetpackOpt
								.filter(jetpack -> tonius.simplyjetpacks.config.Config.enableArmor3DModels)
								.map(jetpack -> RenderUtils.getArmorModel(jetpack, entity))
								.orElse(super.getArmorModelHook(entity, itemStack, slot, model));
					}
					return super.getArmorModelHook(entity, itemStack, slot, model);
				}
				
				@Override
				public ResourceLocation getArmorResource(Entity entity, ItemStack stack, EntityEquipmentSlot slot, String type) {
					if (slot == EntityEquipmentSlot.CHEST) {
						return jetpackOpt
								.map(jetpack -> new ResourceLocation(SimplyJetpacks.RESOURCE_PREFIX + "textures/armor/" + jetpack.getBaseName() + (tonius.simplyjetpacks.config.Config.enableArmor3DModels || jetpack.armorModel == PackModelType.FLAT ? "" : ".flat") + ".png"))
								.orElse(TextureManager.RESOURCE_LOCATION_EMPTY);
					}
					return TextureManager.RESOURCE_LOCATION_EMPTY;
				}
			});
		}
	}
	
	private static ParticleType lastJetpackState0 = null;
	private static boolean wearingJetpack0 = false;
	private static boolean sprintKeyCheck0 = false;
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent evt) {
		if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().world == null) {
			return;
		}
		if (evt.phase == TickEvent.Phase.START) {
			ItemStack stack = Minecraft.getMinecraft().player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
			Optional<Jetpack> jetpackOpt = Utils.getModifiers(stack).stream()
					.filter(trait -> trait instanceof JetpackPancakeHippos)
					.findAny()
					.map(modifier -> ((JetpackPancakeHippos)modifier).jetpack);
			ParticleType particleType = jetpackOpt.map(jetpack -> getParticleType(Minecraft.getMinecraft().player, stack, jetpack)).orElse(null);
			wearingJetpack0 = jetpackOpt.isPresent();
			if (lastJetpackState0 != particleType) {
				lastJetpackState0 = particleType;
				processJetpackUpdate(Minecraft.getMinecraft().player.getEntityId(), particleType);
			}
			//System.out.println(particleType);
		} else {
			if (!Minecraft.getMinecraft().isGamePaused()) {
				IntIterator it = jetpackState.keySet().iterator();
				while (it.hasNext()) {
					int cur = it.nextInt();
					Entity entity = Minecraft.getMinecraft().world.getEntityByID(cur);
					if (entity == null || !(entity instanceof EntityLivingBase) || entity.dimension != Minecraft.getMinecraft().player.dimension) {
						it.remove();
					} else {
						ParticleType particle = jetpackState.get(cur);
						if (particle != null) {
							if (entity.isInWater() && particle != ParticleType.NONE) {
								particle = ParticleType.BUBBLE;
							}
							SimplyJetpacks.proxy.showJetpackParticles(Minecraft.getMinecraft().world, (EntityLivingBase)entity, particle);
							if (tonius.simplyjetpacks.config.Config.jetpackSounds && !Sounds.PTSoundJetpack.isPlayingFor(entity.getEntityId())) {
								Minecraft.getMinecraft().getSoundHandler().playSound(new Sounds.PTSoundJetpack((EntityLivingBase)entity));
							}
						} else {
							it.remove();
						}
					}
				}
			}
			
			if (sprintKeyCheck0 && Minecraft.getMinecraft().player.movementInput.moveForward < 1.0F) {
				sprintKeyCheck0 = false;
			}
			
			if (!tonius.simplyjetpacks.config.Config.doubleTapSprintInAir
					|| !wearingJetpack0
					|| Minecraft.getMinecraft().player.onGround
					|| Minecraft.getMinecraft().player.isSprinting()
					|| Minecraft.getMinecraft().player.isHandActive()
					|| Minecraft.getMinecraft().player.isPotionActive(MobEffects.POISON)) {
				return;
			}
			
			if (!sprintKeyCheck0
					&& Minecraft.getMinecraft().player.movementInput.moveForward >= 1.0F
					&& !Minecraft.getMinecraft().player.collidedHorizontally
					&& (Minecraft.getMinecraft().player.getFoodStats().getFoodLevel() > 6.0F || Minecraft.getMinecraft().player.capabilities.allowFlying)) {
				if (Minecraft.getMinecraft().player.sprintToggleTimer <= 0 && !Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown()) {
					Minecraft.getMinecraft().player.sprintToggleTimer = 7;
					sprintKeyCheck0 = true;
				} else {
					Minecraft.getMinecraft().player.setSprinting(true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onClientDisconnectedFromServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent evt) {
		Sounds.PTSoundJetpack.clearPlayingFor();
	}
	
	public void addHUDInfo(List<String> list, ItemStack stack, boolean showState) {
		if (showState) {
			list.add(this.getHUDStatesInfo(stack));
		}
	}
	public String getHUDStatesInfo(ItemStack stack) {
		return SJStringUtil.getHUDStateText(
			!JetpackSettings.ENGINE.isOff(stack),
			!JetpackSettings.HOVER.isOff(stack),
			null,
			!JetpackSettings.EHOVER.isOff(stack));
	}
	
	public static final Map<Jetpack, JetpackPancakeHippos> jetpackpancakehippos
	= Arrays.stream(Jetpack.values())
	.filter(jetpack -> !jetpack.isArmored || jetpack == Jetpack.JETPLATE_TE_5 /* special case for jetplate */)
	.collect(Collectors.toMap(Function.identity(), JetpackPancakeHippos::new, (a,b) -> b, () -> new EnumMap<>(Jetpack.class)));
	
	public final Jetpack jetpack;
	
	public JetpackPancakeHippos(Jetpack jetpack) {
		super("jetpackpancakehippos_"+jetpack.name().toLowerCase(Locale.US), 0x60b2ff);
		this.jetpack = jetpack;
		//addAspects(new ModifierAspect.DataAspect(this), new ModifierAspect.SingleAspect(this), ModifierAspect.freeModifier);
	}
	
	@Override
	public boolean canApplyTogether(IToolMod otherModifier) {
		return super.canApplyTogether(otherModifier) && !(otherModifier instanceof JetpackPancakeHippos);
	}
	
	@Override
	public boolean canApplyCustom(ItemStack stack) {
		//System.out.println("STACK: "+stack);
		return EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.CHEST && super.canApplyCustom(stack);
	}
	
	
	/*@Override
	public Optional<RecipeMatch.Match> matches(NonNullList<ItemStack> stacks) {
		System.out.println("Candidates: "+stacks);
		System.out.println("Matchers: "+items);
		Optional<RecipeMatch.Match> res = super.matches(stacks);
		System.out.println("Verdict: "+res);
		return res;
	}*/
	
	private static final Int2ObjectMap<ParticleType> lastJetpackState = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>()), jetpackState = new Int2ObjectOpenHashMap<>();
	
	public static void processJetpackUpdate(int entityId, ParticleType particleType) {
		if (particleType != null) {
			jetpackState.put(entityId, particleType);
		} else {
			jetpackState.remove(entityId);
		}
	}
	public static Int2ObjectMap<ParticleType> getJetpackStates() { return jetpackState; }
	
	@Override
	public void onArmorTick(ItemStack armor, World world, EntityPlayer player) {
		this.flyUser(player, armor, false);
	}
	
	@SubscribeEvent
	public static void onLivingTick(LivingEvent.LivingUpdateEvent evt) {
		if (!evt.getEntityLiving().world.isRemote) {
			ParticleType jetpackState = null;
			ItemStack armor = evt.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.CHEST);
			Jetpack jetpack = null;
			if (armor.getItem() instanceof TinkersArmor) {
				jetpack = Utils.getModifiers(armor).stream()
					.filter(trait -> trait instanceof JetpackPancakeHippos)
					.findAny()
					.map(trait -> ((JetpackPancakeHippos)trait).jetpack)
					.orElse(null);
			}
			if (jetpack != null) {
				jetpackState = getParticleType(evt.getEntityLiving(), armor, jetpack);
			}
			if (jetpackState != lastJetpackState.get(evt.getEntityLiving().getEntityId())) {
				if (jetpackState == null) {
					lastJetpackState.remove(evt.getEntityLiving().getEntityId());
				} else {
					lastJetpackState.put(evt.getEntityLiving().getEntityId(), jetpackState);
				}
				PacketHandler.INSTANCE.sendToAllAround(new PacketSJSyncParticles(evt.getEntityLiving().getEntityId(), jetpackState != null ? jetpackState.ordinal() : -1), new NetworkRegistry.TargetPoint(evt.getEntityLiving().dimension, evt.getEntityLiving().posX, evt.getEntityLiving().posY, evt.getEntityLiving().posZ, 256));
			} else if (jetpack != null && evt.getEntityLiving().world.getTotalWorldTime() % 160L == 0) {
				PacketHandler.INSTANCE.sendToAllAround(new PacketSJSyncParticles(evt.getEntityLiving().getEntityId(), jetpackState != null ? jetpackState.ordinal() : -1), new NetworkRegistry.TargetPoint(evt.getEntityLiving().dimension, evt.getEntityLiving().posX, evt.getEntityLiving().posY, evt.getEntityLiving().posZ, 256));
			}
			
			if (evt.getEntityLiving().world.getTotalWorldTime() % 200L == 0) {
				IntIterator itr = lastJetpackState.keySet().iterator();
				while (itr.hasNext()) {
					int entityId = itr.nextInt();
					if (evt.getEntityLiving().world.getEntityByID(entityId) == null) {
						itr.remove();
					}
				}
			}
		}
	}
	
	public static ParticleType getParticleType(EntityLivingBase user, ItemStack stack, Jetpack jetpack) {
		boolean flyKeyDown = SyncHandler.isFlyKeyDown(user);
		if (!JetpackSettings.ENGINE.isOff(stack) && (searchStorage(user, 1).isPresent() || !jetpack.usesEnergy) && (flyKeyDown || !JetpackSettings.HOVER.isOff(stack) && !user.onGround && user.motionY < 0)) {
			return jetpack.defaultParticleType;
		}
		return null;
	}
	
	
	protected int getFuelUsage(ItemStack stack) {
		if (jetpack.getBaseName().contains("enderium")) {
			return (int)Math.round(jetpack.getEnergyUsage() * 0.8);
		}
		return jetpack.getEnergyUsage();
	}
	
	protected static Optional<IEnergyStorage> searchStorage(EntityLivingBase elb, int fuelUsage) {
		Optional<IEnergyStorage> storage = Optional.empty();
		if (elb.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
			IItemHandler cap = elb.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			for (int i=0; i<cap.getSlots(); ++i) {
				ItemStack energyStack = cap.getStackInSlot(i);
				storage = Optional.ofNullable(energyStack.getCapability(CapabilityEnergy.ENERGY, null))
						.filter(raw -> raw.extractEnergy(fuelUsage, true) >= fuelUsage);
				if (storage.isPresent()) break;
			}
		}
		if (!storage.isPresent() && elb instanceof EntityPlayer) {
			IBaublesItemHandler ib = BaublesApi.getBaublesHandler((EntityPlayer)elb);
			for (int i=0; i<ib.getSlots(); ++i) {
				ItemStack energyStack = ib.getStackInSlot(i);
				storage = Optional.ofNullable(energyStack.getCapability(CapabilityEnergy.ENERGY, null))
						.filter(raw -> raw.extractEnergy(fuelUsage, true) >= fuelUsage);
				if (storage.isPresent()) break;
			}
		}
		return storage;
	}
	
	protected void flyUser(EntityPlayer user, ItemStack stack, boolean force) {
		int fuelUsage = (int) (user.isSprinting() ? Math.round(this.getFuelUsage(stack) * jetpack.sprintEnergyModifier) : this.getFuelUsage(stack));
		
		Optional<IEnergyStorage> storage = searchStorage(user, fuelUsage);
		
		if (!JetpackSettings.ENGINE.isOff(stack)) {
			boolean hoverMode = !JetpackSettings.HOVER.isOff(stack);
			double hoverSpeed = tonius.simplyjetpacks.config.Config.invertHoverSneakingBehavior == SyncHandler.isDescendKeyDown(user) ? jetpack.speedVerticalHoverSlow : jetpack.speedVerticalHover;
			boolean flyKeyDown = force || SyncHandler.isFlyKeyDown(user);
			boolean descendKeyDown = SyncHandler.isDescendKeyDown(user);
			double currentAccel = jetpack.accelVertical * (user.motionY < 0.3D ? 2.5D : 1.0D);
			double currentSpeedVertical = jetpack.speedVertical * (user.isInWater() ? 0.4D : 1.0D);
			
			//System.out.println(flyKeyDown + " " + descendKeyDown);
			
			if (flyKeyDown || hoverMode && !user.onGround) {
				if (jetpack.usesEnergy && storage.isPresent()) {
					storage.get().extractEnergy(fuelUsage, false);
				}
				
				if (!jetpack.usesEnergy || storage.map(IEnergyStorage::getEnergyStored).filter(e -> e>0).isPresent()) {
					if (flyKeyDown) {
						if (!hoverMode) {
							user.motionY = Math.min(user.motionY + currentAccel, currentSpeedVertical);
						} else {
							if (descendKeyDown) {
								user.motionY = Math.min(user.motionY + currentAccel, -jetpack.speedVerticalHoverSlow);
							} else {
								user.motionY = Math.min(user.motionY + currentAccel, jetpack.speedVerticalHover);
							}
						}
					} else {
						user.motionY = Math.min(user.motionY + currentAccel, -hoverSpeed);
					}
					
					float speedSideways = (float) (user.isSneaking() ? jetpack.speedSideways * 0.5F : jetpack.speedSideways);
					float speedForward = (float) (user.isSprinting() ? speedSideways * jetpack.sprintSpeedModifier : speedSideways);
					if (SyncHandler.isForwardKeyDown(user)) {
						user.moveRelative(0, 0, speedForward, speedForward);
					}
					if (SyncHandler.isBackwardKeyDown(user)) {
						user.moveRelative(0, 0, -speedSideways, speedSideways * 0.8F);
					}
					if (SyncHandler.isLeftKeyDown(user)) {
						user.moveRelative(speedSideways, 0, 0, speedSideways);
					}
					if (SyncHandler.isRightKeyDown(user)) {
						user.moveRelative(-speedSideways, 0, 0, speedSideways);
					}
					
					if (!user.world.isRemote) {
						user.fallDistance = 0.0F;
						
						if (user instanceof EntityPlayerMP) {
							((EntityPlayerMP) user).connection.floatingTickCount = 0;
						}
					}
				}
			}
		}
		
		//Emergency Hover
		if (!user.world.isRemote && jetpack.emergencyHoverMode && !JetpackSettings.EHOVER.isOff(stack)) {
			if ((!jetpack.usesEnergy || storage.map(IEnergyStorage::getEnergyStored).filter(e -> e>0).isPresent()) && (JetpackSettings.HOVER.isOff(stack) || JetpackSettings.ENGINE.isOff(stack))) {
				if (user.posY < -5) {
					this.doEHover(stack, user);
				} else {
					if (!user.capabilities.isCreativeMode && user.fallDistance - 1.2F >= user.getHealth()) {
						for (int j = 0; j <= 16; j++) {
							int x = Math.round((float) user.posX - 0.5F);
							int y = Math.round((float) user.posY) - j;
							int z = Math.round((float) user.posZ - 0.5F);
							if (!user.world.isAirBlock(new BlockPos(x, y, z))) {
								this.doEHover(stack, user);
								break;
							}
						}
					}
				}
			}
		}
	}
	
	protected void doEHover(ItemStack armor, EntityPlayer user) {
		JetpackSettings.ENGINE.setOff(armor, false);
		JetpackSettings.HOVER.setOff(armor, false);
		ITextComponent msg = SJStringUtil.localizeNew("chat.itemJetpack.emergencyHoverMode.msg");
		msg.setStyle(new Style().setColor(TextFormatting.RED));
		user.sendStatusMessage(msg, true);
	}
	
	@Override
	public String getLocalizedName() {
		return Util.translateFormatted("modifier.jetpackpancakehippos.name",
				I18n.format(jetpack.unlocalisedName + ".name"));
	}
	
	@Override
	public String getLocalizedDesc() {
		return Util.translate(LOC_Desc, "jetpackpancakehippos");
	}
	
	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		super.applyEffect(rootCompound, modifierTag);
		ArmorNBT data = ArmorTagUtil.getArmorStats(rootCompound);
		data.durability += jetpack.energyCapacity * Config.jetpackDurabilityBonusScale;
		TagUtil.setToolTag(rootCompound, data.get());
	}
}
