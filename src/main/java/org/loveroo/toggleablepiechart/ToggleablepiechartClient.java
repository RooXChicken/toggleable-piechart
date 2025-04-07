package org.loveroo.toggleablepiechart;

import org.loveroo.toggleablepiechart.event.DrawPieChart;
import org.loveroo.toggleablepiechart.event.KeybindHandler;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;

public class ToggleablepiechartClient implements ClientModInitializer {
	private KeybindHandler keybindHandler;
	private DrawPieChart pieChartRenderer;
	
	public static boolean piechartToggled = false;

	public static double posX = 0.0;
	public static double posY = 0.0;
	public static float scale = 1.0f;

	@Override
	public void onInitializeClient() {
		pieChartRenderer = new DrawPieChart();
		HudLayerRegistrationCallback.EVENT.register(pieChartRenderer);
		
		keybindHandler = new KeybindHandler(pieChartRenderer);
	}
}