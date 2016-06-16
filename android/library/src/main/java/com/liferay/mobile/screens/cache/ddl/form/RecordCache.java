package com.liferay.mobile.screens.cache.ddl.form;

import com.liferay.mobile.screens.cache.CachedContent;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public abstract class RecordCache implements CachedContent {

	public static final String STRUCTURE_ID = "structureId";
	public static final String RECORD_SET_ID = "recordSetId";
	public static final String RECORD_ID = "recordId";
	public static final String LOCALE = "locale";
	public static final String CONTENT = "content";

	public RecordCache() {
		super();
	}

	public RecordCache(Record record, JSONObject jsonObject) {
		_recordId = record.getRecordId();
		_recordSetId = record.getRecordSetId();
		_structureId = record.getStructureId();
		_locale = record.getLocale().getLanguage();
		_content = jsonObject.toString();
	}

	public Record getRecord() {
		Record record = new Record(new Locale(_locale));
		record.setStructureId(_structureId);
		record.setRecordSetId(_recordSetId);
		record.setRecordId(_recordId);
		return record;
	}

	public long getStructureId() {
		return _structureId;
	}

	public void setStructureId(long structureId) {
		_structureId = structureId;
	}

	public long getRecordId() {
		return _recordId;
	}

	public void setRecordId(long recordId) {
		_recordId = recordId;
	}

	public long getRecordSetId() {
		return _recordSetId;
	}

	public void setRecordSetId(long recordSetId) {
		_recordSetId = recordSetId;
	}

	public String getLocale() {
		return _locale;
	}

	public void setLocale(String locale) {
		_locale = locale;
	}

	public String getContent() {
		return _content;
	}

	public void setContent(String content) {
		_content = content;
	}

	public JSONObject getJSONContent() throws JSONException {
		return new JSONObject(_content);
	}

	public TableCache getTableCache() {
		long groupId = LiferayServerContext.getGroupId();
		return new TableCache(String.valueOf(getId()), getCachedType(), _content, groupId, null, new Locale(_locale));
	}

	protected long _structureId;
	protected long _recordId;
	protected long _recordSetId;
	protected String _locale;
	protected String _content;
}
