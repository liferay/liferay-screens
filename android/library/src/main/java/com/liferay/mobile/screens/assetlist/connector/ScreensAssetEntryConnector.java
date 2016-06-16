package com.liferay.mobile.screens.assetlist.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;

import org.json.JSONArray;

/**
 * @author Javier Gamarra
 */
public interface ScreensAssetEntryConnector {

	JSONArray getAssetEntries(JSONObjectWrapper entryQuery, String s) throws Exception;

	JSONArray getAssetEntries(long companyId, long groupId, String portletItemName, String s, int endRow) throws Exception;

}
