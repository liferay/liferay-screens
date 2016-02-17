package com.liferay.mobile.screens.auth.forgotpassword.operation;

/**
 * @author Javier Gamarra
 */
public interface ForgotPasswordOperation {

	void sendPasswordByEmailAddress(long companyId, String emailAddress) throws Exception;

	void sendPasswordByScreenName(long companyId, String screenName) throws Exception;

	void sendPasswordByUserId(long userId) throws Exception;

}
