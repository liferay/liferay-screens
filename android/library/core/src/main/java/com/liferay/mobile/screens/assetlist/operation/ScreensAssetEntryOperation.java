package com.liferay.mobile.screens.assetlist.operation;

import com.liferay.mobile.android.service.JSONObjectWrapper;

/**
 * @author Javier Gamarra
 */
public interface ScreensAssetEntryOperation {

	void getAssetEntries(JSONObjectWrapper entryQuery, String s) throws Exception;

	void getAssetEntries(long companyId, long groupId, String portletItemName, String s, int endRow) throws Exception;

}
