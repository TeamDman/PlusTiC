package colonelgerdauf.plustic.util;

import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.*;
import vazkii.psi.common.core.handler.*;

public class PsiUtils {
	public static int extractPsi(EntityPlayer player, int maxExtract) {
		if ((Loader.isModLoaded("Psi") || Loader.isModLoaded("psi")) && PlayerDataHandler.get(player) != null && PlayerDataHandler.get(player).getCAD() != null) {
			int amount = PlayerDataHandler.get(player).getAvailablePsi();
			PlayerDataHandler.get(player).deductPsi(Math.min(maxExtract, amount), 20, true);
			return Math.min(maxExtract, amount);
		}
		return 0;
	}
	
	public static int extractPsiExact(EntityPlayer player, int extract) {
		if ((Loader.isModLoaded("Psi") || Loader.isModLoaded("psi")) && PlayerDataHandler.get(player) != null && PlayerDataHandler.get(player).getCAD() != null) {
			int amount = PlayerDataHandler.get(player).getAvailablePsi();
			if (amount >= extract) {
				PlayerDataHandler.get(player).deductPsi(extract, 20, true);
				return Math.min(extract, amount);
			}
		}
		return 0;
	}
}
