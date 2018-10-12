package com.gary.bot.websocket.client.handler;

import javax.websocket.Session;

import com.gary.bot.model.ResponseBody;
import com.gary.bot.model.SubscriptionMessage;
import com.gary.bot.model.TradingParameters;
import com.gary.bot.model.WebsocketResponse;
import com.gary.bot.service.TradingService;
import com.google.gson.Gson;

/**
 * Class used to handle messages published by the websocket server and received
 * by the websocket client
 * 
 * @author Gary
 *
 */
public class SocketMessageHandler {

	private Session userSession;
	private TradingParameters constraints;
	private Gson gson = new Gson();
	private TradingService tradingService;

	public SocketMessageHandler(TradingParameters constraints) {
		this.constraints = constraints;
		this.tradingService = new TradingService(constraints);
	}

	/**
	 * Handle the incoming websocket message. The message can contain
	 * information about connection status or pricing info, so we need to handle
	 * the message appropriately and react to the contents of the message object
	 * 
	 * @param message
	 */
	public void handleMessage(String message) {
		WebsocketResponse response = gson.fromJson(message, WebsocketResponse.class);

		if (response.isConnectionSuccessful()) {
			System.out.println("Successfully connected, proceeding to subscribe...");
			subscribeToProduct();
		} else if (response.isConnectionFailed()) {
			ResponseBody responseBody = response.getBody();
			System.err.println("Connection failed : developerMessage=" + responseBody.getDeveloperMessage()
					+ ", errorCode=" + responseBody.getErrorCode());
		} else if (response.isTradingQuote()) {
			ResponseBody responseBody = response.getBody();
			this.tradingService.handleQuoteMessage(responseBody);
		}
	}

	/**
	 * Subscribe to receive pricing updates about a particular product by
	 * sending a message containing the subscription info to the websocket
	 * server
	 */
	private void subscribeToProduct() {
		SubscriptionMessage subscription = new SubscriptionMessage();
		subscription.setSubscribeTo(this.constraints.getProductId());
		this.sendMessage(gson.toJson(subscription));
	}

	/**
	 * Send a message to the websocket server using the user session created on
	 * connection to the websocket endpoint.
	 *
	 * @param message
	 */
	public void sendMessage(String message) {
		this.userSession.getAsyncRemote().sendText(message);
	}

	public Session getUserSession() {
		return userSession;
	}

	public void setUserSession(Session userSession) {
		this.userSession = userSession;
	}
}
