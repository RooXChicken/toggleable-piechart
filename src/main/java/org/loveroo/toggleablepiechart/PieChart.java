package org.loveroo.toggleablepiechart;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PieChart implements ModInitializer {

	public static final String MOD_ID = "toggleable-piechart";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Toggleable PieChart by Roo");
	}
}