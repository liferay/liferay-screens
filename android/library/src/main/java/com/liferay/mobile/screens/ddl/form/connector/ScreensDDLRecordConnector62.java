package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v62.ScreensddlrecordService;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class ScreensDDLRecordConnector62 implements ScreensDDLRecordConnector {

	public ScreensDDLRecordConnector62(Session session) {
		_ddlRecordService = new ScreensddlrecordService(session);
	}

	@Override
	public void getDdlRecords(long recordSetId, long userId, String s, int startRow, int endRow) throws Exception {
		_ddlRecordService.getDdlRecords(recordSetId, userId, s, startRow, endRow);
	}

	@Override
	public void getDdlRecords(long recordSetId, String s, int startRow, int endRow) throws Exception {
		_ddlRecordService.getDdlRecords(recordSetId, s, startRow, endRow);
	}

	@Override
	public void getDdlRecordsCount(long recordSetId, long userId) throws Exception {
		_ddlRecordService.getDdlRecordsCount(recordSetId, userId);
	}

	@Override
	public void getDdlRecordsCount(long recordSetId) throws Exception {
		_ddlRecordService.getDdlRecordsCount(recordSetId);
	}

	@Override
	public JSONObject getDdlRecord(long recordId, String s) throws Exception {
		return _ddlRecordService.getDdlRecord(recordId, s);
	}

	private final ScreensddlrecordService _ddlRecordService;
}
