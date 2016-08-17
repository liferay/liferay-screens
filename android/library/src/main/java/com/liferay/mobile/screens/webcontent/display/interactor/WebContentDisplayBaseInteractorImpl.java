package com.liferay.mobile.screens.webcontent.display.interactor;

import com.liferay.mobile.screens.base.thread.BaseCachedThreadRemoteInteractor;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayListener;
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public abstract class WebContentDisplayBaseInteractorImpl
	extends BaseCachedThreadRemoteInteractor<WebContentDisplayListener, WebContentDisplayEvent> {

	protected void validate(Locale locale) {
		if (locale == null) {
			throw new IllegalArgumentException("Locale cannot be empty");
		}
	}
}
