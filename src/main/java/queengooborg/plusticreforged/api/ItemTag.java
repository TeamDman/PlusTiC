package queengooborg.plusticreforged.api;

import net.minecraft.resources.ResourceLocation;

public class ItemTag extends Item {
	public ItemTag(ResourceLocation location) {
		super(location);
	}

	public ItemTag(String namespace, String path) {
		super(namespace, path);
	}
}