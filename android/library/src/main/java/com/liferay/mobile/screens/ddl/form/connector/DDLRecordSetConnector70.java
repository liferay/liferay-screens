package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v7.ddlrecordset.DDLRecordSetService;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLRecordSetConnector70 implements DDLRecordSetConnector {

	private final DDLRecordSetService ddlRecordSetService;

	public DDLRecordSetConnector70(Session session) {
		ddlRecordSetService = new DDLRecordSetService(session);
	}

	@Override
	public JSONObject getRecordSet(long recordSetId) throws Exception {
		return ddlRecordSetService.getRecordSet(recordSetId);
	}
}
