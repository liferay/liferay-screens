package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v70.DDLRecordService;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLRecordConnector70 implements DDLRecordConnector {
    private final DDLRecordService ddlRecordService;

    public DDLRecordConnector70(Session session) {
        ddlRecordService = new DDLRecordService(session);
    }

    @Override
    public JSONObject addRecord(long groupId, long recordSetId, int i, JSONObject jsonContent,
        JSONObjectWrapper serviceContextWrapper) throws Exception {
        return ddlRecordService.addRecord(groupId, recordSetId, i, jsonContent, serviceContextWrapper);
    }

    @Override
    public JSONObject updateRecord(long recordId, int i, JSONObject jsonContent, boolean b,
        JSONObjectWrapper serviceContextWrapper) throws Exception {
        return ddlRecordService.updateRecord(recordId, i, jsonContent, b, serviceContextWrapper);
    }
}
