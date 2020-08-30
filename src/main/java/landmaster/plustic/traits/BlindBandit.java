package landmaster.plustic.traits;

import landmaster.plustic.entity.EntityBlindBandit;

public class BlindBandit extends EntityCameoTrait {
    public static final BlindBandit blindbandit = new BlindBandit();

    public BlindBandit() {
        super("blindbandit", 0xFF00FF, EntityBlindBandit::new);
    }
}
