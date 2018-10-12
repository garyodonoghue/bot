package com.gary.bot.model;

/**
 * Domain object used to represent the positionStatus field in the calls to the
 * BUX backend
 * 
 * @author Gary
 *
 */
public class PositionStatus {

	private String id;
	private String positionId;
	private Product product;
	private InvestingAmount investingAmount;
	private InvestingAmount price;
	private String leverage;
	private String direction;
	private String type;
	private String dateCreated;

	public String getId() {
		return id;
	}

	public String getPositionId() {
		return positionId;
	}

	public Product getProduct() {
		return product;
	}

	public InvestingAmount getInvestingAmount() {
		return investingAmount;
	}

	public InvestingAmount getPrice() {
		return price;
	}

	public String getLeverage() {
		return leverage;
	}

	public String getDirection() {
		return direction;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setType(String type) {
		this.type = type;
	}
}
