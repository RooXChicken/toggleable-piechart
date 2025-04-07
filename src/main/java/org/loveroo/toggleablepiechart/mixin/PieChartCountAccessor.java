package org.loveroo.toggleablepiechart.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.hud.debug.PieChart;
import net.minecraft.util.profiler.ProfileResult;

@Mixin(PieChart.class)
public interface PieChartCountAccessor {
    
    @Accessor("profileResult")
    public ProfileResult getProfileResult();

    @Accessor("currentPath")
    public String getCurrentPath();
}
