package com.gary.bot.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain object used to represent the call to the BUX backend when changing a
 * subscription (either subscribing or unsubscribing to a product)
 * 
 * @author Gary
 *
 */
public class SubscriptionMessage {

	private List<String> subscribeTo;
	private List<String> unsubscribeFrom;

	public List<String> getSubscribeTo() {
		return subscribeTo;
	}

	public void setSubscribeTo(String subscribeTo) {
		this.subscribeTo = new ArrayList<String>();
		this.subscribeTo.add("trading.product." + subscribeTo);
	}

	public List<String> getUnsubscribeFrom() {
		this.unsubscribeFrom = new ArrayList<String>();
		return unsubscribeFrom;
	}

	public void setUnsubscribeFrom(List<String> unsubscribeFrom) {
		this.unsubscribeFrom = unsubscribeFrom;
	}
}
