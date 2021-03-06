package com.gary.bot.model;

/**
 * Domain object used to represent the product field in the calls to the BUX
 * backend
 * 
 * @author Gary
 *
 */
public class Product {

	private String securityId;
	private String symbol;
	private String displayName;

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
