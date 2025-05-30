package queengooborg.plusticreforged.modifiers;

import queengooborg.plusticreforged.api.Description;
import queengooborg.plusticreforged.api.Modifier;

import java.awt.*;

public class UnstableMatterModifier extends Modifier {
	public UnstableMatterModifier() {
		super("unstable_matter", "Unstable Matter", new Description("Random explosions plague your enemies. Also, when you are attacked, there is a chance to get Absorption."), new Color(232, 0, 0));
	}

	// XXX Convert me!

//	public UnstableMatter() {
//		super("unstablematter", 0xFFFFFF);
//		MinecraftForge.EVENT_BUS.register(this);
//	}
//
//	@Override
//	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
//		if (isSelected && !world.isRemote && random.nextFloat() < 0.02f) {
//			List<Entity> extras = entity instanceof EntityLivingBase
//					&& ((EntityLivingBase)entity).getRevengeTarget() != null ?
//							ImmutableList.of(((EntityLivingBase)entity).getRevengeTarget())
//							: ImmutableList.of();
//			entExplode(world, entity, extras, 1);
//		}
//	}
//
//	@SubscribeEvent
//	public void onDetonate(ExplosionEvent.Detonate event) {
//		if (!event.getWorld().isRemote && event.getExplosion() instanceof HExplosion) {
//			event.getAffectedEntities().remove(event.getExplosion().getExplosivePlacedBy());
//		}
//	}
//
//	@SubscribeEvent
//	public void defend(LivingHurtEvent event) {
//		ItemStack tool = event.getEntityLiving().getHeldItemMainhand();
//		if (event.getEntity().getEntityWorld().isRemote
//				|| !TinkerUtil.hasTrait(
//						TagUtil.getTagSafe(tool),
//						getIdentifier()))
//			return;
//		if (random.nextFloat() < 0.3f) {
//			entExplode(event.getEntity().getEntityWorld(), event.getEntity(),
//					getAttacker(event.getSource())
//					.map(ImmutableList::of)
//					.orElse(ImmutableList.of()), 1.3f);
//		}
//		if (random.nextFloat() < event.getAmount()/8.0f) { // probability increases with damage
//			event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 1600, 3));
//			event.getEntityLiving().sendMessage(new TextComponentTranslation("msg.plustic.unstablematter.use"));
//		}
//	}
//
//	private static @javax.annotation.Nonnull Optional<Entity> getAttacker(DamageSource source) {
//		if (source instanceof EntityDamageSource) {
//			return Optional.ofNullable(((EntityDamageSource)source).getTrueSource());
//		}
//		return Optional.empty();
//	}
//
//	private static void entExplode(World world, Entity entity, Collection<Entity> extraTargets, float power) {
//		List<Entity> ents = world.getEntitiesWithinAABB(EntityLiving.class, Utils.AABBfromVecs(
//				entity.getPositionVector().subtract(9, 9, 9),
//				entity.getPositionVector().add(9, 9, 9)),
//				ent -> ent instanceof IMob && ent != entity && !extraTargets.contains(ent));
//		ents.addAll(extraTargets);
//		if (!ents.isEmpty()) {
//			Entity target = ents.get(random.nextInt(ents.size()));
//			Explosion explosion = new HExplosion(world, entity, target.posX, target.posY, target.posZ, power, false, false);
//			if (!net.minecraftforge.event.ForgeEventFactory.onExplosionStart(world, explosion)) {
//		        explosion.doExplosionA();
//		        explosion.doExplosionB(true);
//			}
//		}
//	}
//
//	private static class HExplosion extends Explosion {
//		public HExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming,
//				boolean smoking) {
//			super(worldIn, entityIn, x, y, z, size, flaming, smoking);
//		}
//	}
}
