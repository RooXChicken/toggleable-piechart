package org.loveroo.toggleablepiechart.event;

import java.util.HashMap;

import net.minecraft.client.render.RenderLayer;
import org.loveroo.toggleablepiechart.PieChart;
import org.loveroo.toggleablepiechart.client.PieChartClient;
import org.loveroo.toggleablepiechart.mixin.PieChartAccessor;
import org.loveroo.toggleablepiechart.mixin.PieChartCountAccessor;
import org.loveroo.toggleablepiechart.screen.ConfigurePieChart;

import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.fabricmc.fabric.api.client.rendering.v1.LayeredDrawerWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class DrawPieChart implements HudLayerRegistrationCallback {

    private static final Identifier cursorTexture = Identifier.of(PieChart.MOD_ID, "textures/gui/cursor.png");

    // used to store where the player last left off at a certain path
    private static final HashMap<String, Integer> pathIndexRemembrance = new HashMap<>();
    private static boolean isMoving = false;

    private static int index = 0;

    private final int xOffset = 5;
    private final int yOffset = 0;
    private final int caretStart = 6;

    @Override
    public void register(LayeredDrawerWrapper layeredDrawer) {
        layeredDrawer.attachLayerAfter(IdentifiedLayer.MISC_OVERLAYS, Identifier.of(PieChart.MOD_ID, "draw_piechart"),
            (context, tickCounter) -> {
                render(context, tickCounter, false);
            }
        );
    }

    private static boolean isConfig() {
        var client = MinecraftClient.getInstance();
        return client.currentScreen instanceof ConfigurePieChart;
    }

    public void render(DrawContext context, RenderTickCounter tickCounter, boolean fromScreen) {
        var client = MinecraftClient.getInstance();
        var isInConfig = isConfig();

        if(isInConfig && !fromScreen) {
            return;
        }

        if(!isInConfig && !PieChartClient.transform.isToggled()) {
            return;
        }

        var pieChart = getPieChart();
        var lineCount = getPathCount();

        var screenWidth = client.getWindow().getScaledWidth();
        var screenHeight = client.getWindow().getScaledHeight();

        var posX = (int)Math.round(PieChartClient.transform.getPosX());
        var posY = (int)Math.round(PieChartClient.transform.getPosY());

        var scale = (float)PieChartClient.transform.getScale();

        // scale pie chart
        var matrix = context.getMatrices();
        matrix.push();

        matrix.translate(posX, posY, 0);
        matrix.scale(scale, scale, 1.0f);

        var chartX = -(screenWidth - PieChartClient.transform.getWidth()) + xOffset;
        var chartY = -(screenHeight - PieChartClient.transform.getHeight(lineCount));

        matrix.translate(chartX, chartY + yOffset, 0.0);
        pieChart.render(context);

        matrix.pop();
        matrix.push();

        matrix.translate(posX, posY, 0);
        matrix.scale(scale, scale, 1.0f);

        var caretY = PieChartClient.transform.getRawHeight() - caretStart + (index * PieChartClient.transform.heightPerEntry);
        context.drawTexture(RenderLayer::getGuiTextured, cursorTexture, 0, caretY + 1, 0, 0, 7, 7, 7, 7, 0xFFFFFFFF);

        matrix.pop();
    }

    public static boolean isChartShown() {
        return isConfig() || PieChartClient.transform.isToggled();
    }

    public static void move(int step) {
        if(!PieChartClient.transform.isToggled()) {
            return;
        }

        var newStep = index + step;

        if(newStep < 0 || newStep >= getPathCount()-1) {
            return;
        }

        index = newStep;
    }

    public static void select() {
        if(!PieChartClient.transform.isToggled())  {
            return;
        }

        var pieChart = getPieChart();

        isMoving = true;
        pieChart.select(index+1);
        isMoving = false;

        // store where we currently are in the chart
        pathIndexRemembrance.put(getCurrentPath(), index);

        index = 0;
    }

    public static void back() {
        if(!PieChartClient.transform.isToggled()) {
            return;
        }

        var pieChart = getPieChart();
        var currentPath = getCurrentPath();

        // even though you can't go back on root, it will mess with the path remembrance code
        if(currentPath.equals("root")) {
            return;
        }

        isMoving = true;
        pieChart.select(0);
        isMoving = false;

        // return to where we were
        index = pathIndexRemembrance.getOrDefault(currentPath, 0);
    }

    public static int getPathCount() {
        var pieChart = getCountAccessor();

        if(pieChart.getProfileResult() == null || getCurrentPath().isEmpty()) {
            return 0;
        }

        return pieChart.getProfileResult().getTimings(pieChart.getCurrentPath()).size();
    }

    public static String getCurrentPath() {
        var path = getCountAccessor().getCurrentPath();
        return (path == null) ? "" : path;
    }

    public static net.minecraft.client.gui.hud.debug.PieChart getPieChart() {
        var client = MinecraftClient.getInstance();
        return ((PieChartAccessor)client.getDebugHud()).getPieChart();
    }

    public static PieChartCountAccessor getCountAccessor() {
        return (PieChartCountAccessor)getPieChart();
    }

    public static boolean isMoving() {
        return isMoving;
    }
}
