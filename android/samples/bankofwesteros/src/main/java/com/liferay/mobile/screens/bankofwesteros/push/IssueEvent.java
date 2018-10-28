package com.liferay.mobile.screens.bankofwesteros.push;

/**
 * @author Javier Gamarra
 */
public class IssueEvent {

    private final String message;

    public IssueEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
