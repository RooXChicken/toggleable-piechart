package com.rooxchicken.toggleablepiechart.event;

import org.lwjgl.glfw.GLFW;

import com.rooxchicken.toggleablepiechart.ToggleablePieChart;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeyInputHandler
{
	public static final String KEY_CATEGORY_TOGGLEABLEPIECHART = "key.category.toggleable_piechart";
	public static final String KEY_TOGGLE_PIECHART = "key.toggle_piechart";
	
	public static KeyBinding togglePieChartKey;
	
	public static void registerKeyInputs()
	{
		ClientTickEvents.END_CLIENT_TICK.register(client ->
		{
			if(togglePieChartKey.wasPressed())
			{
				ToggleablePieChart.renderPieChart = !ToggleablePieChart.renderPieChart;
			}
		});
	}
	
	public static void register()
	{
		togglePieChartKey = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(KEY_TOGGLE_PIECHART, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_P, KEY_CATEGORY_TOGGLEABLEPIECHART));
		
		registerKeyInputs();
	}
}
