package com.rooxchicken.toggleablepiechart.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.rooxchicken.toggleablepiechart.ToggleablePieChart;

@Mixin(MinecraftClient.class)
public class DebugPieChart {
	@Inject(method = "shouldMonitorTickDuration()Z", at = @At("HEAD"), cancellable = true)
	private void init(CallbackInfoReturnable<Boolean> info) {
		MinecraftClient client = MinecraftClient.getInstance();
		if(client != null)
			info.setReturnValue(ToggleablePieChart.renderPieChart && !client.options.hudHidden);
    }
}