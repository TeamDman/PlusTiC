package colonelgerdauf.plustic.util;

import java.util.*;
import java.util.concurrent.*;

import com.google.common.collect.*;

import colonelgerdauf.plustic.api.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.modifiers.*;

@Mod.EventBusSubscriber(modid = ModInfo.MODID)
public class ModifierRegisterPromise extends CompletableFuture<IModifier> {
	private static final Multimap<String, ModifierRegisterPromise> promises = MultimapBuilder.hashKeys().arrayListValues().build();
	
	private final String identifier;
	
	public ModifierRegisterPromise(String identifier) {
		this.identifier = identifier;
		
		this.whenComplete((modifier, ex) -> promises.remove(this.identifier, this));
		
		final IModifier modifier = TinkerRegistry.getModifier(this.identifier);
		if (modifier != null) {
			this.complete(modifier);
		} else {
			promises.put(this.identifier, this);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onModifierRegister(TinkerRegisterEvent.ModifierRegisterEvent event) {
		new ArrayList<>(promises.get(event.getRecipe().getIdentifier()))
		.forEach(promise -> promise.complete(event.getRecipe()));
	}
}
