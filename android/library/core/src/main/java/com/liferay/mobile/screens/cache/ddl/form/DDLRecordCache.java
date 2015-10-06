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
	public static final String SYNC_DATE = "sync_date";
	public static final String DIRTY = "dirty";
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
		_dirty = 1;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public boolean isDirty() {
		return _dirty == 1;
	}

	public void setDirty(boolean dirty) {
		_dirty = dirty ? 1 : 0;
	}

	public TableCache getTableCache() {
		return new TableCache(String.valueOf(getId()), getCachedType(), _content, _groupId, null, new Locale(_locale));
	}

	public Date getSyncDate() {
		return new Date(_syncDate);
	}

	public void setSyncDate(Date syncDate) {
		_syncDate = syncDate.getTime();
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

	private long _syncDate;
	private int _dirty;
	private long _groupId;
	private long _dateAdded;
}
