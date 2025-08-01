package org.loveroo.toggleablepiechart.data;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.loveroo.toggleablepiechart.PieChart;

public class PieChartTransform {
    private boolean toggled = false;

    private double posX = 0.0;
    private double posY = 0.0;

    private double scale = 1.0;

    private static final int width = 221;
    private final int height = 140;

    public final int heightPerEntry = 9;

    public int[] getPoints(int lines) {
        var x2 = (int)Math.round((getPosX() + (getWidth() * scale)));
        var y2 = (int)Math.round((getPosY() + (getHeight(lines) * scale)));

        return new int[] { (int)Math.round(getPosX()), x2, (int)Math.round(getPosY()), y2 };
    }

    public void load(JSONObject json) {
        toggled = json.optBoolean("toggled", toggled);

        posX = json.optDouble("pos_x", posX);
        posY = json.optDouble("pos_y", posY);

        scale = json.optDouble("scale", scale);
    }

    public void save(JSONObject json) throws JSONException {
        json.put("toggled", toggled);

        json.put("pos_x", posX);
        json.put("pos_y", posY);

        json.put("scale", scale);
    }

    public boolean isPointInside(int x, int y, int lines) {
        var points = getPoints(lines);

        return !(x < points[0] || x > points[1] || y < points[2] || y > points[3]);
    }

    public void handleTransformation(int mouseState, int mouseX, int mouseY, int oldMouseX, int oldMouseY) {
        switch(mouseState) {
            case 0 -> {
                setPosX(getPosX() + (mouseX - oldMouseX));
                setPosY(getPosY() + (mouseY - oldMouseY));
            }

            case 1 -> {
                setScale(getScale() - (oldMouseX - mouseX)/(double)getWidth());
            }
        }
    }

    public void drawOutline(DrawContext context, int lines) {
        var points = getPoints(lines);

        context.drawHorizontalLine(points[0], points[1], points[2], 0xFFFFFFFF);
        context.drawHorizontalLine(points[0], points[1], points[3], 0xFFFFFFFF);

        context.drawVerticalLine(points[0], points[2], points[3], 0xFFFFFFFF);
        context.drawVerticalLine(points[1], points[2], points[3], 0xFFFFFFFF);
    }

    public double getPosX() {
        var client = MinecraftClient.getInstance();
        var width = client.getWindow().getScaledWidth();

        return posX * width;
    }

    public void setPosX(double x) {
        var client = MinecraftClient.getInstance();
        var width = client.getWindow().getScaledWidth();

        posX = (x / width);
    }

    public double getPosY() {
        var client = MinecraftClient.getInstance();
        var height = client.getWindow().getScaledHeight();

        return posY * height;
    }

    public void setPosY(double y) {
        var client = MinecraftClient.getInstance();
        var height = client.getWindow().getScaledHeight();

        posY = (y / height);
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = Math.max(0.1, scale);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight(int lines) {
        return (height + (heightPerEntry * (lines-1)));
    }

    public int getRawHeight() {
        return height;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }
}
