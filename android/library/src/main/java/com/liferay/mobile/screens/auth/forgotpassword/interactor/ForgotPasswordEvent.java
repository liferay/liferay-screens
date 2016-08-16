package com.liferay.mobile.screens.auth.forgotpassword.interactor;

import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;

public class ForgotPasswordEvent extends BasicThreadEvent {

	public ForgotPasswordEvent(Boolean sent) {
		_sent = sent;
	}

	private final Boolean _sent;

	public boolean isPasswordSent() {
		return _sent;
	}
}
