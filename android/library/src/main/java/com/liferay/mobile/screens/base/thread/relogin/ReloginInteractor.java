package com.liferay.mobile.screens.base.thread.relogin;

import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.screens.auth.login.connector.UserConnector;
import com.liferay.mobile.screens.base.thread.BaseThreadInteractor;
import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.base.thread.event.UserEvent;
import com.liferay.mobile.screens.base.thread.listener.LoginThreadListener;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.util.ServiceProvider;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class ReloginInteractor extends BaseThreadInteractor<LoginThreadListener, UserEvent> {

	public ReloginInteractor(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public UserEvent execute(Object... args) throws Exception {
		BasicAuthentication basicAuthentication = new BasicAuthentication("test@liferay.com", "test");
		Session _currentUserSession = new SessionImpl(LiferayServerContext.getServer(), basicAuthentication);
		UserConnector userConnector = ServiceProvider.getInstance().getUserConnector(_currentUserSession);
		JSONObject userJsonObject = userConnector.getUserById(20545);
		return new UserEvent(userJsonObject);
	}

	@Override
	public void onFailure(BasicThreadEvent event) {
		SessionContext.logout();

		getListener().onFailure(event.getException());
	}

	@Override
	public void onSuccess(UserEvent event) {
		User user = new User(event.getJSONObject());
		SessionContext.setCurrentUser(user);

		getListener().onLoginSuccess(user);
	}

}