package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v70.ScreensddmstructureversionService;
import org.json.JSONObject;

public class ScreensDDMStructureConnector70 implements DDMStructureConnector {

	private final ScreensddmstructureversionService screensddmstructurelayoutService;

	public ScreensDDMStructureConnector70(Session session) {
		screensddmstructurelayoutService = new ScreensddmstructureversionService(session);
	}

	@Override
	public JSONObject getStructure(long structureId) throws Exception {
		return screensddmstructurelayoutService.getDdmStructureVersion(structureId);
	}
}
