package com.gary.bot.websocket.client.handler;

import javax.websocket.Session;

import com.gary.bot.model.ResponseBody;
import com.gary.bot.model.SubscriptionMessage;
import com.gary.bot.model.TradeConstraints;
import com.gary.bot.model.WebsocketResponse;
import com.gary.bot.service.TradingService;
import com.google.gson.Gson;

public class SocketMessageHandler {

	private Session userSession;
	private TradeConstraints constraints;
	private Gson gson = new Gson();
	private TradingService tradingService;
	
	public SocketMessageHandler(TradeConstraints constraints){
		this.tradingService = new TradingService();
		this.constraints = constraints;
	}
	
	public void handleMessage(String message) {
		WebsocketResponse response = gson.fromJson(message, WebsocketResponse.class);

		if (response.isSuccessful()) {
			System.out.println("Successfully connected, proceeding to subscribe...");
			subscribeToProduct();
		} else if (response.isFailed()) {
			ResponseBody responseBody = response.getBody();
			System.err.println("Connection failed : developerMessage=" + responseBody.getDeveloperMessage()
					+ ", errorCode=" + responseBody.getErrorCode());
		} else if (response.isTradingQuote()) {
			ResponseBody responseBody = response.getBody();
			this.tradingService.handleQuoteMessage(responseBody, this.constraints);
		}
	}
	
	private void subscribeToProduct() {
		SubscriptionMessage subscription = new SubscriptionMessage();
		subscription.setSubscribeTo(this.constraints.getProductId());
		this.sendMessage(gson.toJson(subscription));
	}
	
	/**
     * Send a message.
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
