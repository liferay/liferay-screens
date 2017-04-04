package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.ddlrecordset.DDLRecordSetService;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLRecordSetConnector62 implements DDLRecordSetConnector {

	private final DDLRecordSetService ddlRecordSetService;

	public DDLRecordSetConnector62(Session session) {
		ddlRecordSetService = new DDLRecordSetService(session);
	}

	@Override
	public JSONObject getRecordSet(long recordSetId) throws Exception {
		ddlRecordSetService.getRecordSet(recordSetId);
		return null;
	}
}
