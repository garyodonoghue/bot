package com.gary.bot;

import java.net.URI;
import java.net.URISyntaxException;

import com.gary.bot.model.TradeConstraints;
import com.gary.bot.websocket.client.WebsocketClient;

public class TradingBot {
	public static void main(String[] args) {
		try {
			// TODO Change to beta endpoint
			URI socketEnpoint = new URI("ws://localhost:8080/subscriptions/me");
			float lowerLimitSellPrice = 12739.9f; //TODO get from args[] array
			float upperLimitSellPrice = 12819.9f;
			float buyPrice = 12753.7f;
			String productId ="sb26493";
			 
			TradeConstraints tradeConstraints = new TradeConstraints(productId, lowerLimitSellPrice, upperLimitSellPrice, buyPrice);
			
			// open websocket
			new WebsocketClient(socketEnpoint, tradeConstraints); 

		} catch (URISyntaxException ex) {
			System.err.println("URISyntaxException exception: " + ex.getMessage());
		}
	}
}
