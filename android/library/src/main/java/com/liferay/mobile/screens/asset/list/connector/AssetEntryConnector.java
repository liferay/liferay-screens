package com.liferay.mobile.screens.asset.list.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;

/**
 * @author Javier Gamarra
 */
public interface AssetEntryConnector {

    Integer getEntriesCount(JSONObjectWrapper entryQuery) throws Exception;
}
