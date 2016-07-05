package com.liferay.mobile.screens.base.thread.relogin;

import com.liferay.mobile.android.service.Session;

/**
 * @author Javier Gamarra
 */
public interface ReloginListener {
	void invalidSession(Session currentUserSession);

	void errorRetrievingSession(Exception e);
}
