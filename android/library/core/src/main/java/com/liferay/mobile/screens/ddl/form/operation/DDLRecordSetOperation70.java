package com.liferay.mobile.screens.ddl.form.operation;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v70.DDLRecordSetService;

/**
 * @author Javier Gamarra
 */
public class DDLRecordSetOperation70 implements DDLRecordSetOperation {

	public DDLRecordSetOperation70(Session session) {
		_ddlRecordSetService = new DDLRecordSetService(session);
	}

	@Override
	public void getRecordSet(long recordSetId) throws Exception {
		_ddlRecordSetService.getRecordSet(recordSetId);
	}

	private final DDLRecordSetService _ddlRecordSetService;
}
