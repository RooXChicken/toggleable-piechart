package org.loveroo.toggleablepiechart.event;

import java.util.HashMap;

import org.loveroo.toggleablepiechart.Toggleablepiechart;
import org.loveroo.toggleablepiechart.ToggleablepiechartClient;
import org.loveroo.toggleablepiechart.mixin.PieChartAccessor;
import org.loveroo.toggleablepiechart.mixin.PieChartCountAccessor;
import org.loveroo.toggleablepiechart.screen.ConfigurePieChart;

import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.fabricmc.fabric.api.client.rendering.v1.LayeredDrawerWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.debug.PieChart;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.ProfileResult;

public class DrawPieChart implements HudLayerRegistrationCallback {
    private static HashMap<String, Integer> pathIndexRememberence; // used to store where the player last left off at a certain path
    private static int index = 1;

    private static boolean isMoving = false;

    @Override
    public void register(LayeredDrawerWrapper layeredDrawer) {
        pathIndexRememberence = new HashMap<String, Integer>();

        layeredDrawer.attachLayerAfter(IdentifiedLayer.MISC_OVERLAYS, Identifier.of(Toggleablepiechart.MOD_ID, "draw_piechart"), 
            (_context, _tickCounter) -> {
                render(_context, _tickCounter, false);
            }
        );
    }

    public void render(DrawContext _context, RenderTickCounter _tickCounter, boolean _fromScreen) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;

        if(client.currentScreen instanceof ConfigurePieChart && !_fromScreen) {
            return;
        }

        if(ToggleablepiechartClient.piechartToggled) {
            PieChart _chart = ((PieChartAccessor)client.getDebugHud()).getPieChart();

            int _scale = (int)client.getWindow().getScaleFactor();
            int _width = client.getWindow().getWidth()/_scale;
            int _height = client.getWindow().getHeight()/_scale;

            int _count = getPathCount()-1;

            // scale piechart
            MatrixStack _stack = _context.getMatrices();
            _stack.push();
            _stack.translate(ToggleablepiechartClient.posX, ToggleablepiechartClient.posY, 0);
            _stack.scale(ToggleablepiechartClient.scale, ToggleablepiechartClient.scale, ToggleablepiechartClient.scale);

            _chart.render(_context);
            _context.drawText(textRenderer, Text.of(">"), _width - (228), _height - (_count*9 - 4) + 9*(index-2), 0xFFFFFFFF, true);
            
            _stack.pop();
        }
    }

    public static void move(int _step) {
        if(!ToggleablepiechartClient.piechartToggled) return;

        if(index + _step <= 0 || index + _step > getPathCount()-1) {
            return;
        }

        index += _step;
    }

    public static void select() {
        if(!ToggleablepiechartClient.piechartToggled) return;

        MinecraftClient client = MinecraftClient.getInstance();
        PieChart _chart = ((PieChartAccessor)client.getDebugHud()).getPieChart();

        // store where we currently are in the chart
        pathIndexRememberence.put(((PieChartCountAccessor)_chart).getCurrentPath(), index);
        
        isMoving = true;
        _chart.select(index);
        isMoving = false;

        index = 1;
    }

    public static void back() {
        if(!ToggleablepiechartClient.piechartToggled) return;

        MinecraftClient client = MinecraftClient.getInstance();
        PieChart _chart = ((PieChartAccessor)client.getDebugHud()).getPieChart();

        // even though you can't go back on root, it will mess with the path rememberence code
        if(((PieChartCountAccessor)_chart).getCurrentPath().equals("root")) {
            return;
        }

        isMoving = true;
        _chart.select(0);
        isMoving = false;

        String _path = ((PieChartCountAccessor)_chart).getCurrentPath();
        if(pathIndexRememberence.containsKey(_path)) {
            // return to where we were
            index = pathIndexRememberence.get(_path);
        }
        else {
            index = 1;
        }
    }

    public static int getPathCount() {
        MinecraftClient client = MinecraftClient.getInstance();
        PieChartCountAccessor _chart = (PieChartCountAccessor)((PieChartAccessor)client.getDebugHud()).getPieChart();

        if(_chart.getProfileResult() == null || _chart.getCurrentPath() == null) {
            return 0;
        }

        return _chart.getProfileResult().getTimings(_chart.getCurrentPath()).size();
    }

    public static boolean isMoving() {
        return isMoving;
    }
}
