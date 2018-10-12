package com.gary.bot.service;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.gary.bot.model.BuyRequest;
import com.gary.bot.model.InvestingAmount;
import com.gary.bot.model.Source;
import com.gary.bot.model.TradingParameters;

/**
 * Helper class used when making calls to the BUX backend services - extracts
 * some of the logic/object population out of the main TradingService class
 * 
 * @author Gary
 *
 */
public class TradingServiceHelper {

	private TradingParameters tradingParams;

	public TradingServiceHelper(TradingParameters tradingParams) {
		this.tradingParams = tradingParams;
	}

	/**
	 * Retrieve the http headers used when making RESTful calls to the BUX
	 * backend
	 * 
	 * @return Http headers used in order to the connect to the BUX backend
	 *         services
	 */
	public HttpHeaders getRequestHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", System.getProperty("authToken"));

		return headers;
	}

	/**
	 * Create and return a fully populated BuyRequest object, used to execute a
	 * buy trade
	 * 
	 * @param productId
	 *            the product id for the product to buy
	 * @return request object used to execute buy trade
	 */
	public BuyRequest getBuyRequest(String productId) {
		BuyRequest buyRequest = new BuyRequest();
		buyRequest.setDirection("BUY");
		buyRequest.setLeverage(2);
		buyRequest.setProductId(productId);

		Source source = new Source();
		source.setSourceType("OTHER");
		buyRequest.setSource(source);

		InvestingAmount investingAmount = new InvestingAmount();
		investingAmount.setAmount("10.0");
		investingAmount.setCurrency("BUX");
		investingAmount.setDecimals("2");

		buyRequest.setInvestingAmount(investingAmount);

		return buyRequest;
	}

	/**
	 * Determine whether or not to execute a sell trade. We should execute a
	 * sell trade if we currently have a position for that product, and the
	 * current price is greater than or equal to the upper limit sell price, or
	 * the current price is less than or equal to the lower limit sell price
	 * 
	 * @param currentPrice
	 *            the current price for the product
	 * @param positionId
	 *            the position id for that product if we hold it, can be null
	 * @return true if we should execute a sell trade, false if not
	 */
	public boolean shouldExecuteSell(float currentPrice, String positionId) {
		return positionId != null && (currentPrice >= tradingParams.getUpperLimitSellPrice()
				|| currentPrice <= tradingParams.getLowerLimitSellPrice());
	}

	/**
	 * Determine whether or not to execute a buy trade. We should execute a buy
	 * trade if we don't currently have a position for that product, and the
	 * current price is less than or equal to the buy price, and the current
	 * price is greater than the lower limit sell price
	 * 
	 * @param currentPrice
	 *            the current price for the product
	 * @param positionId
	 *            the position id for that product if we hold it, can be null
	 * @return true if we should execute a sell trade, false if not
	 */
	public boolean shouldExecuteBuy(float currentPrice, String positionId) {
		return currentPrice <= tradingParams.getBuyPrice() && currentPrice > tradingParams.getLowerLimitSellPrice()
				&& positionId == null;
	}
}
