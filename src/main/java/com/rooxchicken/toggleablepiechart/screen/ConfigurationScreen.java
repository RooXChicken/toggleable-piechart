package com.rooxchicken.toggleablepiechart.screen;

import java.nio.file.attribute.AclEntry;

import com.mojang.blaze3d.systems.RenderSystem;
import com.rooxchicken.toggleablepiechart.ToggleablePieChart;
import com.rooxchicken.toggleablepiechart.client.ToggleablePieChartClient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionSliderWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ConfigurationScreen extends Screen
{
	private boolean mouseDown = false;
	private boolean oldRender = false;
	//public SliderWidget scaleSlider;
	
	private ScaleSlider slider;
	private ButtonWidget resetButton;
	
	 public ConfigurationScreen()
	 {
		 super(Text.of("Toggleable PieChart Config"));
	 }
	 
	 @Override
	 public void init()
	 {
		 MinecraftClient client = MinecraftClient.getInstance();
		 
		 oldRender = ToggleablePieChart.renderPieChart;
		 ToggleablePieChart.renderPieChart = true;
		 
		 if(ToggleablePieChart.PieChartX == -1)
		{
			ToggleablePieChart.PieChartX = MinecraftClient.getInstance().getWindow().getWidth();
			ToggleablePieChart.PieChartY = MinecraftClient.getInstance().getWindow().getHeight();
		}
	 
		int sliderWidth = 100;
        int sliderHeight = 20;

        // Set the position of the slider
        int sliderX = (width - sliderWidth) / 2;
        int sliderY = 20;
        // Create your custom slider with initial value
        slider = new ScaleSlider(sliderX, sliderY, sliderWidth, sliderHeight, ToggleablePieChart.PieChartScale/0.25);

        resetButton = ButtonWidget.builder(Text.of("Reset PieChart"), button ->
        {
        	ToggleablePieChart.PieChartScale = 1;
        	ToggleablePieChart.UpdateValues();
        	ToggleablePieChart.PieChartX = client.getWindow().getWidth()-10;
        	ToggleablePieChart.PieChartY = client.getWindow().getHeight();
        })
		.dimensions(width / 2 - 50, height-30, 100, 20)
        .tooltip(Tooltip.of(Text.of("Resets the PieChart's Position and Scale")))
        .build();
        
        // Add the slider to the screen's children
        //addDrawableChild(slider);
        addDrawableChild(resetButton);
	 }
	 
	 @Override
	 public void close()
	 {
		 ToggleablePieChart.renderPieChart = oldRender;
		 super.close();
	 }
	 
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (button == 0)
        	ToggleablePieChart.MovePieChart = true;

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button)
    {
        if(button == 0)
        	ToggleablePieChart.MovePieChart = false;

        return super.mouseReleased(mouseX, mouseY, button);
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
    	ToggleablePieChart.UpdateValues();
      super.render(context, mouseX, mouseY, delta);
      context.drawCenteredTextWithShadow(textRenderer, Text.literal("Toggleable PieChart Config"), width / 2, 10, 0xffffff);
      
      double scalingFactor = client.getWindow().getScaleFactor();
      
      int x1 = (int)((ToggleablePieChart.PieChartPositionX)/scalingFactor*ToggleablePieChart.PieChartScale); //RIGHT
      int x2 = (int)((ToggleablePieChart.PieChartPositionX - 340)/scalingFactor*ToggleablePieChart.PieChartScale); //LEFT
      
      int y1 = (int)((ToggleablePieChart.PieChartPositionY-120)/scalingFactor*ToggleablePieChart.PieChartScale); //DOWN
      int y2 = (int)((ToggleablePieChart.PieChartPositionY-420)/scalingFactor*ToggleablePieChart.PieChartScale); //UP
      
      if(ToggleablePieChartClient.scaleChart && ToggleablePieChart.MovePieChart)
    	  context.fill(x2, y2, x2+8, y2+8, 0xFFFF00E4 );
      else
    	  context.fill(x2, y2, x2+8, y2+8, 0xFFFFFFFF);
      
      context.drawHorizontalLine(x1, x2, y1, 0xFFFFFFFF);
      context.drawHorizontalLine(x1, x2, y2, 0xFFFFFFFF);
      
      context.drawVerticalLine(x1, y1, y2, 0xFFFFFFFF);
      context.drawVerticalLine(x2, y1, y2, 0xFFFFFFFF);
      
      //int origin = (int)(ToggleablePieChart.PieChartPositionX/scalingFactor*ToggleablePieChart.PieChartScale);
      //context.drawHorizontalLine(origin, origin, (int)(ToggleablePieChart.PieChartPositionY/scalingFactor*ToggleablePieChart.PieChartScale), 0xFFFFFFFF);
    }
}
