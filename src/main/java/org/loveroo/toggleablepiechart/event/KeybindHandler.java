package org.loveroo.toggleablepiechart.event;

import org.loveroo.toggleablepiechart.ToggleablepiechartClient;
import org.loveroo.toggleablepiechart.screen.ConfigurePieChart;
import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeybindHandler {
    private DrawPieChart pieChartRenderer;

    private static final String category = "key.category.piechart";
    private static KeyBinding toggleChartBind;

    private static KeyBinding moveUp;
    private static KeyBinding moveDown;
    private static KeyBinding select;
    private static KeyBinding back;
    private static KeyBinding configure;

    public KeybindHandler(DrawPieChart _chart) {
        pieChartRenderer = _chart;
        
        toggleChartBind = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.piechart.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_P, category));

        moveUp = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.piechart.move_up", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UP, category));
        moveDown = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.piechart.move_down", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, category));

        select = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.piechart.select", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_ENTER, category));
        back = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.piechart.move_back", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_BACKSPACE, category));

        configure = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.piechart.config", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_O, category));

        ClientTickEvents.END_CLIENT_TICK.register((_client) -> {
            if(toggleChartBind.wasPressed()) {
                ToggleablepiechartClient.piechartToggled = !ToggleablepiechartClient.piechartToggled;
            }

            while(moveUp.wasPressed()) {
                DrawPieChart.move(-1);
            }
            while(moveDown.wasPressed()) {
                DrawPieChart.move(1);
            }

            while(select.wasPressed()) {
                DrawPieChart.select();
            }
            while(back.wasPressed()) {
                DrawPieChart.back();
            }

            if(configure.wasPressed()) {
                _client.setScreen(new ConfigurePieChart(pieChartRenderer));
            }
        });
    }
}
