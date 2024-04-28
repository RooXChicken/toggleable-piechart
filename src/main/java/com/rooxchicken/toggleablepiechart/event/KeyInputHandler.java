package com.rooxchicken.toggleablepiechart.event;

import org.lwjgl.glfw.GLFW;

import com.rooxchicken.toggleablepiechart.ToggleablePieChart;
import com.rooxchicken.toggleablepiechart.screen.ConfigurationScreen;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

public class KeyInputHandler
{
	public static final String KEY_CATEGORY_TOGGLEABLEPIECHART = "key.category.toggleable_piechart";
	public static final String KEY_TOGGLE_PIECHART = "key.toggle_piechart";
	public static final String KEY_TOGGLE_SETTINGS = "key.toggle_roo_settings";
	
	public static KeyBinding togglePieChartKey;
	public static KeyBinding toggleRooSettingsKey;
	
	public static void registerKeyInputs()
	{
		ClientTickEvents.END_CLIENT_TICK.register(client ->
		{
			if(togglePieChartKey.wasPressed())
			{
				
				if(ToggleablePieChart.PieChartX == -1)
				{
					ToggleablePieChart.PieChartX = MinecraftClient.getInstance().getWindow().getWidth();
					ToggleablePieChart.PieChartY = MinecraftClient.getInstance().getWindow().getHeight();
				}
				
				ToggleablePieChart.renderPieChart = !ToggleablePieChart.renderPieChart;
			}
			if(toggleRooSettingsKey.wasPressed())
			{
				
				ConfigurationScreen screen = new ConfigurationScreen();
				client.setScreen(screen);
				
				//configOpen = true;
			}
		});
	}
	
	public static void register()
	{
		togglePieChartKey = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(KEY_TOGGLE_PIECHART, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_P, KEY_CATEGORY_TOGGLEABLEPIECHART));
		
		toggleRooSettingsKey = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(KEY_TOGGLE_SETTINGS, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, KEY_CATEGORY_TOGGLEABLEPIECHART));
		
		registerKeyInputs();
	}
}
