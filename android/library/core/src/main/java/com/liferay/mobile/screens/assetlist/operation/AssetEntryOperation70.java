package com.liferay.mobile.screens.assetlist.operation;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v70.assetentry.AssetEntryService;

/**
 * @author Javier Gamarra
 */
public class AssetEntryOperation70 implements AssetEntryOperation {
	public AssetEntryOperation70(Session session) {
		_assetEntryService = new AssetEntryService(session);
	}

	@Override
	public void getEntriesCount(JSONObjectWrapper entryQuery) throws Exception {
		_assetEntryService.getEntriesCount(entryQuery);
	}

	private final AssetEntryService _assetEntryService;
}
