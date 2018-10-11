package com.gary.bot.model;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionMessage {

	private List<String> subscribeTo;
	private List<String> unsubscribeFrom;
	
	public List<String> getSubscribeTo() {
		return subscribeTo;
	}
	public void setSubscribeTo(String subscribeTo) {
		this.subscribeTo = new ArrayList<String>();
		this.subscribeTo.add("trading.product."+subscribeTo);
	}
	public List<String> getUnsubscribeFrom() {
		this.unsubscribeFrom = new ArrayList<String>();
		return unsubscribeFrom;
	}
	public void setUnsubscribeFrom(List<String> unsubscribeFrom) {
		this.unsubscribeFrom = unsubscribeFrom;
	}
}
