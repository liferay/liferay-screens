package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v7.ddmstructure.DDMStructureService;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDMStructureConnector70 implements DDMStructureConnector {
	public DDMStructureConnector70(Session session) {
		_ddmStructureService = new DDMStructureService(session);
	}

	@Override
	public JSONObject getStructure(long structureId) throws Exception {
		return _ddmStructureService.getStructure(structureId);
	}

	private final DDMStructureService _ddmStructureService;
}
