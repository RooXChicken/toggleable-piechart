package com.rooxchicken.toggleablepiechart.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.rooxchicken.toggleablepiechart.ToggleablePieChart;
import com.rooxchicken.toggleablepiechart.client.ToggleablePieChartClient;

@Mixin(DebugHud.class)
public class DebugHudMixin
{	
	@Inject(method = "shouldShowRenderingChart()Z", at = @At("HEAD"), cancellable = true)
	private void shouldShowRenderingChart(CallbackInfoReturnable<Boolean> info) {
		MinecraftClient client = MinecraftClient.getInstance();
		
		if(client != null)
		{
			//ToggleablePieChartClient.movePieChartLogic();
			
			info.setReturnValue(ToggleablePieChart.renderPieChart && !client.options.hudHidden);
		}
		else
			info.setReturnValue(false);
    }
}