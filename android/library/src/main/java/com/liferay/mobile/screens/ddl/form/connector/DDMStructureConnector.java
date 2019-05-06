package com.liferay.mobile.screens.ddl.form.connector;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public interface DDMStructureConnector {
    JSONObject getStructure(long structureId) throws Exception;
}
