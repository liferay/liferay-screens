package com.liferay.mobile.screens.auth.forgotpassword.connector;

/**
 * @author Javier Gamarra
 */
public interface ForgotPasswordConnector {

    Boolean sendPasswordByEmailAddress(long companyId, String emailAddress) throws Exception;

    Boolean sendPasswordByScreenName(long companyId, String screenName) throws Exception;

    Boolean sendPasswordByUserId(long userId) throws Exception;
}
