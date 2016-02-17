package com.liferay.mobile.screens.ddl.form.operation;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v70.ddmstructure.DDMStructureService;

/**
 * @author Javier Gamarra
 */
public class DDMStructureOperation70 implements DDMStructureOperation {
	public DDMStructureOperation70(Session session) {
		_ddmStructureService = new DDMStructureService(session);
	}

	@Override
	public void getStructure(long structureId) throws Exception {
		_ddmStructureService.getStructure(structureId);
	}

	private final DDMStructureService _ddmStructureService;
}
