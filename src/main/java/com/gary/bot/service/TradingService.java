package com.gary.bot.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.gary.bot.model.BuyRequest;
import com.gary.bot.model.PositionStatus;
import com.gary.bot.model.ResponseBody;
import com.gary.bot.model.TradingParameters;
import com.google.gson.Gson;

/**
 * Service class used to make calls to the BUX backend in order to buy and sell
 * products based on trading parameters
 * 
 * @author Gary
 *
 */
public class TradingService {

	private String buyEndpoint;
	private String sellEndpoint;

	private RestTemplate restTemplate;
	private Gson gson;

	private String positionId;
	private TradingServiceHelper tradingServiceHelper;

	private TradingParameters constraints;

	public TradingService(TradingParameters constraints) {
		this.buyEndpoint = System.getProperty("buyEndpoint");
		this.sellEndpoint = System.getProperty("sellEndpoint");

		this.restTemplate = new RestTemplate();
		this.gson = new Gson();
		this.constraints = constraints;
		tradingServiceHelper = new TradingServiceHelper(constraints);
	}

	public void handleQuoteMessage(ResponseBody responseBody) {
		System.out.println(
				"productId=" + responseBody.getSecurityId() + ", currentPrice=" + responseBody.getCurrentPrice());

		float currentPrice = Float.parseFloat(responseBody.getCurrentPrice());

		if (this.tradingServiceHelper.shouldExecuteBuy(currentPrice, this.positionId)) {
			this.executeBuy();
		} else if (this.tradingServiceHelper.shouldExecuteSell(currentPrice, this.positionId)) {
			this.executeSell();
		}
	}

	public void executeBuy() {
		System.out.println("Executing buy trade on productId=" + this.constraints.getProductId());

		BuyRequest buyRequest = this.tradingServiceHelper.getBuyRequest(this.constraints.getProductId());
		HttpHeaders headers = this.tradingServiceHelper.getRequestHeaders();
		HttpEntity<String> requestObject = new HttpEntity<>(gson.toJson(buyRequest), headers);
		ResponseEntity<String> positionStatus = restTemplate.postForEntity(this.buyEndpoint, requestObject,
				String.class);

		PositionStatus positionStatusObj = gson.fromJson(positionStatus.getBody(), PositionStatus.class);
		this.positionId = positionStatusObj.getPositionId();
		System.out.println("Successfully executed buy trade on productId=" + this.constraints.getProductId()
				+ ", positionId=" + this.positionId);
	}

	public void executeSell() {
		System.out.println("Executing sell trade on positionId=" + this.positionId);

		HttpHeaders headers = this.tradingServiceHelper.getRequestHeaders();
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		this.restTemplate.exchange(this.sellEndpoint + this.positionId, HttpMethod.DELETE, request, String.class);
		System.out.println("Successfully executed sell trade on positionId=" + this.positionId);
		this.positionId = null;
	}
}
