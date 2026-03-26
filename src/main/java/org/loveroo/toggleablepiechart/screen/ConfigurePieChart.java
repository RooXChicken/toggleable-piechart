package org.loveroo.toggleablepiechart.screen;

import net.minecraft.client.gui.Click;
import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import org.loveroo.toggleablepiechart.client.PieChartClient;
import org.loveroo.toggleablepiechart.event.DrawPieChart;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.List;

public class ConfigurePieChart extends Screen {

    private DrawPieChart pieChartRenderer;

    private int mouseState = -1;
    private boolean isSelected = false;

    private int mouseX = 0;
    private int mouseY = 0;

    private int oldMouseY = 0;
    private int oldMouseX = 0;

    public ConfigurePieChart(DrawPieChart chart) {
        super(Text.of("Configure PieChart"));

        pieChartRenderer = chart;
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        mouseState = click.button();
        handleClick();

        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseReleased(Click click) {
        mouseState = -1;
        handleClick();

        PieChartClient.saveConfig();

        return super.mouseReleased(click);
    }

    protected void handleClick() {
        switch(mouseState) {
            case 0, 1 -> {
                if(PieChartClient.transform.isPointInside(mouseX, mouseY, DrawPieChart.getPathCount())) {
                    isSelected = true;
                }
            }

            case -1 -> {
                isSelected = false;
            }
        }
    }
    
    @Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);

        this.oldMouseX = this.mouseX;
        this.oldMouseY = this.mouseY;

        this.mouseX = mouseX;
        this.mouseY = mouseY;

        if(isSelected) {
            PieChartClient.transform.handleTransformation(mouseState, mouseX, mouseY, oldMouseX, oldMouseY);
        }

        var lineCount = DrawPieChart.getPathCount();

        PieChartClient.transform.drawOutline(context, lineCount);
		pieChartRenderer.drawChart(context, RenderTickCounter.ZERO, true);

        if(PieChartClient.transform.isPointInside(mouseX, mouseY, lineCount)) {
            context.drawTooltip(client.textRenderer, List.of(Text.of("Left click to move"), Text.of("Right click to scale")), mouseX, mouseY);
        }
	}

    @Override
    public void close() {
        super.close();
    }
}
