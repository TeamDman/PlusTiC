package landmaster.plustic.entity.render;

import javax.annotation.*;

import landmaster.plustic.api.*;
import landmaster.plustic.entity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;

public class RenderBlindBandit extends RenderLiving<EntityBlindBandit> {
	public static final ResourceLocation tex = new ResourceLocation(ModInfo.MODID+":textures/entity/blindbandit.png");
	
	public RenderBlindBandit(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelBlindBandit(), 0.5F);
	}
	
	@Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityBlindBandit ent) {
		return tex;
	}
}
