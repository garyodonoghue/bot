package com.gary.bot.websocket.client;

import java.net.URI;
import java.util.Scanner;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import com.gary.bot.model.TradingParameters;
import com.gary.bot.websocket.client.handler.SocketMessageHandler;

/**
 * Websocket client class used to connect to the BUX websocket server endpoint
 * and handle incoming messages
 * 
 * @author Gary
 *
 */
@ClientEndpoint(configurator = WebsocketClientConfigurator.class)
public class WebsocketClient {

	private Session userSession = null;
	private SocketMessageHandler messageHandler;
	private TradingParameters constraints;
	private SocketMessageHandler socketMessageHandler;

	public WebsocketClient(URI endpointURI, TradingParameters constraints) {
		try {
			this.constraints = constraints;
			this.socketMessageHandler = new SocketMessageHandler(this.constraints);
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			setupMessageHandler();

			container.connectToServer(this, endpointURI);

			// don't close immediately
			new Scanner(System.in).nextLine();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Add custom message handler for handling incoming messages from the
	 * websocket server
	 */
	private void setupMessageHandler() {
		this.addMessageHandler(this.socketMessageHandler);
	}

	/**
	 * Callback hook for Connection open events.
	 *
	 * @param userSession
	 *            the userSession which is opened.
	 */
	@OnOpen
	public void onOpen(Session userSession) {
		System.out.println("opening websocket");
		this.userSession = userSession;
		this.socketMessageHandler.setUserSession(this.userSession);
	}

	/**
	 * Callback hook for Connection close events.
	 *
	 * @param userSession
	 *            the userSession which is getting closed.
	 * @param reason
	 *            the reason for connection close
	 */
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		System.out.println("closing websocket");
		this.userSession = null;
	}

	/**
	 * Callback hook for incoming message events.
	 *
	 * @param message
	 *            The text message
	 */
	@OnMessage
	public void onMessage(String message) {
		if (this.messageHandler != null) {
			this.messageHandler.handleMessage(message);
		}
	}

	/**
	 * Callback hook for error events
	 * 
	 * @param message
	 */
	@OnError
	public void onError(Throwable message) {
		System.err.println("Error=" + message);
	}

	/**
	 * register message handler
	 *
	 * @param msgHandler
	 */
	public void addMessageHandler(SocketMessageHandler msgHandler) {
		this.messageHandler = msgHandler;
	}
}