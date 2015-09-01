package com.liferay.mobile.screens.cache.ddl.form;

import com.liferay.mobile.screens.cache.CachedType;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;

import java.util.Date;
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class DDLRecordCache extends RecordCache {

	public static final String TABLE_NAME = "ddlRecord";
	public static final String SENT = "sent";
	public static final String GROUP_ID = "groupId";
	public static final String DATE_ADDED = "dateAdded";

	public DDLRecordCache() {
		super();
		_dateAdded = new Date().getTime();
	}

	public DDLRecordCache(Long groupId, Record record, JSONObject jsonObject) {
		super(record, jsonObject);
		_groupId = groupId == null ? LiferayServerContext.getGroupId() : groupId;
		_dateAdded = new Date().getTime();
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public boolean isSent() {
		return _sent;
	}

	public void setSent(boolean sent) {
		_sent = sent;
	}

	public TableCache getTableCache() {
		return new TableCache(String.valueOf(getId()), getCachedType(), _content, _groupId, null, new Locale(_locale));
	}

	@Override
	public CachedType getCachedType() {
		return DefaultCachedType.DDL_RECORD;
	}

	@Override
	public String getId() {
		return String.valueOf(_recordId);
	}

	public long getDateAdded() {
		return _dateAdded;
	}

	public void setDateAdded(long dateAdded) {
		_dateAdded = dateAdded;
	}

	private boolean _sent;
	private long _groupId;
	private long _dateAdded;
}
