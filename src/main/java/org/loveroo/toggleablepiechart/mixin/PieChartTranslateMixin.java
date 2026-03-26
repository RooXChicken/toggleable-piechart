package org.loveroo.toggleablepiechart.mixin;

import net.minecraft.client.gui.hud.debug.chart.PieChart;
import org.loveroo.toggleablepiechart.client.PieChartClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PieChart.class)
public abstract class PieChartTranslateMixin {

    @ModifyArgs(method = "render(Lnet/minecraft/client/gui/DrawContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;addProfilerChart(Ljava/util/List;IIII)V"))
    public void addProfilerChart(Args args) {
        var points = PieChartClient.transform.getPoints(0);
        args.set(1, points[0]);
        args.set(2, points[2]);
        args.set(3, points[1]);
        args.set(4, points[3]);
    }
}
