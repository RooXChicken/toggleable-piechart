package com.rooxchicken.toggleablepiechart.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.rooxchicken.toggleablepiechart.ToggleablePieChart;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

@Mixin(Window.class)
public class WindowModification {
	@Inject(method = "getFramebufferWidth()I", at = @At("HEAD"), cancellable = true)
	private void getFramebufferWidth(CallbackInfoReturnable<Integer> info) {
		if(ToggleablePieChart.renderingPieChart)
		{
			ToggleablePieChart.windowIndex++;
			if(ToggleablePieChart.windowIndex > 2)
				info.setReturnValue(ToggleablePieChart.PieChartPositionX);
			else
				info.setReturnValue(ToggleablePieChart.PieChartScaleX);
		}
    }
	
	@Inject(method = "getFramebufferHeight()I", at = @At("HEAD"), cancellable = true)
	private void getFramebufferHeight(CallbackInfoReturnable<Integer> info) {
		if(ToggleablePieChart.renderingPieChart)
		{
			ToggleablePieChart.windowIndex++;
			if(ToggleablePieChart.windowIndex > 2)
				info.setReturnValue(ToggleablePieChart.PieChartPositionY);
			else
				info.setReturnValue(ToggleablePieChart.PieChartScaleY);
		}
    }
	@Inject(method = "onWindowSizeChanged(JII)V", at = @At("HEAD"), cancellable = true)
	private void onWindowSizeChanged(long window, int width, int height, CallbackInfo info)
	{
		if(ToggleablePieChart.OldWindowSizeX == -1)
		{
			ToggleablePieChart.OldWindowSizeX = width;
			ToggleablePieChart.OldWindowSizeY = height;
		}
		
		ToggleablePieChart.PieChartX *= (width + 0.0) / ToggleablePieChart.OldWindowSizeX;
		ToggleablePieChart.PieChartY *= (height + 0.0) / ToggleablePieChart.OldWindowSizeY;
		
		ToggleablePieChart.OldWindowSizeX = width;
		ToggleablePieChart.OldWindowSizeY = height;
	}
}








