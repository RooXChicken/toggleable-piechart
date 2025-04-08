package org.loveroo.toggleablepiechart.screen;

import org.loveroo.toggleablepiechart.ToggleablepiechartClient;
import org.loveroo.toggleablepiechart.event.DrawPieChart;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ConfigurePieChart extends Screen {
    private DrawPieChart pieChartRenderer;
    private boolean wasToggled = false;

    private int oldMouseX = Integer.MIN_VALUE;
    private int oldMouseY = Integer.MIN_VALUE;

    private boolean positioning = false;
    private boolean scaling = false;

    public ConfigurePieChart(DrawPieChart _chart) {
        super(Text.of("Configure PieChart"));

        pieChartRenderer = _chart;
        wasToggled = ToggleablepiechartClient.piechartToggled;
        ToggleablepiechartClient.piechartToggled = true;
    }

    private boolean isInChart(int _mouseX, int _mouseY) {
        float _scale = (float)client.getWindow().getScaleFactor() * ToggleablepiechartClient.scale;
        int _width = (int)(client.getWindow().getWidth()/_scale + ToggleablepiechartClient.posX);
        int _height = (int)(client.getWindow().getHeight()/_scale + ToggleablepiechartClient.posY);

        return (
            _mouseX > _width-225 && _mouseX < _width-6 &&
            _mouseY > _height-239 && _mouseY < _height-1
        );
    }

    @Override
    public boolean mouseClicked(double _mouseX, double _mouseY, int _button) {
        if(_button == 0)
            positioning = true;
        else if(_button == 1)
            scaling = true;

        return super.mouseClicked(_mouseX, _mouseY, _button);
    }

    @Override
    public boolean mouseReleased(double _mouseX, double _mouseY, int _button) {
        if(_button == 0)
            positioning = false;
        else if(_button == 1)
            scaling = false;

        return super.mouseReleased(_mouseX, _mouseY, _button);
    }
    
    @Override
	public void render(DrawContext _context, int _mouseX, int _mouseY, float _delta) {
		super.render(_context, _mouseX, _mouseY, _delta);

        int _scale = (int)client.getWindow().getScaleFactor();
        int _width = client.getWindow().getWidth()/_scale;
        int _height = client.getWindow().getHeight()/_scale;

        // check if they haven't been initialized yet
        if(oldMouseX == Integer.MIN_VALUE) oldMouseX = _mouseX;
        if(oldMouseY == Integer.MIN_VALUE) oldMouseY = _mouseY;

        handlePositioning(_mouseX, _mouseY);
        handleScaling(_mouseX, _mouseY);

        oldMouseX = _mouseX;
        oldMouseY = _mouseY;

		pieChartRenderer.render(_context, RenderTickCounter.ZERO, true);

        MatrixStack _stack = _context.getMatrices();
        _stack.push();
        _stack.translate(ToggleablepiechartClient.posX, ToggleablepiechartClient.posY, 0);
        _stack.scale(ToggleablepiechartClient.scale, ToggleablepiechartClient.scale, ToggleablepiechartClient.scale);

        int _heightFactor = (9 * DrawPieChart.getPathCount()) + 131;

        _context.drawHorizontalLine(_width-225, _width-6, _height-_heightFactor, 0xFFFFFFFF);
        _context.drawHorizontalLine(_width-225, _width-6, _height-1, 0xFFFFFFFF);

        _context.drawVerticalLine(_width-225, _height-_heightFactor, _height-1, 0xFFFFFFFF);
        _context.drawVerticalLine(_width-6, _height-_heightFactor, _height-1, 0xFFFFFFFF);

        setTooltip(Text.of("Left click to move\nRight click to scale"));

        _stack.pop();
	}

    private void handlePositioning(int _mouseX, int _mouseY) {
        if(positioning) {
            ToggleablepiechartClient.posX -= oldMouseX - _mouseX;
            ToggleablepiechartClient.posY -= oldMouseY - _mouseY;
        }
    }

    private void handleScaling(int _mouseX, int _mouseY) {
        if(scaling) {
            ToggleablepiechartClient.scale -= (oldMouseX - _mouseX)/100.0;

            ToggleablepiechartClient.posX += (oldMouseX - _mouseX) * 4;
            ToggleablepiechartClient.posY += (oldMouseX - _mouseX) * 4;
        }
    }

    @Override
    public void close() {
        ToggleablepiechartClient.piechartToggled = wasToggled;
        super.close();
    }
}
