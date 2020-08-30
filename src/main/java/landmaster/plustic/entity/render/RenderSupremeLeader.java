package landmaster.plustic.entity.render;

import landmaster.plustic.ModInfo;
import landmaster.plustic.entity.EntitySupremeLeader;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class RenderSupremeLeader extends RenderLiving<EntitySupremeLeader> {
    public static final ResourceLocation tex = new ResourceLocation(ModInfo.MODID,"textures/entity/supremeleader.png");

    public RenderSupremeLeader(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelCreeper(), 0.5F);
        this.addLayer(new LayerSupremeLeaderCharge(this));
    }

    @Override
    protected void preRenderCallback(EntitySupremeLeader entityLivingBaseIn, float partialTickTime) {
        float f = entityLivingBaseIn.getCreeperFlashIntensity(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        GlStateManager.scale(f2, f3, f2);
    }

    @Override
    protected int getColorMultiplier(EntitySupremeLeader entityLivingBaseIn, float lightBrightness, float partialTickTime) {
        float f = entityLivingBaseIn.getCreeperFlashIntensity(partialTickTime);

        if ((int) (f * 10.0F) % 2 == 0) {
            return 0;
        } else {
            int i = (int) (f * 0.2F * 255.0F);
            i = MathHelper.clamp(i, 0, 255);
            return i << 24 | 0x30FFFFFF;
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntitySupremeLeader entity) {
        return tex;
    }
}
