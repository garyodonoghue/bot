package com.gary.bot.model;

public class ResponseBody {

	private String userId;
	private String sessionId;
	private String time;
	private String developerMessage;
	private String errorCode;
	private String securityId;
	private String currentPrice;
	
	public String getSecurityId() {
		return securityId;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}
	
	public String getUserId() {
		return userId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getTime() {
		return time;
	}
	
	public String getDeveloperMessage() {
		return developerMessage;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	@Override
	public String toString() {
		return "ResponseBody [userId=" + userId + ", sessionId=" + sessionId + ", time=" + time + ", developerMessage="
				+ developerMessage + ", errorCode=" + errorCode + ", securityId=" + securityId + ", currentPrice="
				+ currentPrice + "]";
	}
}
