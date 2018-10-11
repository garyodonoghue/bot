package com.gary.bot.model;

import static com.gary.bot.model.ConnectionStatus.FAILED;
import static com.gary.bot.model.ConnectionStatus.SUCCESS;
import static com.gary.bot.model.ConnectionStatus.TRADING_QUOTE;

public class WebsocketResponse {

	private String t;
	private ResponseBody body;
	
	public String getT() {
		return t;
	}

	public ResponseBody getBody() {
		return body;
	}
	
	public boolean isSuccessful() {
		return SUCCESS.equals(t);
	}
	
	public boolean isFailed() {
		return FAILED.equals(t);
	}
	
	@Override
	public String toString() {
		return "ConnectionResponse [t=" + t + ", body=" + body + "]";
	}

	public boolean isTradingQuote() {
		return TRADING_QUOTE.equals(t);
	}
}
