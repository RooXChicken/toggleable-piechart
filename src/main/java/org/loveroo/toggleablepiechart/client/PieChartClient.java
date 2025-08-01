package org.loveroo.toggleablepiechart.client;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.util.Identifier;
import org.json.JSONObject;
import org.loveroo.toggleablepiechart.PieChart;
import org.loveroo.toggleablepiechart.data.PieChartTransform;
import org.loveroo.toggleablepiechart.event.DrawPieChart;
import org.loveroo.toggleablepiechart.event.KeybindHandler;

import net.fabricmc.api.ClientModInitializer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class PieChartClient implements ClientModInitializer {

	private static final String configPath = "toggleable-piechart-config.json";

	public static final PieChartTransform transform = new PieChartTransform();

	private KeybindHandler keybindHandler;
	private DrawPieChart pieChartRenderer;

	@Override
	public void onInitializeClient() {
		pieChartRenderer = new DrawPieChart();
		HudElementRegistry.attachElementAfter(VanillaHudElements.DEBUG, Identifier.of(PieChart.MOD_ID, "pie_chart"), pieChartRenderer);
		
		keybindHandler = new KeybindHandler(pieChartRenderer);

		loadConfig();
	}

	public static void loadConfig() {
		try {
			var config = new File(configPath);

			if(!config.exists()) {
				saveConfig();
			}

			var reader = new FileReader(config);
			var scanner = new Scanner(reader);

			var data = new StringBuilder();
			while(scanner.hasNext()) {
				data.append(scanner.next());
			}

			var json = new JSONObject(data.toString());
			transform.load(json);
		}
		catch(Exception e) {
			PieChart.LOGGER.error("Failed to load Toggleable PieChart config!", e);
		}
	}

	public static void saveConfig() {
		try {
			var config = new File(configPath);
			var writer = new FileWriter(config);

			var json = new JSONObject();
			transform.save(json);

			writer.write(json.toString());
			writer.close();
		}
		catch(Exception e) {
			PieChart.LOGGER.error("Failed to save Toggleable PieChart config!", e);
		}
	}
}