package colonelgerdauf.plustic.util;

import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;

public class ClientUtils {
	public static void drawBeam(Vec3d S, Vec3d E, Vec3d P, float width) {
		Vec3d PS = S.subtract(P);
		Vec3d SE = E.subtract(S);
		
		Vec3d normal = PS.crossProduct(SE).normalize();
		
		Vec3d half = normal.scale(width);
		Vec3d p1 = S.add(half);
		Vec3d p2 = S.subtract(half);
		Vec3d p3 = E.add(half);
		Vec3d p4 = E.subtract(half);
		
		drawQuad(Tessellator.getInstance(), p1, p3, p4, p2);
	}
	
	private static void drawQuad(Tessellator tessellator, Vec3d p1, Vec3d p2, Vec3d p3, Vec3d p4) {
		int brightness = 240;
		int b1 = brightness >> 16 & 65535;
		int b2 = brightness & 65535;
		
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.pos(p1.x, p1.y, p1.z).tex(0.0D, 0.0D).lightmap(b1, b2).color(255, 255, 255, 128)
				.endVertex();
		buffer.pos(p2.x, p2.y, p2.z).tex(1.0D, 0.0D).lightmap(b1, b2).color(255, 255, 255, 128)
				.endVertex();
		buffer.pos(p3.x, p3.y, p3.z).tex(1.0D, 1.0D).lightmap(b1, b2).color(255, 255, 255, 128)
				.endVertex();
		buffer.pos(p4.x, p4.y, p4.z).tex(0.0D, 1.0D).lightmap(b1, b2).color(255, 255, 255, 128)
				.endVertex();
	}
}
