package queengooborg.plusticreforged.api;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import queengooborg.plusticreforged.Registries;
import queengooborg.plusticreforged.config.ModInfo;
import slimeknights.mantle.registration.object.FluidObject;

import java.awt.*;
import java.util.function.Supplier;

public class Fluid {
	public final String id;
	public final String materialName;
	public final int temperature;
	public final int light;
	public final int density;
	public final int viscosity;

	public final Supplier<ForgeFlowingFluid.Source> FLUID;
	public final Supplier<ForgeFlowingFluid.Flowing> FLUID_FLOWING;
	public final FluidObject<ForgeFlowingFluid> FLUID_OBJECT;
	public final ForgeFlowingFluid.Properties FLUID_PROPERTIES;
	public final Supplier<LiquidBlock> FLUID_BLOCK;
	public final RegistryObject<BucketItem> FLUID_BUCKET;
	public final ResourceLocation TEXTURE_STILL;
	public final ResourceLocation TEXTURE_FLOWING;

	public Fluid(FluidObject<ForgeFlowingFluid> fluid) {
		this.id = null;
		this.materialName = null;
		this.temperature = fluid.get().getAttributes().getTemperature();
		this.light = fluid.get().getAttributes().getLuminosity();
		this.density = fluid.get().getAttributes().getDensity();
		this.viscosity = fluid.get().getAttributes().getViscosity();

		FLUID = () -> (ForgeFlowingFluid.Source) fluid.get();
		FLUID_FLOWING = () -> (ForgeFlowingFluid.Flowing) fluid.getFlowing();
		FLUID_BLOCK = fluid::getBlock;
		FLUID_OBJECT = fluid;
		TEXTURE_STILL = fluid.get().getAttributes().getStillTexture();
		TEXTURE_FLOWING = fluid.getFlowing().getAttributes().getFlowingTexture();

		// We can't get either of these from the fluid object, so we set them to null
		FLUID_PROPERTIES = null;
		FLUID_BUCKET = null;
	}

	public Fluid(String id, String materialName, int temperature, int light, int density, int viscosity, Color color) {
		this.id = id;
		this.materialName = materialName;
		this.temperature = temperature;
		this.light = light;
		this.density = density;
		this.viscosity = viscosity;

		FLUID = Registries.FLUIDS.register(id, () -> new ForgeFlowingFluid.Source(getFluidProperties()));
		FLUID_FLOWING = Registries.FLUIDS.register("flowing_" + id, () -> new ForgeFlowingFluid.Flowing(getFluidProperties()));

		TEXTURE_STILL = new ResourceLocation("tconstruct", "block/fluid/molten/still");
		TEXTURE_FLOWING = new ResourceLocation("tconstruct", "block/fluid/molten/flowing");

		FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(FLUID, FLUID_FLOWING, FluidAttributes.builder(TEXTURE_STILL, TEXTURE_FLOWING).overlay(TEXTURE_STILL).color(color.getRGB()).luminosity(light).density(density).viscosity(viscosity).temperature(temperature).sound(this.isHot() ? SoundEvents.BUCKET_FILL : SoundEvents.BUCKET_FILL_LAVA, this.isHot() ? SoundEvents.BUCKET_EMPTY : SoundEvents.BUCKET_EMPTY_LAVA));

		FLUID_BLOCK = Registries.BLOCKS.register(id + "_block", () -> new LiquidBlock(FLUID, Block.Properties.of(net.minecraft.world.level.material.Material.LAVA).lightLevel((state) -> {
			return light;
		}).randomTicks().strength(100.0F).noDrops()));
		FLUID_BUCKET = Registries.ITEMS.register(id + "_bucket", () -> new BucketItem(FLUID, new BucketItem.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(CreativeModeTab.TAB_MISC)));

		FLUID_PROPERTIES.bucket(FLUID_BUCKET).block(FLUID_BLOCK).explosionResistance(1000F).tickRate(9);

		FLUID_OBJECT = new FluidObject<>(new ResourceLocation(ModInfo.MOD_ID, id), id, FLUID, FLUID_FLOWING, FLUID_BLOCK);
	}

	public boolean isHot() {
		return temperature > 600;
	}

	public ForgeFlowingFluid.Properties getFluidProperties() {
		return FLUID_PROPERTIES;
	}

	public ForgeFlowingFluid getFluid() {
		return FLUID.get();
	}

	public TagKey<net.minecraft.world.level.material.Fluid> getLocalTag() {
		return FLUID_OBJECT.getLocalTag();
	}

	public TagKey<net.minecraft.world.level.material.Fluid> getForgeTag() {
		return FLUID_OBJECT.getForgeTag();
	}
}