package com.liferay.mobile.screens.ddl.form.connector;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public interface DDLRecordSetConnector {
	JSONObject getRecordSet(long recordSetId) throws Exception;
}
