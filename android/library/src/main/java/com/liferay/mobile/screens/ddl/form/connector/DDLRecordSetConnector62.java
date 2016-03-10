package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.ddlrecordset.DDLRecordSetService;

/**
 * @author Javier Gamarra
 */
public class DDLRecordSetConnector62 implements DDLRecordSetConnector {
	public DDLRecordSetConnector62(Session session) {
		_ddlRecordSetService = new DDLRecordSetService(session);
	}

	@Override
	public void getRecordSet(long recordSetId) throws Exception {
		_ddlRecordSetService.getRecordSet(recordSetId);
	}

	private final DDLRecordSetService _ddlRecordSetService;
}
