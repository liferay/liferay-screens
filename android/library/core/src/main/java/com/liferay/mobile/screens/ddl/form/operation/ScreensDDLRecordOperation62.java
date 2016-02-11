package com.liferay.mobile.screens.ddl.form.operation;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v62.ScreensddlrecordService;

/**
 * @author Javier Gamarra
 */
public class ScreensDDLRecordOperation62 implements ScreensDDLRecordOperation {
	public ScreensDDLRecordOperation62(Session session) {
		_screensddlrecordService = new ScreensddlrecordService(session);
	}

	@Override
	public void getDdlRecords(long recordSetId, long userId, String s, int startRow, int endRow) throws Exception {
		_screensddlrecordService.getDdlRecords(recordSetId, userId, s, startRow, endRow);
	}

	@Override
	public void getDdlRecords(long recordSetId, String s, int startRow, int endRow) throws Exception {
		_screensddlrecordService.getDdlRecords(recordSetId, s, startRow, endRow);
	}

	@Override
	public void getDdlRecordsCount(long recordSetId, long userId) throws Exception {
		_screensddlrecordService.getDdlRecordsCount(recordSetId, userId);
	}

	@Override
	public void getDdlRecordsCount(long recordSetId) throws Exception {
		_screensddlrecordService.getDdlRecordsCount(recordSetId);
	}

	@Override
	public void getDdlRecord(long recordId, String s) throws Exception {
		_screensddlrecordService.getDdlRecord(recordId, s);
	}

	private final ScreensddlrecordService _screensddlrecordService;
}
