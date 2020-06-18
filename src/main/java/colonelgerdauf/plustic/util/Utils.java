package landmaster.plustic.util;

import java.lang.invoke.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.Optional;
import java.util.function.*;

import javax.annotation.*;

import org.apache.commons.lang3.*;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Throwables;

import landmaster.plustic.*;
import landmaster.plustic.api.*;
import landmaster.plustic.block.*;
import landmaster.plustic.fluids.*;
import net.darkhax.tesla.capability.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraftforge.energy.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraftforge.oredict.*;
import slimeknights.tconstruct.smeltery.block.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.smeltery.*;
import slimeknights.tconstruct.library.utils.TagUtil;

public class Utils {
	private static final Map<String, Material> tinkerMaterials;
	
	static {
		try {
			Field temp = TinkerRegistry.class.getDeclaredField("materials");
			temp.setAccessible(true);
			tinkerMaterials = (Map<String, Material>) MethodHandles.lookup().unreflectGetter(temp).invokeExact();
		} catch (Throwable e) {
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}
	
	private static final Map<String, ModContainer> tinkerMaterialRegisteredByMod;
	
	static {
		try {
			Field temp = TinkerRegistry.class.getDeclaredField("materialRegisteredByMod");
			temp.setAccessible(true);
			tinkerMaterialRegisteredByMod = (Map<String, ModContainer>) MethodHandles.lookup().unreflectGetter(temp)
					.invokeExact();
		} catch (Throwable e) {
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}
	
	private static final List<ICastingRecipe> tableCastingRecipes;
	static {
		try {
			Field temp = TinkerRegistry.class.getDeclaredField("tableCastRegistry");
			temp.setAccessible(true);
			tableCastingRecipes = (List<ICastingRecipe>) MethodHandles.lookup().unreflectGetter(temp).invokeExact();
		} catch (Throwable e) {
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}
	
	public static void forceOut(String material) {
		if (tinkerMaterials.remove(material) != null) {
			PlusTiC.log.info(String.format("Forcing out material %s", material));
		}
	}
	
	public static void forceOutModsMaterial(String material, String... anyOfTheseModids) {
		Optional.ofNullable(tinkerMaterialRegisteredByMod.get(material))
				.filter(cont -> ArrayUtils.contains(anyOfTheseModids, cont.getModId()))
				.ifPresent(cont -> forceOut(material));
	}
	
	public static void displaceTableCastingRecipe(ICastingRecipe recipe) {
		if (tableCastingRecipes.remove(recipe)) {
			tableCastingRecipes.add(recipe);
		}
	}
	
	public static void addTableCastingRecipeDirectly(ICastingRecipe recipe) {
		tableCastingRecipes.add(recipe);
	}
	
	/**
	 * Pushes a material into a lower priority.
	 * 
	 * @param displace
	 *            the identifier of the material to be pushed
	 */
	public static void displace(String displace) {
		Material displaced = tinkerMaterials.remove(displace);
		if (displaced != null) tinkerMaterials.put(displace, displaced);
	}
	
	public static boolean matchesOre(ItemStack is, String od) {
		return OreDictionary.doesOreNameExist(od) && !is.isEmpty()
				&& ArrayUtils.contains(OreDictionary.getOreIDs(is), OreDictionary.getOreID(od));
	}
	
	public static AxisAlignedBB AABBfromVecs(Vec3d v1, Vec3d v2) {
		return new AxisAlignedBB(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z);
	}
	
	/**
	 * Based on
	 * {@link slimeknights.tconstruct.library.utils.EntityUtil#raytraceEntityPlayerLook(EntityPlayer, float)},
	 * except with a predicate to filter out unwanted entities.
	 */
	public static RayTraceResult raytraceEntityPlayerLookWithPred(EntityPlayer player, float range,
			Predicate<? super Entity> pred) {
		Vec3d eye = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ); // Entity.getPositionEyes
		Vec3d look = player.getLook(1.0f);
		
		return raytraceEntityWithPred(player, eye, look, range, true, pred);
	}
	
	/**
	 * Based on
	 * {@link slimeknights.tconstruct.library.utils.EntityUtil#raytraceEntity(Entity, Vec3d, Vec3d, double, boolean)},
	 * except with a predicate to filter out unwanted entities.
	 */
	public static RayTraceResult raytraceEntityWithPred(Entity entity, Vec3d start, Vec3d look, double range,
			boolean ignoreCanBeCollidedWith, Predicate<? super Entity> pred) {
		// Vec3 look = entity.getLook(partialTicks);
		Vec3d direction = start.add(look.x * range, look.y * range, look.z * range);
		
		// Vec3 direction = vec3.addVector(vec31.x * d0, vec31.y * d0, vec31.z *
		// d0);
		Entity pointedEntity = null;
		Vec3d hit = null;
		AxisAlignedBB bb = entity.getEntityBoundingBox().expand(look.x * range, look.y * range, look.z * range)
				.expand(1, 1, 1);
		List<Entity> entitiesInArea = entity.getEntityWorld().getEntitiesInAABBexcluding(entity, bb,
				EntitySelectors.NOT_SPECTATING.and(pred)::test);
		double range2 = range; // range to the current candidate. Used to find
								// the closest entity.
		
		for (Entity candidate : entitiesInArea) {
			if (ignoreCanBeCollidedWith || candidate.canBeCollidedWith()) {
				// does our vector go through the entity?
				double colBorder = candidate.getCollisionBorderSize();
				AxisAlignedBB entityBB = candidate.getEntityBoundingBox().expand(colBorder, colBorder, colBorder);
				RayTraceResult movingobjectposition = entityBB.calculateIntercept(start, direction);
				
				// needs special casing: vector starts inside the entity
				if (entityBB.contains(start)) {
					if (0.0D < range2 || range2 == 0.0D) {
						pointedEntity = candidate;
						hit = movingobjectposition == null ? start : movingobjectposition.hitVec;
						range2 = 0.0D;
					}
				} else if (movingobjectposition != null) {
					double dist = start.distanceTo(movingobjectposition.hitVec);
					
					if (dist < range2 || range2 == 0.0D) {
						if (candidate == entity.getRidingEntity() && !entity.canRiderInteract()) {
							if (range2 == 0.0D) {
								pointedEntity = candidate;
								hit = movingobjectposition.hitVec;
							}
						} else {
							pointedEntity = candidate;
							hit = movingobjectposition.hitVec;
							range2 = dist;
						}
					}
				}
			}
		}
		
		if (pointedEntity != null && range2 < range) {
			return new RayTraceResult(pointedEntity, hit);
		}
		return null;
	}
	
	public static void addModifierItem(Modifier modifier, String modid, String name) {
		addModifierItem(modifier, modid, name, 0);
	}
	
	public static void addModifierItem(Modifier modifier, String modid, String name, int meta) {
		addModifierItem(modifier, modid, name, meta, 1, 1);
	}
	
	public static void addModifierItem(Modifier modifier, String modid, String name, int meta, int needed,
			int matched) {
		if (modifier == null)
			return;
		ItemStack is = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(modid, name)), 1, meta);
		modifier.addItem(is, needed, matched);
	}
	
	public static FluidMolten fluidMetal(String name, int color) {
		return registerFluid(new FluidMolten(name, color));
	}
	
	public static void initFluidMetal(Fluid fluid) {
		registerMoltenBlock(fluid);
		FluidRegistry.addBucketForFluid(fluid);
		PlusTiC.proxy.registerFluidModels(fluid);
	}
	
	public static <T extends Fluid> T registerFluid(T fluid) {
		fluid.setUnlocalizedName(ModInfo.MODID + "." + fluid.getName().toLowerCase(Locale.US));
		FluidRegistry.registerFluid(fluid);
		return fluid;
	}
	
	public static <T extends Block> T registerBlock(T block, String name) {
		block.setTranslationKey(ModInfo.MODID + "." + name);
		block.setRegistryName(ModInfo.MODID + "." + name);
		Item ib = new ItemBlock(block).setRegistryName(block.getRegistryName());
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(ib);
		return block;
	}
	
	public static BlockMolten registerMoltenBlock(Fluid fluid) {
		BlockMolten block = new BlockMolten(fluid) {
			@Nonnull
			@Override
			public String getTranslationKey() {
				Fluid fluid = FluidRegistry.getFluid(fluidName);
				if(fluid != null) {
					return fluid.getUnlocalizedName().substring(0, fluid.getUnlocalizedName().length() - 5); // chop off .name
				}
				return super.getTranslationKey();
			}
		};
		return registerBlock(block, "molten_" + fluid.getName());
	}
	
	public static void setDispItem(Material mat, String modid, String name) {
		if (mat == null)
			return;
		mat.setRepresentativeItem(Item.REGISTRY.getObject(new ResourceLocation(modid, name)));
	}
	
	public static void setDispItem(Material mat, String modid, String name, int meta) {
		if (mat == null)
			return;
		ItemStack is = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(modid, name)), 1, meta);
		mat.setRepresentativeItem(is);
	}
	
	public static void setDispItem(Material mat, String ore) {
		List<ItemStack> ores = OreDictionary.getOres(ore);
		if (mat == null || ores.isEmpty())
			return;
		mat.setRepresentativeItem(ores.get(0));
	}
	
	public static int gcd(int a, int b, int... rest) {
		if (rest.length > 0) {
			int[] rest1 = new int[rest.length - 1];
			System.arraycopy(rest, 1, rest1, 0, rest1.length);
			return gcd(gcd(a, b), rest[0], rest1);
		}
		return b == 0 ? a : gcd(b, a % b);
	}
	
	public static void teleportPlayerTo(EntityPlayerMP player, Coord4D coord) {
		if (player.dimension != coord.dimensionId) {
			player.changeDimension(coord.dimensionId, (world, entity, yaw) -> entity.setPositionAndUpdate(coord.xCoord +0.5, coord.yCoord +1, coord.zCoord +0.5));
		} else {
			player.setPositionAndUpdate(coord.xCoord +0.5, coord.yCoord +1, coord.zCoord +0.5);
		}
		player.world.updateEntityWithOptionalForce(player, true);
	}
	
	public static boolean canTeleportTo(EntityPlayer player, Coord4D dest) {
		if (dest == null)
			return false;
		for (int i = 1; i <= 2; ++i) {
			if (Optional.ofNullable(dest.add(0, i, 0).blockState())
					.map(bs -> bs.getCollisionBoundingBox(dest.world(), dest.pos()))
					.orElse(null) != null) {
				return false;
			}
		}
		return true;
	}
	
	public static int extractEnergy(ItemStack is, int amount, boolean simulate) {
		if (is != null) {
			if (is.hasCapability(CapabilityEnergy.ENERGY, null)) {
				return is.getCapability(CapabilityEnergy.ENERGY, null).extractEnergy(amount, simulate);
			}
			if (Loader.isModLoaded("tesla") && is.hasCapability(TeslaCapabilities.CAPABILITY_PRODUCER, null)) {
				return (int) is.getCapability(TeslaCapabilities.CAPABILITY_PRODUCER, null).takePower(amount, simulate);
			}
		}
		return 0;
	}
	
	public static class ItemMatGroup {
		public Item nugget, ingot;
		public Block block;
		
		public ItemMatGroup() {
		}
		
		public ItemMatGroup(Item nugget, Item ingot, Block block) {
			this.nugget = nugget;
			this.ingot = ingot;
			this.block = block;
		}
	}
	
	public static ItemMatGroup registerMatGroup(String name) {
		ItemMatGroup img = new ItemMatGroup();
		img.nugget = new Item().setTranslationKey(name + "nugget").setRegistryName(name + "nugget");
		img.nugget.setCreativeTab(TinkerRegistry.tabGeneral);
		ForgeRegistries.ITEMS.register(img.nugget);
		OreDictionary.registerOre("nugget" + StringUtils.capitalize(name), img.nugget);
		PlusTiC.proxy.registerItemRenderer(img.nugget, 0, name + "nugget");
		
		img.ingot = new Item().setTranslationKey(name + "ingot").setRegistryName(name + "ingot");
		img.ingot.setCreativeTab(TinkerRegistry.tabGeneral);
		ForgeRegistries.ITEMS.register(img.ingot);
		OreDictionary.registerOre("ingot" + StringUtils.capitalize(name), img.ingot);
		PlusTiC.proxy.registerItemRenderer(img.ingot, 0, name + "ingot");
		
		img.block = new MetalBlock(name + "block");
		img.block.setCreativeTab(TinkerRegistry.tabGeneral);
		ItemBlock bitem = new ItemBlock(img.block);
		ForgeRegistries.BLOCKS.register(img.block);
		ForgeRegistries.ITEMS.register(bitem.setRegistryName(img.block.getRegistryName()));
		OreDictionary.registerOre("block" + StringUtils.capitalize(name), img.block);
		PlusTiC.proxy.registerItemRenderer(bitem, 0, name + "block");
		
		return img;
	}
	
	public static List<IModifier> getModifiers(ItemStack stack) {
		List<IModifier> modifiers = new ArrayList<>();
		NBTTagList modifiersTagList = TagUtil.getBaseModifiersTagList(stack);
		for(int i = 0; i < modifiersTagList.tagCount(); i++) {
			IModifier modifier = TinkerRegistry.getModifier(modifiersTagList.getStringTagAt(i));
			if(modifier != null) {
				modifiers.add(modifier);
			}
		}
		return modifiers;
	}
	
	private static final MethodHandle harvestersHandle;
	static {
		try {
			Field field = Block.class.getDeclaredField("harvesters");
			field.setAccessible(true);
			harvestersHandle = MethodHandles.lookup().unreflectGetter(field);
		} catch (Throwable e) {
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}
	
	public static ThreadLocal<EntityPlayer> getHarvester() {
		try {
			return (ThreadLocal<EntityPlayer>)harvestersHandle.invokeExact();
		} catch (Throwable e) {
			Throwables.throwIfUnchecked(e);
			throw new RuntimeException(e);
		}
	}
	
	public static boolean areFluidStacksIdentical(@Nullable FluidStack a, @Nullable FluidStack b) {
		return a != null ? a.isFluidStackIdentical(b) : b == null;
	}
	
	public static FluidStack safeCopy(FluidStack stack) {
		return stack != null ? stack.copy() : null;
	}
}
