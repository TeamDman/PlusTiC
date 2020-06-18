package landmaster.plustic.entity.ai;

import landmaster.plustic.entity.EntitySupremeLeader;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAISupremeLeaderSwell extends EntityAIBase {
	/** The creeper that is swelling. */
	EntitySupremeLeader swellingCreeper;
	/**
	 * The creeper's attack target. This is used for the changing of the
	 * creeper's state.
	 */
	EntityLivingBase creeperAttackTarget;
	
	public EntityAISupremeLeaderSwell(EntitySupremeLeader entitycreeperIn) {
		this.swellingCreeper = entitycreeperIn;
		this.setMutexBits(1);
	}
	
	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		EntityLivingBase entitylivingbase = this.swellingCreeper.getAttackTarget();
		return this.swellingCreeper.getCreeperState() > 0
				|| entitylivingbase != null && this.swellingCreeper.getDistanceSq(entitylivingbase) < 9.0D;
	}
	
	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.swellingCreeper.getNavigator().clearPath();
		this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
	}
	
	/**
	 * Resets the task
	 */
	public void resetTask() {
		this.creeperAttackTarget = null;
	}
	
	/**
	 * Updates the task
	 */
	public void updateTask() {
		if (this.creeperAttackTarget == null) {
			this.swellingCreeper.setCreeperState(-1);
		} else if (this.swellingCreeper.getDistanceSq(this.creeperAttackTarget) > 49.0D) {
			this.swellingCreeper.setCreeperState(-1);
		} else if (!this.swellingCreeper.getEntitySenses().canSee(this.creeperAttackTarget)) {
			this.swellingCreeper.setCreeperState(-1);
		} else {
			this.swellingCreeper.setCreeperState(1);
		}
	}
}
