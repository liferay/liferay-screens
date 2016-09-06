package com.liferay.mobile.screens.bankofwesteros.push;

/**
 * @author Javier Gamarra
 */
public class IssueEvent {

	public IssueEvent(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	private final String message;
}
