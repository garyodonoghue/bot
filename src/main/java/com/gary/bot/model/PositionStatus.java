package com.gary.bot.model;

public class PositionStatus {

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
	private String id;
	private String positionId;
	private Product product;
	private InvestingAmount investingAmount;
	private InvestingAmount price;
	private String leverage;
	private String direction;
	private String type;
	private String dateCreated;
}
