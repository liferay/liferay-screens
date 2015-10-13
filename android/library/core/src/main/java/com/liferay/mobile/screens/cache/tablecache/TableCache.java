package com.liferay.mobile.screens.cache.tablecache;

import com.liferay.mobile.screens.cache.CachedContent;
import com.liferay.mobile.screens.cache.CachedType;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.LiferayLocale;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.util.Date;
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
@StorIOSQLiteType(table = TableCache.TABLE_NAME)
public class TableCache implements CachedContent {

	public static final String TABLE_NAME = "cache";
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String DATE = "date";
	public static final String USER_ID = "userId";
	public static final String GROUP_ID = "groupId";
	public static final String CONTENT = "content";
	public static final String DIRTY = "dirty";
	public static final String SYNC_DATE = "sync_date";
	public static final String LOCALE = "locale";

	public TableCache() {
		super();
	}

	public TableCache(String id, CachedType cachedType, String content) {
		this(id, cachedType, content, null, null, null);
	}

	public TableCache(String id, CachedType cachedType, String content, Long groupId, Long userId, Locale locale) {
		super();
		_id = id;
		_cachedType = cachedType;
		_cachedTypeString = cachedType.name();
		_content = content;
		_date = new Date().getTime();
		_userId = userId == null ? SessionContext.getDefaultUserId() : userId;
		_groupId = groupId == null ? LiferayServerContext.getGroupId() : groupId;
		_locale = locale == null ? LiferayLocale.getDefaultSupportedLocale() :
			LiferayLocale.getSupportedLocale(locale.getDisplayLanguage());
		_dirty = 1;
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

	@Override
	public TableCache getTableCache() {
		return this;
	}

	public CachedType getCachedType() {
		return _cachedType;
	}

	public void setType(CachedType type) {
		_cachedType = type;
		_cachedTypeString = type.name();
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

	public int getDirty() {
		return _dirty;
	}

	public void setDirty(boolean dirty) {
		_dirty = dirty ? 0 : 1;
	}

	public void setDirty(int dirty) {
		_dirty = dirty;
	}

	public Long getGroupId() {
		return _groupId;
	}

	public void setGroupId(Long groupId) {
		_groupId = groupId;
	}

	public Long getUserId() {
		return _userId;
	}

	public void setUserId(Long userId) {
		_userId = userId;
	}

	public Date getSyncDate() {
		return new Date(_syncDate);
	}

	public void setSyncDate(Date syncDate) {
		_syncDate = syncDate.getTime();
	}

	public String getLocale() {
		return _locale;
	}

	public void setLocale(String locale) {
		_locale = locale;
	}

	@StorIOSQLiteColumn(name = ID, key = true)
	String _id;
	@StorIOSQLiteColumn(name = TYPE, key = true)
	String _cachedTypeString;
	@StorIOSQLiteColumn(name = GROUP_ID)
	Long _groupId;
	@StorIOSQLiteColumn(name = DATE)
	Long _date;
	@StorIOSQLiteColumn(name = CONTENT)
	String _content;
	@StorIOSQLiteColumn(name = USER_ID)
	Long _userId;
	@StorIOSQLiteColumn(name = DIRTY)
	int _dirty;
	@StorIOSQLiteColumn(name = SYNC_DATE)
	Long _syncDate;
	@StorIOSQLiteColumn(name = LOCALE)
	String _locale;

	private CachedType _cachedType;
}