package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.ddmstructure.DDMStructureService;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDMStructureConnector62 implements DDMStructureConnector {

	private final DDMStructureService ddmStructureService;

	public DDMStructureConnector62(Session session) {
		ddmStructureService = new DDMStructureService(session);
	}

	@Override
	public JSONObject getStructure(long structureId) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ddmStructure", ddmStructureService.getStructure(structureId));
		return jsonObject;
	}
}
