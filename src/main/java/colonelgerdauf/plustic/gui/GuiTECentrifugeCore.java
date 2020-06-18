package landmaster.plustic.gui;

import java.util.*;

import landmaster.plustic.api.*;
import landmaster.plustic.gui.container.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraftforge.energy.*;

public class GuiTECentrifugeCore extends GuiContainer {
	public static final ResourceLocation TEX = new ResourceLocation(ModInfo.MODID, "textures/gui/centrifuge_core.png");
	public static final ResourceLocation ENERGY_BAR = new ResourceLocation(ModInfo.MODID, "textures/gui/energy_bar.png");
	
	private ContainerTECentrifugeCore cont;
	private InventoryPlayer playerInv;
	
	public GuiTECentrifugeCore(ContainerTECentrifugeCore inventorySlotsIn, InventoryPlayer playerInv) {
		super(inventorySlotsIn);
		this.cont = inventorySlotsIn;
		this.playerInv = playerInv;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(TEX);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
		// Energy stuff
		mc.getTextureManager().bindTexture(ENERGY_BAR);
		IEnergyStorage cap = cont.getTE().getCapability(CapabilityEnergy.ENERGY, null);
		double percentEmpty = ((double)(cap.getMaxEnergyStored()-cap.getEnergyStored())) / cap.getMaxEnergyStored();
		int eX = guiLeft+10, eY = guiTop+25;
		drawTexturedModalRect(eX, eY, 17, 0, 14, 42);
		drawTexturedModalRect(eX, eY, 1, 0, 14, (int)(42*percentEmpty));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n.format("tile.centrifuge.core.name");
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
		fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
		
		int k = (this.width - this.xSize) / 2; //X asis on GUI
		int l = (this.height - this.ySize) / 2; //Y asis on GUI
		int trueX = mouseX-k, trueY = mouseY-l;
		
		// Energy stuff
		IEnergyStorage cap = cont.getTE().getCapability(CapabilityEnergy.ENERGY, null);
		int eX = 10, eY = 25;
		if (isPointInRegion(eX, eY, 14, 42, mouseX, mouseY)) {
			drawHoveringText(Arrays.asList(String.format("%d RF / %d RF",
					cap.getEnergyStored(),
					cap.getMaxEnergyStored())), trueX, trueY);
		}
	}
}
