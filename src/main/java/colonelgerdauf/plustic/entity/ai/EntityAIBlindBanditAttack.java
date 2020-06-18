package landmaster.plustic.entity.ai;

import landmaster.plustic.entity.*;
import net.minecraft.entity.ai.*;

public class EntityAIBlindBanditAttack extends EntityAIAttackMelee {
	private int raiseArmTicks;
    private EntityBlindBandit bandit;

    public EntityAIBlindBanditAttack(EntityBlindBandit banditIn, double speedIn, boolean longMemoryIn) {
        super(banditIn, speedIn, longMemoryIn);
        this.bandit = banditIn;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        super.startExecuting();
        this.raiseArmTicks = 0;
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        super.resetTask();
        this.bandit.setArmsRaised(false);
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask() {
        super.updateTask();
        ++this.raiseArmTicks;

        if (this.raiseArmTicks >= 5 && this.attackTick < 10) {
            this.bandit.setArmsRaised(true);
        } else {
            this.bandit.setArmsRaised(false);
        }
    }
}
