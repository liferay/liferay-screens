package com.liferay.mobile.screens.ddl.form.connector;

/**
 * @author Javier Gamarra
 */
public interface ScreensDDLRecordConnector {
	void getDdlRecords(long recordSetId, long userId, String s, int startRow, int endRow) throws Exception;

	void getDdlRecords(long recordSetId, String s, int startRow, int endRow) throws Exception;

	void getDdlRecordsCount(long recordSetId, long userId) throws Exception;

	void getDdlRecordsCount(long recordSetId) throws Exception;

	void getDdlRecord(long recordId, String s) throws Exception;
}
