package com.liferay.mobile.screens.cache.ddl.form;

import com.liferay.mobile.screens.cache.CachedType;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLRecordCache extends RecordCache {

	public static final String TABLE_NAME = "ddlRecord";
	public static final String SENT = "sent";

	public DDLRecordCache() {
		super();
	}

	public DDLRecordCache(long groupId, Record record, JSONObject jsonObject, boolean sent) {
		super(groupId, record, jsonObject);
		_sent = sent;
	}

	public boolean isSent() {
		return _sent;
	}

	public void setSent(boolean sent) {
		_sent = sent;
	}

	@Override
	public CachedType getCachedType() {
		return DefaultCachedType.DDL_RECORD;
	}

	@Override
	public String getId() {
		return String.valueOf(_recordId);
	}

	private boolean _sent;
}
