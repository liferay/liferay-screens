package com.liferay.mobile.screens.webcontentdisplay.interactor;

import com.liferay.mobile.screens.base.interactor.BaseCachedRemoteInteractor;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayListener;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public abstract class WebContentDisplayBaseInteractorImpl
	extends BaseCachedRemoteInteractor<WebContentDisplayListener, WebContentDisplayEvent> {

	public WebContentDisplayBaseInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	@Override
	protected void notifyError(WebContentDisplayEvent event) {
		getListener().onWebContentFailure(null, event.getException());
	}

	protected void validate(Locale locale) {
		if (locale == null) {
			throw new IllegalArgumentException("Locale cannot be empty");
		}
	}

}
