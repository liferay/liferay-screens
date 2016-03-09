package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v70.ddlrecord.DDLRecordService;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLRecordConnector70 implements DDLRecordConnector {
	public DDLRecordConnector70(Session session) {
		_ddlRecordService = new DDLRecordService(session);
	}

	@Override
	public JSONObject addRecord(long groupId, long recordSetId, int i, JSONObject jsonContent, JSONObjectWrapper serviceContextWrapper) throws Exception {
		return _ddlRecordService.addRecord(groupId, recordSetId, i, jsonContent, serviceContextWrapper);
	}

	@Override
	public JSONObject updateRecord(long recordId, int i, JSONObject jsonContent, boolean b, JSONObjectWrapper serviceContextWrapper) throws Exception {
		return _ddlRecordService.updateRecord(recordId, i, jsonContent, b, serviceContextWrapper);
	}

	private final DDLRecordService _ddlRecordService;
}
