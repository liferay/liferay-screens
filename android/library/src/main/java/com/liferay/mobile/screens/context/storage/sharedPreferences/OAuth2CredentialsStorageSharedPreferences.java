package com.liferay.mobile.screens.context.storage.sharedPreferences;

import android.content.SharedPreferences;
import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.auth.oauth2.OAuth2Authentication;
import com.liferay.mobile.screens.context.AuthenticationType;
import java.util.Arrays;
import java.util.List;

public class OAuth2CredentialsStorageSharedPreferences extends BaseCredentialsStorageSharedPreferences {

    private static final String AUTH = "auth";
    private static final String ACCESSTOKEN = "accessToken";
    private static final String REFRESHTOKEN = "refreshToken";
    private static final String SCOPE = "scope";
    private static final String ACCESSTOKENEXPIRATIONDATE = "accessTokenExpirationDate";
    private static final String CLIENTID = "clientId";
    private static final String CLIENTSECRET = "clientSecret";

    @Override
    protected void storeAuth(Authentication auth) {

        OAuth2Authentication oauth2Authentication = (OAuth2Authentication) auth;
        getSharedPref().edit()
            .putString(AUTH, AuthenticationType.OAUTH2REDIRECT.name())
            .putString(ACCESSTOKEN, oauth2Authentication.getAccessToken())
            .putString(REFRESHTOKEN, oauth2Authentication.getRefreshToken())
            .putString(SCOPE, getScopeString(oauth2Authentication.getScope()))
            .putLong(ACCESSTOKENEXPIRATIONDATE, oauth2Authentication.getAccessTokenExpirationDate())
            .putString(CLIENTID, oauth2Authentication.getClientId())
            .putString(CLIENTSECRET, oauth2Authentication.getClientSecret())
            .apply();
    }

    @Override
    protected Authentication loadAuth() {
        SharedPreferences sharedPref = getSharedPref();

        String accessToken = sharedPref.getString(ACCESSTOKEN, null);
        String refreshToken = sharedPref.getString(REFRESHTOKEN, null);
        List<String> scope = getScopeList(sharedPref.getString(SCOPE, ""));
        long accessTokenExpirationDate = sharedPref.getLong(ACCESSTOKENEXPIRATIONDATE, 0);
        String clientId = sharedPref.getString(CLIENTID, null);
        String clientSecret = sharedPref.getString(CLIENTSECRET, "");

        if (accessToken == null || refreshToken == null || clientId == null) {
            return null;
        }

        return new OAuth2Authentication(accessToken, refreshToken, scope, accessTokenExpirationDate, clientId,
            clientSecret);
    }

    private String getScopeString(List<String> scopes) {
        StringBuilder builder = new StringBuilder();

        for (String scope : scopes) {
            builder.append(scope).append(" ");
        }

        return builder.toString();
    }

    private List<String> getScopeList(String scopes) {
        return Arrays.asList(scopes.split(" "));
    }
}
