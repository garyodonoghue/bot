package com.gary.bot.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.gary.bot.model.BuyRequest;
import com.gary.bot.model.ResponseBody;
import com.gary.bot.model.TradingParameters;

/**
 * Test class used to test the behavior of TradingService.java
 * 
 * @author Gary
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TradingServiceTest {

	private TradingService classUnderTest;

	@Mock
	private TradingServiceHelper tradingServiceHelper;

	@Mock
	private RestTemplate restTemplate;

	@Before
	public void setup() {
		this.classUnderTest = Mockito.spy(new TradingService());
		this.classUnderTest.setTradingServiceHelper(tradingServiceHelper);
		this.classUnderTest.setRestTemplate(restTemplate);
	}

	@Test
	public void testHandleQuoteMessageForBuyExecutesBuy() {
		// Arrange.
		ResponseBody responseBody = new ResponseBody();
		responseBody.setCurrentPrice("10.0");
		Mockito.when(this.tradingServiceHelper.shouldExecuteBuy(10.0f, null)).thenReturn(true);
		Mockito.doNothing().when(this.classUnderTest).executeBuy();

		// Act.
		this.classUnderTest.handleQuoteMessage(responseBody);

		// Assert.
		Mockito.verify(this.classUnderTest).executeBuy();
	}

	@Test
	public void testHandleQuoteMessageForSellExecutesSell() {
		// Arrange.
		ResponseBody responseBody = new ResponseBody();
		responseBody.setCurrentPrice("10.0");
		Mockito.when(this.tradingServiceHelper.shouldExecuteBuy(10.0f, null)).thenReturn(false);
		Mockito.when(this.tradingServiceHelper.shouldExecuteSell(10.0f, null)).thenReturn(true);
		Mockito.doNothing().when(this.classUnderTest).executeSell();

		// Act.
		this.classUnderTest.handleQuoteMessage(responseBody);

		// Assert.
		Mockito.verify(this.classUnderTest).executeSell();
	}

	@Test
	public void testHandleQuoteMessageForNeitherDoesNotExecuteTrade() {
		// Arrange.
		ResponseBody responseBody = new ResponseBody();
		responseBody.setCurrentPrice("10.0");
		Mockito.when(this.tradingServiceHelper.shouldExecuteBuy(10.0f, null)).thenReturn(false);
		Mockito.when(this.tradingServiceHelper.shouldExecuteSell(10.0f, null)).thenReturn(false);

		// Act.
		this.classUnderTest.handleQuoteMessage(responseBody);

		// Assert.
		Mockito.verify(this.classUnderTest, Mockito.never()).executeSell();
		Mockito.verify(this.classUnderTest, Mockito.never()).executeBuy();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testExecuteBuy() {
		// Arrange.
		TradingParameters tradingParameters = new TradingParameters("abc123", 0, 0, 0);
		TradingService tradingService = Mockito.spy(new TradingService(tradingParameters));
		tradingService.setRestTemplate(restTemplate);
		tradingService.setTradingServiceHelper(tradingServiceHelper);

		BuyRequest buyRequest = new BuyRequest();
		Mockito.when(this.tradingServiceHelper.getBuyRequest(Mockito.anyString())).thenReturn(buyRequest);
		String mockPositionId = "4c58a0b2-ea78-46a0-ac21-5a8c22d527dc";
		ResponseEntity<String> mockBuyResponse = new ResponseEntity<String>("{'positionId':" + mockPositionId + "}",
				null);
		Mockito.when(restTemplate.postForEntity(((String) Mockito.isNull()), Mockito.any(), Mockito.any(Class.class)))
				.thenReturn(mockBuyResponse);

		// Act.
		tradingService.executeBuy();

		// Assert.
		Mockito.verify(tradingServiceHelper).getBuyRequest("abc123");
		Mockito.verify(tradingServiceHelper).getRequestHeaders();
		Mockito.verify(restTemplate).postForEntity(((String) Mockito.isNull()), Mockito.any(),
				Mockito.any(Class.class));
		Assert.assertEquals(mockPositionId, tradingService.getPositionId());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testExecuteSell() {
		// Arrange.

		// Act.
		classUnderTest.executeSell();

		// Assert.
		Mockito.verify(tradingServiceHelper).getRequestHeaders();
		Mockito.verify(restTemplate).exchange(Mockito.anyString(), Mockito.any(HttpMethod.class),
				Mockito.any(HttpEntity.class), Mockito.any(Class.class));
	}
}
