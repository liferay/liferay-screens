package com.liferay.mobile.screens.user;

import com.liferay.mobile.screens.context.User;

public interface GetUserListener {

	void onGetUserSuccess(User user);

	void onGetUserFailure(Exception exception);
}
