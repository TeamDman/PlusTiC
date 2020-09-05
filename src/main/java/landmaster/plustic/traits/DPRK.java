package landmaster.plustic.traits;

import landmaster.plustic.entity.EntitySupremeLeader;

public class DPRK extends EntityCameoTrait {
    public static final DPRK dprk = new DPRK();

    public DPRK() {
        super("dprk", 0xE30000, EntitySupremeLeader::new);
    }
}
