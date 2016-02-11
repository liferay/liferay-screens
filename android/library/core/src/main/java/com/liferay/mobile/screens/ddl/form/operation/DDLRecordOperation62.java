package com.liferay.mobile.screens.ddl.form.operation;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.ddlrecord.DDLRecordService;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLRecordOperation62 implements DDLRecordOperation {
	public DDLRecordOperation62(Session session) {
		ddlRecordService = new DDLRecordService(session);
	}

	@Override
	public JSONObject addRecord(long groupId, long recordSetId, int i, JSONObject jsonContent, JSONObjectWrapper serviceContextWrapper) throws Exception {
		return ddlRecordService.addRecord(groupId, recordSetId, i, jsonContent, serviceContextWrapper);
	}

	@Override
	public JSONObject updateRecord(long recordId, int i, JSONObject jsonContent, boolean b, JSONObjectWrapper serviceContextWrapper) throws Exception {
		return ddlRecordService.updateRecord(recordId, i, jsonContent, b, serviceContextWrapper);
	}

	private final DDLRecordService ddlRecordService;
}
