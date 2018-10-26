package com.liferay.mobile.screens.auth.forgotpassword.interactor;

import com.liferay.mobile.screens.base.interactor.event.BasicEvent;

public class ForgotPasswordEvent extends BasicEvent {

    private final Boolean sent;

    public ForgotPasswordEvent() {
        super();
        sent = false;
    }

    public ForgotPasswordEvent(Boolean sent) {
        this.sent = sent;
    }

    public boolean isPasswordSent() {
        return sent;
    }
}
