package com.gary.bot.service;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.gary.bot.model.BuyRequest;
import com.gary.bot.model.TradingParameters;

@RunWith(MockitoJUnitRunner.class)
public class TradingServiceHelperTest {

	private TradingServiceHelper classUnderTest;

	@Before
	public void setup() {
		TradingParameters tradingParameters = new TradingParameters(null, 10.0f, 20.0f, 15.0f);
		classUnderTest = new TradingServiceHelper(tradingParameters);
	}

	@Test
	public void testGetRequestHeaders() {
		// Arrange.
		System.setProperty("authToken", "abc123");

		// Act.
		HttpHeaders headers = this.classUnderTest.getRequestHeaders();

		// Assert
		Assert.assertEquals(headers.getAccept(), Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		Assert.assertEquals(headers.getContentType(), MediaType.APPLICATION_JSON);
		Assert.assertEquals(headers.get("Authorization").get(0), "abc123");
	}

	@Test
	public void testGetBuyRequest() {
		// Arrange.

		// Act.
		BuyRequest buyRequest = this.classUnderTest.getBuyRequest("123");

		// Assert
		Assert.assertEquals(buyRequest.getDirection(), "BUY");
		Assert.assertEquals(buyRequest.getProductId(), "123");
		Assert.assertEquals(buyRequest.getSource().getSourceType(), "OTHER");
		Assert.assertEquals(buyRequest.getInvestingAmount().getAmount(), "10.0");
		Assert.assertEquals(buyRequest.getInvestingAmount().getCurrency(), "BUX");
		Assert.assertEquals(buyRequest.getInvestingAmount().getDecimals(), "2");
	}

	@Test
	public void testShouldExecuteSellWhenPriceGreaterThanUpperLimit() {
		// Arrange.

		// Act.
		boolean response = this.classUnderTest.shouldExecuteSell(50.0f, "123");

		// Assert
		Assert.assertTrue(response);
	}

	@Test
	public void testShouldExecuteSellWhenPriceLessThanLowerLimitSellPrice() {
		// Arrange.

		// Act.
		boolean response = this.classUnderTest.shouldExecuteSell(5.0f, "123");

		// Assert
		Assert.assertTrue(response);
	}

	@Test
	public void testShouldExecuteSellWhenNoPositionForProduct() {
		// Arrange.

		// Act.
		boolean response = this.classUnderTest.shouldExecuteSell(5.0f, null);

		// Assert
		Assert.assertFalse(response);
	}

	@Test
	public void testShouldExecuteBuyCurrentPriceLessThanBuyPriceAndGreaterThanLowerLimitSellPrice() {
		// Arrange.

		// Act.
		boolean response = this.classUnderTest.shouldExecuteBuy(11.0f, null);

		// Assert
		Assert.assertTrue(response);
	}

	@Test
	public void testShouldExecuteBuyCurrentPriceGreaterThanBuyPrice() {
		// Arrange.

		// Act.
		boolean response = this.classUnderTest.shouldExecuteBuy(16.0f, null);

		// Assert
		Assert.assertFalse(response);
	}

	@Test
	public void testShouldExecuteBuyCurrentPriceLessThanBuyPriceLessThanLowerLimitSellPrice() {
		// Arrange.

		// Act.
		boolean response = this.classUnderTest.shouldExecuteBuy(8.0f, null);

		// Assert
		Assert.assertFalse(response);
	}

	@Test
	public void testShouldExecuteBuyAlreadyHavePosition() {
		// Arrange.

		// Act.
		boolean response = this.classUnderTest.shouldExecuteBuy(8.0f, "123");

		// Assert
		Assert.assertFalse(response);
	}
}
