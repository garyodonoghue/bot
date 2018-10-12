package com.gary.bot.model;

/**
 * Domain object used to represent the source field in the calls to the BUX
 * backend
 * 
 * @author Gary
 *
 */
public class Source {
	private String sourceType;

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
}
