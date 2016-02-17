package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v70.ddmstructure.DDMStructureService;

/**
 * @author Javier Gamarra
 */
public class DDMStructureConnector70 implements DDMStructureConnector {
	public DDMStructureConnector70(Session session) {
		_ddmStructureService = new DDMStructureService(session);
	}

	@Override
	public void getStructure(long structureId) throws Exception {
		_ddmStructureService.getStructure(structureId);
	}

	private final DDMStructureService _ddmStructureService;
}
