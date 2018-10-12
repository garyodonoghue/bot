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

			// These values could have just as easily been passed as runtime
			// args in the app runtime configuration, or as system.in params
			// entered by the user, but same effect doing it here
			float lowerLimitSellPrice = 11590.5f;
			float upperLimitSellPrice = 11599.0f;
			float buyPrice = 11591.5f;
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
