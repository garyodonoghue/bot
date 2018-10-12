package com.gary.bot.model;

/**
 * 
 * Request object used when making a 'BUY' order type for a particular product
 * id
 * 
 * @author Gary
 */
public class BuyRequest {

	private String productId;
	private InvestingAmount investingAmount;
	private int leverage;
	private String direction;
	private Source source;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public InvestingAmount getInvestingAmount() {
		return investingAmount;
	}

	public void setInvestingAmount(InvestingAmount investingAmount) {
		this.investingAmount = investingAmount;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public void setLeverage(int leverage) {
		this.leverage = leverage;
	}

	public int getLeverage() {
		return this.leverage;
	}
}
