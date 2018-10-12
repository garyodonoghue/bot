package com.gary.bot.model;

import static com.gary.bot.model.ConnectionStatus.TRADING_QUOTE;

/**
 * Domain object used to capture the response from calls to the BUX Websocket
 * server
 * 
 * @author Gary
 *
 */
public class WebsocketResponse {

	private String t;
	private ResponseBody body;

	/**
	 * Return true if the type code 't' indicates a connection success
	 * 
	 * @return
	 */
	public boolean isConnectionSuccessful() {
		return ConnectionStatus.SUCCESS.equals(t);
	}

	/**
	 * Return true if the type code 't' indicates a connection failure
	 * 
	 * @return
	 */
	public boolean isConnectionFailed() {
		return ConnectionStatus.FAILED.equals(t);
	}

	public String getT() {
		return t;
	}

	public ResponseBody getBody() {
		return body;
	}

	@Override
	public String toString() {
		return "ConnectionResponse [t=" + t + ", body=" + body + "]";
	}

	public boolean isTradingQuote() {
		return TRADING_QUOTE.equals(t);
	}
}
