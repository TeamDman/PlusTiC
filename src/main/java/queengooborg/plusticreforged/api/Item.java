package queengooborg.plusticreforged.api;

import net.minecraft.resources.ResourceLocation;

public class Item implements Ingredient {
	public ResourceLocation location;

	public Item(ResourceLocation location) {
		this.location = location;
	}

	public Item(String namespace, String path) {
		this(new ResourceLocation(namespace, path));
	}
}
