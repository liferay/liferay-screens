package com.liferay.mobile.screens.cache;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.util.Date;

/**
 * @author Javier Gamarra
 */
@StorIOSQLiteType(table = CachedResult.TABLE_NAME)
public class CachedResult implements CachedContent {

	public static final String TABLE_NAME = "cached_result";
	public static final String ID = "id";
	public static final String CACHED_TYPE = "cached_type";
	public static final String DATE = "date";
	public static final String CONTENT = "content";

	public CachedResult() {
		super();
	}

	public CachedResult(String id, CachedType cachedType) {
		this(id, cachedType, null);
	}

	public CachedResult(String id, CachedType cachedType, String content) {
		super();
		_id = id;
		_cachedType = cachedType.name();
		_content = content;
		_date = new Date().getTime();
	}

	public void setDate(Date date) {
		_date = date.getTime();
	}

	public String getId() {
		return _id;
	}

	public void setId(String id) {
		_id = id;
	}

	public CachedType getCachedType() {
		return CachedType.valueOf(_cachedType);
	}

	public void setCachedType(CachedType cachedType) {
		_cachedType = cachedType.name();
	}

	public void setType(String type) {
		_cachedType = type;
	}

	public Long getDate() {
		return _date;
	}

	public void setDate(Long date) {
		_date = date;
	}

	public String getContent() {
		return _content;
	}

	public void setContent(String content) {
		_content = content;
	}

	@StorIOSQLiteColumn(name = ID, key = true)
	String _id;
	@StorIOSQLiteColumn(name = CACHED_TYPE, key = true)
	String _cachedType;
	@StorIOSQLiteColumn(name = DATE)
	Long _date;
	@StorIOSQLiteColumn(name = CONTENT)
	String _content;
}
