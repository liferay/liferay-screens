package com.liferay.mobile.screens.user.interactor;

import android.support.annotation.NonNull;
import com.liferay.mobile.screens.auth.login.connector.UserConnector;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.event.BasicEvent;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.user.GetUserListener;
import com.liferay.mobile.screens.util.ServiceProvider;
import org.json.JSONObject;

public class GetUserInteractor extends BaseRemoteInteractor<GetUserListener, BasicEvent> {

	@Override
	public BasicEvent execute(Object... args) throws Exception {
		String textValue = (String) args[0];
		String getUserBy = (String) args[1];

		if (null == textValue || textValue.isEmpty()) {
			throw new IllegalArgumentException("Text value cannot be empty");
		}
		if (null == getUserBy || getUserBy.isEmpty()) {
			getUserBy = "emailAddress";
		}

		return new BasicEvent(getJSONObject(textValue, getUserBy));
	}

	@Override
	public void onSuccess(BasicEvent event) {
		getListener().onGetUserSuccess(new User(event.getJSONObject()));
	}

	@Override
	public void onFailure(BasicEvent event) {
		getListener().onGetUserFailure(event.getException());
	}

	@NonNull
	private JSONObject getJSONObject(String textValue, String getUserByAttribute) throws Exception {
		long companyId = LiferayServerContext.getCompanyId();
		UserConnector connector = getUserConnector();

		switch (getUserByAttribute) {
			case "userId":
				return connector.getUserById(Long.parseLong(textValue));
			case "screenName":
				return connector.getUserByScreenName(companyId, textValue);
			case "emailAddress":
			default:
				return connector.getUserByEmailAddress(companyId, textValue);
		}
	}

	protected UserConnector getUserConnector() {
		return ServiceProvider.getInstance().getUserConnector(getSession());
	}
}