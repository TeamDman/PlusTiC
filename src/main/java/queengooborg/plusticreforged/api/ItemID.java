package queengooborg.plusticreforged.api;

import net.minecraft.resources.ResourceLocation;

public class ItemID extends Item {
	public ItemID(ResourceLocation location) {
		super(location);
	}

	public ItemID(String namespace, String path) {
		super(namespace, path);
	}
}
