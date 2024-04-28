package com.rooxchicken.toggleablepiechart.client;

import com.rooxchicken.toggleablepiechart.ToggleablePieChart;
import com.rooxchicken.toggleablepiechart.event.KeyInputHandler;

//import com.rooxchicken.truehealth.networking.PingTester;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;

public class ToggleablePieChartClient implements ClientModInitializer
{
	private double oldMouseX = -1;
	private double oldMouseY = -1;
	
	private double scaleX = -1;
	private double scaleY = -1;
	
	private double oldScale = -1;
	
	private double length = -1;
	
	private boolean moveChart = false;
	public static boolean scaleChart = false;
	@Override
	public void onInitializeClient()
	{	
		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			ToggleablePieChart.renderPieChart = false;
		});
		
//		ClientTickEvents.END_CLIENT_TICK.register(client -> {
//			
//			ToggleablePieChart.PieChartScaleX = (int)(client.getWindow().getFramebufferWidth() / ToggleablePieChart.PieChartScale);
//			ToggleablePieChart.PieChartScaleY = (int)(client.getWindow().getFramebufferHeight() / ToggleablePieChart.PieChartScale);
//		});
		
		WorldRenderEvents.END.register((context) -> {
			HandlePieChart();
		});
		
		KeyInputHandler.register();
	}
	
	public void HandlePieChart()
	{
		MinecraftClient client = MinecraftClient.getInstance();
		
		if(!ToggleablePieChart.MovePieChart)
		{
			moveChart = false;
			ResetOldClick();
			return;
		}
		
		double scalingFactor = client.getWindow().getScaleFactor();
		
		double mouseX = client.mouse.getX()/scalingFactor;
		double mouseY = client.mouse.getY()/scalingFactor;
	      
	      int x1 = (int)((ToggleablePieChart.PieChartPositionX)/scalingFactor*ToggleablePieChart.PieChartScale); //RIGHT
	      int x2 = (int)((ToggleablePieChart.PieChartPositionX - 340)/scalingFactor*ToggleablePieChart.PieChartScale); //LEFT
	      
	      int y1 = (int)((ToggleablePieChart.PieChartPositionY-120)/scalingFactor*ToggleablePieChart.PieChartScale); //DOWN
	      int y2 = (int)((ToggleablePieChart.PieChartPositionY-420)/scalingFactor*ToggleablePieChart.PieChartScale); //UP
	      
	      //ToggleablePieChart.LOGGER.info("" + ToggleablePieChart.PieChartPositionX);
		
		//aabb check 
	      //x2, y2, x2+8, y2+8
	      
		if(!moveChart)
		{
			if(mouseX > x2 && mouseX < x1 && mouseY < y1 && mouseY > y2)
			{
				moveChart = true;
				scaleChart = false;
				scaleX = mouseX;
				scaleY = mouseY;
				oldScale = ToggleablePieChart.PieChartScale;
				
				length = x1-x2;
			}
			else
			{
				moveChart = false;
				ToggleablePieChart.MovePieChart = false;
				
				return;
			}
			
			if(mouseX > x2 && mouseX < x2+8 && mouseY > y2 && mouseY < y2+8)
		      {
		    	  scaleChart = true;
		      }
		}
		
		if(moveChart && !scaleChart)
		{
			ToggleablePieChart.PieChartX += ((mouseX - oldMouseX) * 1/ToggleablePieChart.PieChartScale) * scalingFactor;
			ToggleablePieChart.PieChartY += ((mouseY - oldMouseY) * 1/ToggleablePieChart.PieChartScale) * scalingFactor;
		}
		
		if(scaleChart)
			ToggleablePieChart.PieChartScale = (float)oldScale * (float)((length-Math.min(mouseX - scaleX, mouseY - scaleY))/length);
		
		ResetOldClick();
	}
	
	private void ResetOldClick()
	{
		MinecraftClient client = MinecraftClient.getInstance();
		double scalingFactor = client.getWindow().getScaleFactor();
		
		oldMouseX = client.mouse.getX()/scalingFactor;
		oldMouseY = client.mouse.getY()/scalingFactor;
	}
}