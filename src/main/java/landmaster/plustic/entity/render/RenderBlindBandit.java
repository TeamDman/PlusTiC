package landmaster.plustic.entity.render;

import landmaster.plustic.entity.EntityBlindBandit;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class RenderBlindBandit extends RenderLiving<EntityBlindBandit> {
    public static final ResourceLocation tex = new ResourceLocation(ModInfo.MODID + ":textures/entity/blindbandit.png");

    public RenderBlindBandit(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelBlindBandit(), 0.5F);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityBlindBandit ent) {
        return tex;
    }
}
