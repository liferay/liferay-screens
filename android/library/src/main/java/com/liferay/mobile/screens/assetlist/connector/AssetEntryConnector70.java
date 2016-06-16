package com.liferay.mobile.screens.assetlist.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v7.assetentry.AssetEntryService;

/**
 * @author Javier Gamarra
 */
public class AssetEntryConnector70 implements AssetEntryConnector {

	public AssetEntryConnector70(Session session) {
		_assetEntryService = new AssetEntryService(session);
	}

	@Override
	public Integer getEntriesCount(JSONObjectWrapper entryQuery) throws Exception {
		return _assetEntryService.getEntriesCount(entryQuery);
	}

	private final AssetEntryService _assetEntryService;
}
