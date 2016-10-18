package com.liferay.mobile.screens.asset.list.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v62.ScreensassetentryService;
import org.json.JSONArray;

/**
 * @author Javier Gamarra
 */
public class ScreensAssetEntryConnector62 implements ScreensAssetEntryConnector {

	private final ScreensassetentryService screensassetentryService;

	public ScreensAssetEntryConnector62(Session session) {
		screensassetentryService = new ScreensassetentryService(session);
	}

	@Override
	public JSONArray getAssetEntries(JSONObjectWrapper entryQuery, String s) throws Exception {
		return screensassetentryService.getAssetEntries(entryQuery, s);
	}

	@Override
	public JSONArray getAssetEntries(long companyId, long groupId, String portletItemName, String s, int endRow)
		throws Exception {
		return screensassetentryService.getAssetEntries(companyId, groupId, portletItemName, s, endRow);
	}
}
