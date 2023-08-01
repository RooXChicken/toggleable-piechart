package com.rooxchicken.toggleablepiechart.client;

import com.rooxchicken.toggleablepiechart.event.KeyInputHandler;

//import com.rooxchicken.truehealth.networking.PingTester;

import net.fabricmc.api.ClientModInitializer;

public class ToggleablePieChartClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient()
	{
		KeyInputHandler.register();
	}
}