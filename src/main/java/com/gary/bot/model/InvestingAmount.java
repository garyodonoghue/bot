package com.gary.bot.model;

/**
 * Domain object used to represent the investingAmount field in the calls to the
 * BUX backend
 * 
 * @author Gary
 *
 */
public class InvestingAmount {

	private String currency;
	private String decimals;
	private String amount;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDecimals() {
		return decimals;
	}

	public void setDecimals(String decimals) {
		this.decimals = decimals;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
