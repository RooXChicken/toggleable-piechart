package org.loveroo.toggleablepiechart.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.gui.hud.debug.PieChart;

@Mixin(DebugHud.class)
public interface PieChartAccessor {
    
    @Accessor("pieChart")
    public PieChart getPieChart();
}
