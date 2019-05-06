package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v70.ScreensddlrecordService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class ScreensDDLRecordConnector70 implements ScreensDDLRecordConnector {

    private final ScreensddlrecordService ddlRecordService;

    public ScreensDDLRecordConnector70(Session session) {
        ddlRecordService = new ScreensddlrecordService(session);
    }

    @Override
    public JSONArray getDdlRecords(long recordSetId, long userId, String s, int startRow, int endRow,
        JSONObjectWrapper obc) throws Exception {
        return ddlRecordService.getDdlRecords(recordSetId, userId, s, startRow, endRow, obc);
    }

    @Override
    public JSONArray getDdlRecords(long recordSetId, String s, int startRow, int endRow, JSONObjectWrapper obc)
        throws Exception {
        return ddlRecordService.getDdlRecords(recordSetId, s, startRow, endRow, obc);
    }

    @Override
    public Integer getDdlRecordsCount(long recordSetId, long userId) throws Exception {
        return ddlRecordService.getDdlRecordsCount(recordSetId, userId);
    }

    @Override
    public Integer getDdlRecordsCount(long recordSetId) throws Exception {
        return ddlRecordService.getDdlRecordsCount(recordSetId);
    }

    @Override
    public JSONObject getDdlRecord(long recordId, String s) throws Exception {
        return ddlRecordService.getDdlRecord(recordId, s);
    }
}
