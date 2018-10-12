package com.gary.bot.model;

/**
 * Constants class used to store the various string constants used in the type
 * field ('t') in the response from the websocket server
 * 
 * @author Gary
 */
public class ConnectionStatus {
	public final static String SUCCESS = "connect.connected";
	public final static String FAILED = "connect.failed";
	public final static String TRADING_QUOTE = "trading.quote";
}
