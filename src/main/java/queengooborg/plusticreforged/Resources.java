package queengooborg.plusticreforged;

import queengooborg.plusticreforged.api.Material;
import queengooborg.plusticreforged.api.Modifier;
import queengooborg.plusticreforged.materials.*;
import queengooborg.plusticreforged.modifiers.*;

public class Resources {
	public static final Modifier[] MODIFIERS = new Modifier[]{
			new HeavyModifier(),
			new InvulnerableModifier(),
			new BloodyMaryModifier(),
			new NaturesBlessingModifier(),
			new JadedModifier(),
			new NaturesWrathModifier(),
			new NaturesPowerModifier(),
			new ApocalypseModifier(),
			new FromTheAshesModifier(),
			new ExplosiveModifier(),
			new DarkTravelerModifier(),
			new IgnobleModifier(),
			new UnstableMatterModifier(),
			new PsicologicalModifier(),
			new GlobalModifier(),
			new PortlyModifier(),
			new ElementalModifier(),
			new ManaModifier(),
			new TerrafirmaModifier(),
	};

	public static final Material[] MATERIALS = new Material[]{
			// ModuleBase + New
			new BedrockMaterial(),
			new TNTMaterial(),

			// ModuleGems + ModuleGemsPlus
			new RubyMaterial(),
			new SapphireMaterial(),
			new PeridotMaterial(),
			new JadeMaterial(),
			new MalachiteMaterial(),
			new TopazMaterial(),
			new AmethystMaterial(),
			new PhoenixiteMaterial(),

			// ModuleProjectE
			new DarkMatterMaterial(),
			new RedMatterMaterial(),

			// ModulePsi
			new PsimetalMaterial(),
			new PsigemMaterial(),

			// ModuleAppEng2
			new CertusQuartzMaterial(),
			new FluixMaterial(),

			// ModuleBotania
			new ManasteelMaterial(),
			new TerrasteelMaterial(),
			new ElementiumMaterial(),
			// new LivingwoodMaterial(), -- botania:livingwood
			// new MirionMaterial(), -- custom alloy

			// ModuleIndusForego
			// new PinkSlimeMaterial(), -- unsure how to implement yet

			// ModuleArmorPlus
			// new WitherBoneMaterial(), -- armorplus:wither_bone
			// new GuardianScaleMaterial(), -- armorplus:guardian_scale

			// ModuleDraconicEvolution -- unavailable in MC 1.19
			// XXX Original used three of four cores, but we should use the ingots instead
			// new DraconiumMaterial(), -- Forge tags: forge:ingots/draconium
			// new AwakenedDraconiumMaterial(), -- Forge tags: forge:ingots/draconium_awakened
	};

	public static Modifier getModifier(String id) {
		for (Modifier modifier : MODIFIERS) {
			if (modifier.id.equals(id)) {
				return modifier;
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	public static Material getMaterial(String id) {
		for (Material material : MATERIALS) {
			if (material.id.equals(id)) {
				return material;
			}
		}
		return null;
	}
}
