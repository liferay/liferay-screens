package com.liferay.mobile.screens.ddl.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public interface WithDDM {

	DDMStructure getDDMStructure();

	void parseDDMStructure(JSONObject jsonObject) throws JSONException;
}
