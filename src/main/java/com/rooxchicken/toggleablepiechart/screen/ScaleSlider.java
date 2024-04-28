package com.rooxchicken.toggleablepiechart.screen;

import com.rooxchicken.toggleablepiechart.ToggleablePieChart;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ScaleSlider extends SliderWidget {
    public ScaleSlider(int x, int y, int width, int height, double value) {
        super(x, y, width, height, sliderMessage(), value);
    }

    @Override
    protected void updateMessage() {
        setMessage(sliderMessage());
    }

    @Override
    protected void applyValue()
    {
    	ToggleablePieChart.PieChartScale = (float)Math.max(0.1, Math.min(4, value*4));
    }
    
    private static Text sliderMessage()
    {
    	return Text.of(String.format("Scale %.1f", ToggleablePieChart.PieChartScale) + "%");
    }
}