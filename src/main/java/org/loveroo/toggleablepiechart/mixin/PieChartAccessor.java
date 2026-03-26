package org.loveroo.toggleablepiechart.mixin;

import net.minecraft.client.gui.hud.debug.chart.PieChart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.hud.DebugHud;

@Mixin(DebugHud.class)
public interface PieChartAccessor {
    
    @Accessor("pieChart")
    PieChart getPieChart();
}
