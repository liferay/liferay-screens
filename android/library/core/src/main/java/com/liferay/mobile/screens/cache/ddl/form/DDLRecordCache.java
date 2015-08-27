package com.liferay.mobile.screens.cache.ddl.form;

import com.liferay.mobile.screens.cache.CachedType;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class DDLRecordCache extends RecordCache {

	public static final String TABLE_NAME = "ddlRecord";
	public static final String SENT = "sent";
	public static final String GROUP_ID = "groupId";

	public DDLRecordCache() {
		super();
	}

	public DDLRecordCache(Long groupId, Record record, JSONObject jsonObject, boolean sent) {
		super(record, jsonObject);
		_sent = sent;
		_groupId = groupId == null ? LiferayServerContext.getGroupId() : groupId;
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

	private boolean _sent;
	private Long _groupId;
}
