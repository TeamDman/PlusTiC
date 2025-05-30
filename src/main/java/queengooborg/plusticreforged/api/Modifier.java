package queengooborg.plusticreforged.api;

import net.minecraft.resources.ResourceLocation;
import queengooborg.plusticreforged.Registries;
import queengooborg.plusticreforged.config.ModInfo;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import java.awt.*;
import java.util.Objects;

public abstract class Modifier extends slimeknights.tconstruct.library.modifiers.Modifier {
	public String id;
	public String name;
	public Description description = new Description();

	// XXX This is temporary to determine which modifiers have been converted to the new system
	// Remove this once all modifiers have been converted
	public boolean usable = false;

	public ResourceLocation resourceLocation;
	public StaticModifier<Modifier> instance;

	public Modifier(String id, String name, Description description, Color color) {
		// XXX Need to figure out how to set the colors in the new system
//		super(color.getRGB());

		this.id = Objects.requireNonNull(id);
		this.name = Objects.requireNonNull(name);
		if (description != null) this.description = description;

		this.resourceLocation = new ResourceLocation(ModInfo.MOD_ID, id);

		this.instance = Registries.MODIFIERS.register(id, () -> this);
	}
}
