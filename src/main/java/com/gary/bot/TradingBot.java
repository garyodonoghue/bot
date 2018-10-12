package com.gary.bot;

import java.net.URI;
import java.net.URISyntaxException;

import com.gary.bot.model.TradingParameters;
import com.gary.bot.websocket.client.WebsocketClient;

/**
 * Main class used to configure and run the trading-bot application. This class
 * specifies a number of parameters used to configure the bot, e.g. the
 * websocket endpoint to connect to in order to receive pricing info for a
 * particular product id, the lowerLimitSell price at which to sell out of a
 * held product and make a loss, the upper limit sell price at which to sell out
 * of a product and make a profit, the buy price at which to buy a particular
 * product and the product id itself which to buy and sell.
 * 
 * @author Gary
 */
public class TradingBot {
	public static void main(String[] args) {
		try {
			URI socketEnpoint = new URI(System.getProperty("wsEndpoint"));
			float lowerLimitSellPrice = 12739.9f;
			float upperLimitSellPrice = 12819.9f;
			float buyPrice = 12753.7f;
			String productId = "sb26493";

			TradingParameters tradeConstraints = new TradingParameters(productId, lowerLimitSellPrice,
					upperLimitSellPrice, buyPrice);

			// open websocket
			new WebsocketClient(socketEnpoint, tradeConstraints);

		} catch (URISyntaxException ex) {
			System.err.println("URISyntaxException exception: " + ex.getMessage());
		}
	}
}
