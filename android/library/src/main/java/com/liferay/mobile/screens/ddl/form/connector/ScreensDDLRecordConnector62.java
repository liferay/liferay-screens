package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v62.ScreensddlrecordService;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class ScreensDDLRecordConnector62 implements ScreensDDLRecordConnector {

	public ScreensDDLRecordConnector62(Session session) {
		_ddlRecordService = new ScreensddlrecordService(session);
	}

	@Override
	public JSONArray getDdlRecords(long recordSetId, long userId, String s, int startRow, int endRow,
		JSONObjectWrapper obc) throws Exception {
		return _ddlRecordService.getDdlRecords(recordSetId, userId, s, startRow, endRow);
	}

	@Override
	public JSONArray getDdlRecords(long recordSetId, String s, int startRow, int endRow,
		JSONObjectWrapper obc) throws Exception {
		return _ddlRecordService.getDdlRecords(recordSetId, s, startRow, endRow);
	}

	@Override
	public Integer getDdlRecordsCount(long recordSetId, long userId) throws Exception {
		return _ddlRecordService.getDdlRecordsCount(recordSetId, userId);
	}

	@Override
	public Integer getDdlRecordsCount(long recordSetId) throws Exception {
		return _ddlRecordService.getDdlRecordsCount(recordSetId);
	}

	@Override
	public JSONObject getDdlRecord(long recordId, String s) throws Exception {
		return _ddlRecordService.getDdlRecord(recordId, s);
	}

	private final ScreensddlrecordService _ddlRecordService;
}
