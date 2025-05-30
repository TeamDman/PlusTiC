package queengooborg.plusticreforged.api;

import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.IColorMapping;

import java.awt.*;
import java.util.Objects;

public class MaterialColors {
	public Color base;
	public Color f63 = new Color(0x3F3F3F);
	public Color f102 = new Color(0x5E5E5E);
	public Color f140 = new Color(0x828282);
	public Color f178 = new Color(0xA8A8A8);
	public Color f216 = new Color(0xD8D8D8);
	public Color f255 = new Color(0xFFFFFF);
	public int luminosity = 0;

	public MaterialColors() {
		this(new Color(0xD8D8D8), 0);
	}

	public MaterialColors(int base) {
		this(new Color(base), 0);
	}

	public MaterialColors(Color base) {
		this(base, 0);
	}

	public MaterialColors(Color base, int luminosity) {
		this.base = Objects.requireNonNull(base);
		this.f63 = adjustColorBrightness(base, 63);
		this.f102 = adjustColorBrightness(base, 102);
		this.f140 = adjustColorBrightness(base, 140);
		this.f178 = adjustColorBrightness(base, 178);
		this.f216 = adjustColorBrightness(base, 216);
		this.f255 = adjustColorBrightness(base, 255);
		this.luminosity = luminosity;
	}

	public MaterialColors(Color base, int luminosity, Color f63, Color f102, Color f140, Color f178, Color f216, Color f255) {
		this.base = base;
		this.f63 = f63 == null ? adjustColorBrightness(base, 63) : f63;
		this.f102 = f102 == null ? adjustColorBrightness(base, 102) : f102;
		this.f140 = f140 == null ? adjustColorBrightness(base, 140) : f140;
		this.f178 = f178 == null ? adjustColorBrightness(base, 178) : f178;
		this.f216 = f216 == null ? adjustColorBrightness(base, 216) : f216;
		this.f255 = f255 == null ? adjustColorBrightness(base, 255) : f255;
		this.luminosity = luminosity;
	}

	public IColorMapping getColorMapper() {
		return GreyToColorMapping.builderFromBlack()
				.addARGB(63, f63.getRGB())
				.addARGB(102, f102.getRGB())
				.addARGB(140, f140.getRGB())
				.addARGB(178, f178.getRGB())
				.addARGB(216, f216.getRGB())
				.addARGB(255, f255.getRGB())
				.build();
	}

	public Color adjustColorBrightness(Color input, int brightness) {
		// XXX This is a placeholder for the actual color brightness calculation

		// Iron example (base: 0xD8D8D8):
		// [...].colorMapper(
		//  GreyToColorMapping.builderFromBlack()
		//  .addARGB(63, 0xFF353535)
		//  .addARGB(102, 0xFF5E5E5E)
		//  .addARGB(140, 0xFF828282)
		//  .addARGB(178, 0xFFA8A8A8)
		//  .addARGB(216, 0xFFD8D8D8)
		//  .addARGB(255, 0xFFFFFFFF)
		//  .build()
		// );

		// Copper example (base: 0xF98648):
		// [...].colorMapper(
		//  GreyToColorMapping.builderFromBlack()
		//  .addARGB(63, 0xFF72341A)
		//  .addARGB(102, 0xFF934828)
		//  .addARGB(140, 0xFFD87236)
		//  .addARGB(178, 0xFFEF8345)
		//  .addARGB(216, 0xFFFBA165)
		//  .addARGB(255, 0xFFFAC493)
		//  .build()
		// );

		int shift = (int) ((brightness - 127 - 40) / 1.5f);
		return new Color(
				Math.min(255, Math.max(0, input.getRed() + shift)),
				Math.min(255, Math.max(0, input.getGreen() + shift)),
				Math.min(255, Math.max(0, input.getBlue() + shift)),
				input.getAlpha()
		);
	}
}