package com.liferay.mobile.screens.ddl.model;

import java.util.Map;

/**
 * @author Javier Gamarra
 */
public interface WithDDM {

	DDMStructure getDDMStructure();

	Map<String, Object> getValues();
}
