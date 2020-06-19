package colonelgerdauf.plustic.traits;

import colonelgerdauf.plustic.entity.*;

public class DPRK extends EntityCameoTrait {
	public static final DPRK dprk = new DPRK();
	
	public DPRK() {
		super("dprk", 0xE30000, EntitySupremeLeader::new);
	}
}
