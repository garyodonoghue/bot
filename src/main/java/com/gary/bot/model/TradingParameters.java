package com.gary.bot.model;

/**
 * Class used to represent the various parameters used when deciding to buy or
 * sell a particular product
 * 
 * @author Gary
 */
public class TradingParameters {

	private float lowerLimitSellPrice;
	private float upperLimitSellPrice;
	private float buyPrice;
	private String productId;

	public TradingParameters(String productId, float lowerLimitSellPrice, float upperLimitSellPrice, float buyPrice) {
		this.productId = productId;
		this.lowerLimitSellPrice = lowerLimitSellPrice;
		this.upperLimitSellPrice = upperLimitSellPrice;
		this.buyPrice = buyPrice;
	}

	public String getProductId() {
		return productId;
	}

	public float getLowerLimitSellPrice() {
		return lowerLimitSellPrice;
	}

	public float getUpperLimitSellPrice() {
		return upperLimitSellPrice;
	}

	public float getBuyPrice() {
		return buyPrice;
	}
}
