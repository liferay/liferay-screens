package com.liferay.mobile.screens.auth.forgotpassword.interactor;

import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;

public class ForgotPasswordEvent extends BasicThreadEvent {

	private final Boolean sent;

	public ForgotPasswordEvent(Boolean sent) {
		this.sent = sent;
	}

	public boolean isPasswordSent() {
		return sent;
	}
}
