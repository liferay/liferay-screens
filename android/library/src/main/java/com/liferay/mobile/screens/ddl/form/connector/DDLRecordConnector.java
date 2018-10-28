package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public interface DDLRecordConnector {
    JSONObject addRecord(long groupId, long recordSetId, int i, JSONObject jsonContent,
        JSONObjectWrapper serviceContextWrapper) throws Exception;

    JSONObject updateRecord(long recordId, int i, JSONObject jsonContent, boolean b,
        JSONObjectWrapper serviceContextWrapper) throws Exception;
}
