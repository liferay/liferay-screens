package com.liferay.mobile.screens.assetlist.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;

/**
 * @author Javier Gamarra
 */
public interface AssetEntryConnector {

	void getEntriesCount(JSONObjectWrapper entryQuery) throws Exception;
}
