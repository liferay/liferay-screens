package com.liferay.mobile.screens.base.interactor;

import org.json.JSONException;

/**
 * @author Javier Gamarra
 */
public interface CacheCallback {

	void loadOnline() throws Exception;

	boolean retrieveFromCache() throws JSONException, Exception;

}
