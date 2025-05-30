package queengooborg.plusticreforged.api;

import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import queengooborg.plusticreforged.config.ModInfo;
import slimeknights.tconstruct.common.json.ConfigEnabledCondition;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import java.util.Arrays;
import java.util.Objects;

import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.ORDER_COMPAT;
import static slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider.ORDER_HARVEST;

public class Material {
	public String id;
	public String name;
	public Ingredient ingredient;
	public Description description = new Description();
	public int tier = 1;
	public MaterialType type;
	public int order = ORDER_HARVEST + ORDER_COMPAT;
	public MaterialColors color = new MaterialColors();
	public MaterialStats stats = new MaterialStats();
	public Modifier[] modifiers;
	public Fluid moltenFluid;
	public ICondition condition;

	public MaterialId resourceLocation;

	public Material(String id, String name, Description description, Ingredient ingredient, ICondition condition, int tier, MaterialType type, int order, MaterialColors color, MaterialStats stats, int fluidTemperature) {
		this(id, name, description, ingredient, condition, tier, type, order, color, stats, new Modifier[]{}, fluidTemperature);
	}

	public Material(String id, String name, Ingredient ingredient, Description description, ICondition condition, int tier, MaterialType type, int order, MaterialColors color, MaterialStats stats, Fluid fluid) {
		this(id, name, description, ingredient, condition, tier, type, order, color, stats, new Modifier[]{}, fluid);
	}

	public Material(String id, String name, Description description, Ingredient ingredient, ICondition condition, int tier, MaterialType type, int order, MaterialColors color, MaterialStats stats, Modifier modifier, int fluidTemperature) {
		this(id, name, description, ingredient, condition, tier, type, order, color, stats, new Modifier[]{modifier}, fluidTemperature);
	}

	public Material(String id, String name, Ingredient ingredient, Description description, ICondition condition, int tier, MaterialType type, int order, MaterialColors color, MaterialStats stats, Modifier modifier, Fluid fluid) {
		this(id, name, description, ingredient, condition, tier, type, order, color, stats, new Modifier[]{modifier}, fluid);
	}

	public Material(String id, String name, Description description, Ingredient ingredient, ICondition condition, int tier, MaterialType type, int order, MaterialColors color, MaterialStats stats, Modifier[] modifiers, int fluidTemperature) {
		this(id, name, description, ingredient, condition, tier, type, order, color, stats, modifiers, new Fluid("molten_" + id, name, fluidTemperature, 15, 3000, 6000, color.base));
	}

	public Material(String id, String name, Description description, Ingredient ingredient, ICondition condition, int tier, MaterialType type, int order, MaterialColors color, MaterialStats stats, Modifier[] modifiers, Fluid fluid) {

		this.id = Objects.requireNonNull(id);
		this.name = Objects.requireNonNull(name);
		this.ingredient = Objects.requireNonNull(ingredient);
		if (description != null) this.description = description;
		this.tier = tier;
		this.order = order;
		if (type != null) this.type = type;
		if (color != null) this.color = color;
		if (stats != null) this.stats = stats;
		if (modifiers != null) this.modifiers = modifiers;
		this.moltenFluid = Objects.requireNonNull(fluid);

		// XXX Need to add alloy support (and improve the way we handle tags)
		if (ingredient instanceof Item) {
			Item item = (Item) ingredient;

			ICondition[] conditions = null;
			if (type == MaterialType.METAL) {
				conditions = new ICondition[]{
						ConfigEnabledCondition.FORCE_INTEGRATION_MATERIALS,
						new NotCondition(
								item instanceof ItemTag ?
										new TagEmptyCondition(item.location) :
										new TagEmptyCondition("forge", "ingots/" + item.location.getPath())
						)
				};
			} else if (item instanceof ItemTag) {
				conditions = new ICondition[]{new NotCondition(new TagEmptyCondition(item.location))};
			}

			this.condition = conditions == null ? condition : new OrCondition(addCondition(conditions, condition));
		}

		this.resourceLocation = new MaterialId(ModInfo.MOD_ID, id);
	}

	private ICondition[] addCondition(ICondition[] conditions, ICondition newCondition) {
		if (newCondition == null) return conditions;

		// We have to...create an entirely new array to add an element? Ugh, that's annoying...
		ICondition[] newConditions = new ICondition[conditions.length + 1];
		System.arraycopy(conditions, 0, newConditions, 0, conditions.length);
		newConditions[conditions.length] = newCondition;
		return newConditions;
	}

	public StaticModifier[] getModifiers() {
		return Arrays.stream(modifiers).map(modifier -> modifier.instance).toArray(StaticModifier[]::new);
	}
}