package com.gary.bot.service;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.gary.bot.model.BuyRequest;
import com.gary.bot.model.InvestingAmount;
import com.gary.bot.model.PositionStatus;
import com.gary.bot.model.ResponseBody;
import com.gary.bot.model.Source;
import com.gary.bot.model.TradeConstraints;
import com.google.gson.Gson;

public class TradingService {

	private String buyEndpoint = "***";
	private String sellEndpoint = "***";
	
	private RestTemplate restTemplate = new RestTemplate();
	private Gson gson = new Gson();
	
	private String positionId;
	private TradeConstraints constraints;
	
	public void handleQuoteMessage(ResponseBody responseBody, TradeConstraints constraints) {
		System.out.println(
				"productId=" + responseBody.getSecurityId() + ", currentPrice=" + responseBody.getCurrentPrice());

		this.constraints = constraints;
		float currentPrice = Float.parseFloat(responseBody.getCurrentPrice());

		if (shouldExecuteBuy(constraints, currentPrice)) {
			this.executeBuy();
		} else if (shouldExecuteSell(constraints, currentPrice)) {
			this.executeSell();
		} 
	}

	private boolean shouldExecuteBuy(TradeConstraints constraints, float currentPrice) {
		return currentPrice <= constraints.getBuyPrice() && currentPrice > constraints.getLowerLimitSellPrice() && this.positionId == null;
	}

	private boolean shouldExecuteSell(TradeConstraints constraints, float currentPrice) {
		return this.positionId != null && (currentPrice >= constraints.getUpperLimitSellPrice() || currentPrice <= constraints.getLowerLimitSellPrice());
	}
	
	public void executeBuy(){
		System.out.println("Executing buy trade on productId="+this.constraints.getProductId());
		BuyRequest buyRequest = new BuyRequest();
		buyRequest.setDirection("BUY");
		buyRequest.setLeverage(2);
		buyRequest.setProductId(this.constraints.getProductId());
		
		Source source = new Source();
		source.setSourceType("OTHER");
		buyRequest.setSource(source);
		
		InvestingAmount investingAmount = new InvestingAmount();
		investingAmount.setAmount("10.0");
		investingAmount.setCurrency("BUX");
		investingAmount.setDecimals("2");
		
		buyRequest.setInvestingAmount(investingAmount);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", System.getProperty("authToken"));
		
		HttpEntity<String> requestObject = new HttpEntity<>(gson.toJson(buyRequest), headers);
		
		ResponseEntity<String> positionStatus = restTemplate.postForEntity(this.buyEndpoint, requestObject, String.class);
		
		PositionStatus positionStatusObj = gson.fromJson(positionStatus.getBody(), PositionStatus.class);
		this.positionId = positionStatusObj.getPositionId();
		System.out.println("Successfully executed buy trade on productId="+this.constraints.getProductId()+", positionId="+this.positionId);
	}

	public void executeSell(){
		System.out.println("Executing sell trade on positionId="+this.positionId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", System.getProperty("authToken"));
		
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		this.restTemplate.exchange(this.sellEndpoint+this.positionId, HttpMethod.DELETE, request, String.class);
		System.out.println("Successfully executed sell trade on positionId="+this.positionId);
		this.positionId = null;
	}
}
