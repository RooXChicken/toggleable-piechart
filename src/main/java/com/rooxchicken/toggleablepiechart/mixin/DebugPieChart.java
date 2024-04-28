package com.rooxchicken.toggleablepiechart.mixin;

import net.minecraft.client.MinecraftClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.rooxchicken.toggleablepiechart.ToggleablePieChart;

@Mixin(MinecraftClient.class)
public class DebugPieChart
{
	@Inject(method = "drawProfilerResults(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/profiler/ProfileResult;)V", at = @At("HEAD"))
	private void drawProfilerResultsHead(CallbackInfo info) {
		ToggleablePieChart.renderingPieChart = true;
		ToggleablePieChart.windowIndex = 0;
    }

	@Inject(method = "drawProfilerResults(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/profiler/ProfileResult;)V", at = @At("TAIL"))
	private void drawProfilerResultsTail(CallbackInfo info) {
		ToggleablePieChart.renderingPieChart = false;
    }
//	@Inject(method = "stop()V", at = @At("HEAD"))
//	private void stop(CallbackInfo info)
//	{
//		ToggleablePieChart.LOGGER.info("Disabling the PieChart");
//		ToggleablePieChart.renderPieChart = false;
//	}

	@Inject(method = "shouldMonitorTickDuration()Z", at = @At("HEAD"), cancellable = true)
	private void shouldMonitorTickDuration(CallbackInfoReturnable<Boolean> info) {
		MinecraftClient client = MinecraftClient.getInstance();

		if(client != null)
			info.setReturnValue(ToggleablePieChart.renderPieChart && !client.options.hudHidden);
		else
			info.setReturnValue(false);
    }
}
