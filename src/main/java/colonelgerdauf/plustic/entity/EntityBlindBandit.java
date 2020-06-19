package colonelgerdauf.plustic.entity;

import java.util.*;

import javax.annotation.*;

import colonelgerdauf.plustic.entity.ai.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.datasync.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;

public class EntityBlindBandit extends EntityCreature {
	private static final DataParameter<Boolean> ARMS_RAISED = EntityDataManager.createKey(EntityBlindBandit.class, DataSerializers.BOOLEAN);
	
	public static final int MAX_COUNTDOWN = 300;
	
	private int countdown = MAX_COUNTDOWN;
	private @Nullable UUID summonerId;
	
	public EntityBlindBandit(World worldIn) {
		super(worldIn);
		setSize(0.6F, 1.95F);
		this.isImmuneToFire = true;
	}
	
	public EntityBlindBandit(World worldIn, Entity summoner, EntityLivingBase target) {
		this(worldIn);
		summonerId = summoner.getPersistentID();
		setAttackTarget(target);
	}
	
	@Override
	public void addPotionEffect(PotionEffect potioneffectIn) {}
	
	@Override
	public boolean isImmuneToExplosions() {
		return true;
	}
	
	@Override
	public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
		if (entitylivingbaseIn == null
				|| !entitylivingbaseIn.getPersistentID().equals(summonerId)) {
			super.setAttackTarget(entitylivingbaseIn);
		}
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (--countdown <= 0) {
			this.setDead();
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (compound.hasKey("DeathCountdown", 99)) {
			countdown = compound.getInteger("DeathCountdown");
		}
		if (compound.hasUniqueId("SummonerId")) {
			summonerId = compound.getUniqueId("SummonerId");
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("DeathCountdown", countdown);
		if (summonerId != null) {
			compound.setUniqueId("SummonerId", summonerId);
		}
	}
	
	@Override
	public boolean canAttackClass(Class<? extends EntityLivingBase> cls) {
		return true; // can attack ghasts
	}
	
	@Override
    protected void entityInit() {
		super.entityInit();
        this.getDataManager().register(ARMS_RAISED, Boolean.valueOf(false));
	}
	
	public void setArmsRaised(boolean armsRaised) {
        this.getDataManager().set(ARMS_RAISED, Boolean.valueOf(armsRaised));
    }

    @SideOnly(Side.CLIENT)
    public boolean isArmsRaised() {
        return this.getDataManager().get(ARMS_RAISED).booleanValue();
    }
    
    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
    	if (__attackEntityAsMob(entityIn)) {
    		if (entityIn instanceof EntityLivingBase) {
    			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
    		}
    		return true;
    	}
    	return false;
    }
    
	private boolean __attackEntityAsMob(Entity entityIn)
    {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        int i = 0;

        if (entityIn instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }
        
        DamageSource src = DamageSource.causeMobDamage(this);
        src.setDamageBypassesArmor();
        boolean flag = entityIn.attackEntityFrom(src, f);

        if (flag)
        {
            if (i > 0 && entityIn instanceof EntityLivingBase)
            {
                ((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                entityIn.setFire(j * 4);
            }

            if (entityIn instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entityIn;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : null;

                if (itemstack != null && itemstack1 != null && itemstack.getItem() instanceof ItemAxe && itemstack1.getItem() == Items.SHIELD)
                {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1)
                    {
                        entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
                        this.getEntityWorld().setEntityState(entityplayer, (byte)30);
                    }
                }
            }

            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }
	
	@Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.5D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(19.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(8.0D);
    }
	
	@Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIBlindBanditAttack(this, 1.0D, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }
	
	private void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 10,
        		true, false, ent -> ent instanceof IMob));
    }
}
