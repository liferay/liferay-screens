package com.liferay.mobile.screens.asset.list.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public interface ScreensAssetEntryConnector {

    JSONArray getAssetEntries(JSONObjectWrapper entryQuery, String s) throws Exception;

    JSONArray getAssetEntries(long companyId, long groupId, String portletItemName, String s, int endRow)
        throws Exception;

    JSONObject getAssetEntry(long entryId, String language) throws Exception;

    JSONObject getAssetEntry(String className, long classPK, String language) throws Exception;
}
