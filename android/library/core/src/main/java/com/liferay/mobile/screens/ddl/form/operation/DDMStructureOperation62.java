package com.liferay.mobile.screens.ddl.form.operation;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.ddmstructure.DDMStructureService;

/**
 * @author Javier Gamarra
 */
public class DDMStructureOperation62 implements DDMStructureOperation {
	public DDMStructureOperation62(Session session) {
		_ddmStructureService = new DDMStructureService(session);
	}

	@Override
	public void getStructure(long structureId) throws Exception {
		_ddmStructureService.getStructure(structureId);
	}

	private final DDMStructureService _ddmStructureService;
}
