package com.gary.bot.model;

/**
 * Domain object used to represent the 'body' field in the response to calls to
 * the BUX backend
 * 
 * @author Gary
 *
 */
public class ResponseBody {

	private String userId;
	private String sessionId;
	private String time;
	private String developerMessage;
	private String errorCode;
	private String securityId;
	private String currentPrice;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

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
