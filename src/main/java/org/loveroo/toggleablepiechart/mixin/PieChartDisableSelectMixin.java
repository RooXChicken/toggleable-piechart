package org.loveroo.toggleablepiechart.mixin;

import net.minecraft.client.gui.hud.debug.chart.PieChart;
import org.loveroo.toggleablepiechart.event.DrawPieChart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.profiler.ProfileResult;

@Mixin(PieChart.class)
public class PieChartDisableSelectMixin {

    @Inject(method = "select(I)V", at = @At(value = "HEAD"), cancellable = true)
    public void preventMoving(CallbackInfo info) {
        // rather hacky way of doing this but i would rather do this than spend an hour or two fighting with mixins
        if(!DrawPieChart.isMoving()) {
            info.cancel();
        }
    }
}
