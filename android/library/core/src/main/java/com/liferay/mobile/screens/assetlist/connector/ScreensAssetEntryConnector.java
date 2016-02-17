package com.liferay.mobile.screens.assetlist.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;

/**
 * @author Javier Gamarra
 */
public interface ScreensAssetEntryConnector {

	void getAssetEntries(JSONObjectWrapper entryQuery, String s) throws Exception;

	void getAssetEntries(long companyId, long groupId, String portletItemName, String s, int endRow) throws Exception;

}
