package com.liferay.mobile.screens.assetlist.operation;

import com.liferay.mobile.android.service.JSONObjectWrapper;

/**
 * @author Javier Gamarra
 */
public interface AssetEntryOperation {

	void getEntriesCount(JSONObjectWrapper entryQuery) throws Exception;
}
