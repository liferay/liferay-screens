package com.liferay.mobile.screens.cache.ddl.form;

import com.liferay.mobile.screens.cache.CachedType;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLFormCache extends RecordCache {

	public static final String TABLE_NAME = "ddlform";

	public DDLFormCache() {
		super();
	}

	public DDLFormCache(long groupId, Record record, JSONObject jsonObject) {
		super(groupId, record, jsonObject);
	}

	@Override
	public CachedType getCachedType() {
		return DefaultCachedType.DDL_FORM;
	}

	@Override
	public String getId() {
		return String.valueOf(_recordSetId);
	}

}
