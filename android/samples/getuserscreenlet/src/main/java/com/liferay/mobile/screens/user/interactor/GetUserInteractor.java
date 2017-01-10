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

	private String textValue;
	private String getUserByAttribute;

	@Override
	public BasicEvent execute(Object... args) throws Exception {
		textValue = (String) args[0];
		getUserByAttribute = (String) args[1];

		validate();

		JSONObject jsonObject = getJSONObject();
		return new BasicEvent(jsonObject);
	}

	@Override
	public void onSuccess(BasicEvent event) {
		getListener().onGetUserSuccess(new User(event.getJSONObject()));
	}

	@Override
	public void onFailure(BasicEvent event) {
		getListener().onGetUserFailure(event.getException());
	}

	private void validate() {
		if (null == textValue || textValue.isEmpty()) {
			throw new IllegalArgumentException("Text value cannot be empty");
		}
		if (null == getUserByAttribute || getUserByAttribute.isEmpty()) {
			getUserByAttribute = "emailAddress";
		}
	}

	@NonNull
	private JSONObject getJSONObject() throws Exception {
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

	public UserConnector getUserConnector() {
		return ServiceProvider.getInstance().getUserConnector(getSession());
	}
}