package com.liferay.mobile.screens.ddl.form.operation;

import com.liferay.mobile.android.service.JSONObjectWrapper;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public interface DDLRecordOperation {
	JSONObject addRecord(long groupId, long recordSetId, int i, JSONObject jsonContent, JSONObjectWrapper serviceContextWrapper) throws Exception;

	JSONObject updateRecord(long recordId, int i, JSONObject jsonContent, boolean b, JSONObjectWrapper serviceContextWrapper) throws Exception;

}
