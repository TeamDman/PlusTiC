package queengooborg.plusticreforged.api;

import java.util.Objects;

public class MaterialType {
	public static MaterialType METAL = new MaterialType();
	public static MaterialType POWDER = new MaterialType(new String[]{"powder"});
	public static MaterialType STONE = new MaterialType(new String[]{"stone", "wood", "stick"});
	public static MaterialType WOOD = new MaterialType(new String[]{"wood", "stick"});
	public static MaterialType BONE = new MaterialType(new String[]{"bone", "stick"});
	public static MaterialType CRYSTAL = new MaterialType(new String[]{"crystal"});

	public String[] fallbacks = {"metal"};

	public MaterialType() {
		this(new String[]{"metal"});
	}

	public MaterialType(String[] fallbacks) {
		this.fallbacks = Objects.requireNonNull(fallbacks);
	}
}
