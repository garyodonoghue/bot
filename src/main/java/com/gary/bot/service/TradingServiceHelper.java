package com.gary.bot.service;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.gary.bot.model.BuyRequest;
import com.gary.bot.model.InvestingAmount;
import com.gary.bot.model.Source;
import com.gary.bot.model.TradingParameters;

public class TradingServiceHelper {

	private TradingParameters constraints;

	public TradingServiceHelper(TradingParameters constraints) {
		this.constraints = constraints;
	}

	public HttpHeaders getRequestHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", System.getProperty("authToken"));

		return headers;
	}

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

	public boolean shouldExecuteSell(float currentPrice, String positionId) {
		return positionId != null && (currentPrice >= constraints.getUpperLimitSellPrice()
				|| currentPrice <= constraints.getLowerLimitSellPrice());
	}

	public boolean shouldExecuteBuy(float currentPrice, String positionId) {
		return currentPrice <= constraints.getBuyPrice() && currentPrice > constraints.getLowerLimitSellPrice()
				&& positionId == null;
	}
}
