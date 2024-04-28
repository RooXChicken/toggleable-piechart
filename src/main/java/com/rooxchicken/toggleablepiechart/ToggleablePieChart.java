package com.rooxchicken.toggleablepiechart;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.InfestedBlock;
import net.minecraft.client.MinecraftClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToggleablePieChart implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("toggleable-piechart");
    
    public static boolean renderPieChart = false;
    public static boolean renderingPieChart = false;
    public static float PieChartScale = 1;
    public static float OldPieChartScale = 1;
    public static int PieChartPositionX = 0;
    public static int PieChartPositionY = 0;
    
    public static double PieChartX = -1;
    public static double PieChartY = -1;
    public static int PieChartScaleX = 0;
    public static int PieChartScaleY = 0;
    
    public static int OldWindowSizeX = -1;
    public static int OldWindowSizeY = -1;
    public static boolean MovePieChart = false;
    
    public static int windowIndex = 0;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Allowing you to toggle the PieChart since 1987");
	}
	
	public static void UpdateValues()
	{
		MinecraftClient client = MinecraftClient.getInstance();
		
		ToggleablePieChart.PieChartX *= ToggleablePieChart.OldPieChartScale / ToggleablePieChart.PieChartScale;
		ToggleablePieChart.PieChartY *= (ToggleablePieChart.OldPieChartScale / ToggleablePieChart.PieChartScale);
		
		//LOGGER.info("" + ToggleablePieChart.PieChartPositionY);
		
		ToggleablePieChart.OldPieChartScale = ToggleablePieChart.PieChartScale;

		ToggleablePieChart.PieChartScaleX = (int)(client.getWindow().getFramebufferWidth() / ToggleablePieChart.PieChartScale);
		ToggleablePieChart.PieChartScaleY = (int)(client.getWindow().getFramebufferHeight() / ToggleablePieChart.PieChartScale);
		
		double scalingFactor = MinecraftClient.getInstance().getWindow().getScaleFactor();
		
		ToggleablePieChart.PieChartPositionX = (int)(ToggleablePieChart.PieChartX);
		ToggleablePieChart.PieChartPositionY = (int)(ToggleablePieChart.PieChartY - 
				(((ToggleablePieChart.PieChartPositionY-120)/scalingFactor*ToggleablePieChart.PieChartScale) - (ToggleablePieChart.PieChartPositionY/scalingFactor*ToggleablePieChart.PieChartScale)));
	}
}






