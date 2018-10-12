package com.gary.bot.websocket.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig;

/**
 * Custom web socket client configurator, used to set headers on the request to
 * the websocket server, in order to authenticate successfully
 * 
 * @author Gary
 *
 */
public class WebsocketClientConfigurator extends ClientEndpointConfig.Configurator {

	/**
	 * Lifecycle method which is hooked into before the call is made to the
	 * websocket server endpoint. Used to set the authorization header on the
	 * request in order to authenticate successfully with the websocket server
	 */
	@Override
	public void beforeRequest(Map<String, List<String>> headers) {
		List<String> authTokenHeader = new ArrayList<String>();
		authTokenHeader.add(System.getProperty("authToken"));
		headers.put("Authorization", authTokenHeader);

		List<String> acceptLanguageHeader = new ArrayList<String>();
		acceptLanguageHeader.add("nl-NL,en;q=0.8");
		headers.put("Accept-Language", acceptLanguageHeader);
	}
}
