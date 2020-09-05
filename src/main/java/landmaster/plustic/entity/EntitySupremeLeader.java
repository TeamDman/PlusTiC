package landmaster.plustic.entity;

import landmaster.plustic.entity.ai.EntityAISupremeLeaderSwell;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.UUID;

public class EntitySupremeLeader extends EntityCreature {
    private static final DataParameter<Integer> STATE = EntityDataManager.createKey(EntitySupremeLeader.class,
            DataSerializers.VARINT);
    private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(EntitySupremeLeader.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(EntitySupremeLeader.class,
            DataSerializers.BOOLEAN);

    static {
        MinecraftForge.EVENT_BUS.register(EntitySupremeLeader.class);
    }

    /**
     * Time when this creeper was last in an active state (Messed up code here,
     * probably causes creeper animation to go weird)
     */
    private int lastActiveTime;
    /**
     * The amount of time since the creeper was close enough to the player to
     * ignite
     */
    private int timeSinceIgnited;
    private int fuseTime = 20;
    /**
     * Explosion radius for this creeper.
     */
    private int explosionRadius = 3;
    private @Nullable
    UUID summonerId;
    public EntitySupremeLeader(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.7F);
    }

    public EntitySupremeLeader(World worldIn, Entity summoner, EntityLivingBase target) {
        this(worldIn);
        summonerId = summoner.getPersistentID();
        setAttackTarget(target);
    }

    @SubscribeEvent
    public static void onDetonate(ExplosionEvent.Detonate event) {
        if (!event.getWorld().isRemote && event.getExplosion() instanceof KimExplosion) {
            event.getAffectedEntities().removeIf(
                    ent -> ent.getUniqueID().equals(
                            ((KimExplosion) event.getExplosion())
                                    .getSupremeLeader().summonerId));
        }
    }

    public static void registerFixesCreeper(DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntitySupremeLeader.class);
    }

    @Override
    public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
        if (entitylivingbaseIn == null
                || !entitylivingbaseIn.getPersistentID().equals(summonerId)) {
            super.setAttackTarget(entitylivingbaseIn);
        }
    }

    @Override
    public boolean canAttackClass(Class<? extends EntityLivingBase> cls) {
        return true; // can attack ghasts
    }

    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAISupremeLeaderSwell(this));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 10,
                true, false, ent -> ent instanceof IMob));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(80);
    }

    /**
     * The maximum height from where the entity is alowed to jump (used in
     * pathfinder)
     */
    public int getMaxFallHeight() {
        return this.getAttackTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
    }

    public void fall(float distance, float damageMultiplier) {
        super.fall(distance, damageMultiplier);
        this.timeSinceIgnited = (int) ((float) this.timeSinceIgnited + distance * 1.5F);

        if (this.timeSinceIgnited > this.fuseTime - 5) {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(STATE, Integer.valueOf(-1));
        this.dataManager.register(POWERED, Boolean.valueOf(false));
        this.dataManager.register(IGNITED, Boolean.valueOf(false));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        if (((Boolean) this.dataManager.get(POWERED)).booleanValue()) {
            compound.setBoolean("powered", true);
        }

        compound.setShort("Fuse", (short) this.fuseTime);
        compound.setByte("ExplosionRadius", (byte) this.explosionRadius);
        compound.setBoolean("ignited", this.hasIgnited());

        if (summonerId != null) {
            compound.setUniqueId("SummonerId", summonerId);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(POWERED, Boolean.valueOf(compound.getBoolean("powered")));

        if (compound.hasKey("Fuse", 99)) {
            this.fuseTime = compound.getShort("Fuse");
        }

        if (compound.hasKey("ExplosionRadius", 99)) {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }

        if (compound.getBoolean("ignited")) {
            this.ignite();
        }

        if (compound.hasUniqueId("SummonerId")) {
            summonerId = compound.getUniqueId("SummonerId");
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;

            if (this.hasIgnited()) {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                this.explode();
            }
        }

        super.onUpdate();
    }

    protected SoundEvent getHurtSound() {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CREEPER_DEATH;
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        return true;
    }

    /**
     * Returns true if the creeper is powered by a lightning bolt.
     */
    public boolean getPowered() {
        return ((Boolean) this.dataManager.get(POWERED)).booleanValue();
    }

    /**
     * Params: (Float)Render tick. Returns the intensity of the creeper's flash
     * when it is ignited.
     */
    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(float p_70831_1_) {
        return ((float) this.lastActiveTime + (float) (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_)
                / (float) (this.fuseTime - 2);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return null;
    }

    /**
     * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
     */
    public int getCreeperState() {
        return ((Integer) this.dataManager.get(STATE)).intValue();
    }

    /**
     * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
     */
    public void setCreeperState(int state) {
        this.dataManager.set(STATE, Integer.valueOf(state));
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    public void onStruckByLightning(EntityLightningBolt lightningBolt) {
        super.onStruckByLightning(lightningBolt);
        this.dataManager.set(POWERED, Boolean.valueOf(true));
    }

    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE,
                    this.getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
            player.swingArm(hand);

            if (!this.world.isRemote) {
                this.ignite();
                itemstack.damageItem(1, player);
                return true;
            }
        }

        return super.processInteract(player, hand);
    }

    /**
     * Creates an explosion as determined by this creeper's power and explosion
     * radius.
     */
    private void explode() {
        if (!this.world.isRemote) {
            float f = this.getPowered() ? 2.0F : 1.0F;
            this.dead = true;

            Explosion explosion = new KimExplosion(this.world, this, this.posX, this.posY, this.posZ, (float) this.explosionRadius * f, false, false);
            if (!net.minecraftforge.event.ForgeEventFactory.onExplosionStart(world, explosion)) {
                explosion.doExplosionA();
                explosion.doExplosionB(true);
            }

            this.setDead();
            this.spawnLingeringCloud();
        }
    }

    private void spawnLingeringCloud() {
        Collection<PotionEffect> collection = this.getActivePotionEffects();

        if (!collection.isEmpty()) {
            EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY,
                    this.posZ);
            entityareaeffectcloud.setRadius(2.5F);
            entityareaeffectcloud.setRadiusOnUse(-0.5F);
            entityareaeffectcloud.setWaitTime(10);
            entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
            entityareaeffectcloud
                    .setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float) entityareaeffectcloud.getDuration());

            for (PotionEffect potioneffect : collection) {
                entityareaeffectcloud.addEffect(new PotionEffect(potioneffect));
            }

            this.world.spawnEntity(entityareaeffectcloud);
        }
    }

    public boolean hasIgnited() {
        return ((Boolean) this.dataManager.get(IGNITED)).booleanValue();
    }

    public void ignite() {
        this.dataManager.set(IGNITED, Boolean.valueOf(true));
    }

    private static class KimExplosion extends Explosion {
        private final EntitySupremeLeader kim;

        public KimExplosion(World worldIn, EntitySupremeLeader entityIn, double x, double y, double z, float size, boolean flaming,
                            boolean smoking) {
            super(worldIn, entityIn, x, y, z, size, flaming, smoking);
            this.kim = entityIn;
        }

        public EntitySupremeLeader getSupremeLeader() {
            return kim;
        }
    }
}
