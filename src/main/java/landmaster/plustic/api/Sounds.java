package landmaster.plustic.api;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import landmaster.plustic.ModInfo;
import landmaster.plustic.modifiers.armor.JetpackPancakeHippos;
import landmaster.plustic.traits.MusicOfTheSpheres;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import tonius.simplyjetpacks.sound.SJSoundRegistry;

import java.lang.ref.WeakReference;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public class Sounds {
    public static final SoundEvent LASER_BEAM = new SoundEvent(new ResourceLocation(ModInfo.MODID, "laser_beam")).setRegistryName("laser_beam");

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(LASER_BEAM);
    }

    public static void playSoundToAll(Entity entity, SoundEvent sound, float volume, float pitch) {
        entity.getEntityWorld().playSound(null, entity.posX, entity.posY, entity.posZ,
                sound, entity.getSoundCategory(), volume, pitch);
    }

    @SideOnly(Side.CLIENT)
    public static class MOTSSound extends MovingSound {

        private WeakReference<EntityPlayer> player;

        public MOTSSound(EntityPlayer player, SoundEvent soundIn, SoundCategory categoryIn) {
            super(soundIn, categoryIn);
            this.player = new WeakReference<>(player);
        }

        @Override
        public void update() {
            if (player.get() == null || !TinkerUtil.hasTrait(TagUtil.getTagSafe(player.get().getHeldItemMainhand()), MusicOfTheSpheres.musicofthespheres.identifier)) {
                this.donePlaying = true;
            }
            this.xPosF = (float) this.player.get().posX;
            this.yPosF = (float) this.player.get().posY;
            this.zPosF = (float) this.player.get().posZ;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class PTSoundJetpack extends MovingSound {
        private static final Int2ObjectMap<PTSoundJetpack> playingFor = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());

        private final EntityLivingBase user;
        private int fadeOut = -1;

        public PTSoundJetpack(EntityLivingBase target) {
            super(SJSoundRegistry.JETPACK.getSoundEvent(), SoundCategory.PLAYERS);
            this.repeat = true;
            this.user = target;
            playingFor.put(target.getEntityId(), this);
        }

        public static boolean isPlayingFor(int entityId) {
            return playingFor.containsKey(entityId) && playingFor.get(entityId) != null && !playingFor.get(entityId).donePlaying;
        }

        public static void clearPlayingFor() {
            playingFor.clear();
        }

        @Override
        public void update() {
            this.xPosF = (float) this.user.posX;
            this.yPosF = (float) this.user.posY;
            this.zPosF = (float) this.user.posZ;

            if (this.fadeOut < 0 && !JetpackPancakeHippos.getJetpackStates().containsKey(this.user.getEntityId())) {
                this.fadeOut = 0;
                playingFor.remove(this.user.getEntityId());
            } else if (this.fadeOut >= 5) {
                this.donePlaying = true;
            } else if (this.fadeOut >= 0) {
                this.volume = 1.0F - this.fadeOut / 5F;
                this.fadeOut++;
            }
        }
    }
}
