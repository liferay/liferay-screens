package com.liferay.mobile.screens.cache.userportrait;

import com.liferay.mobile.screens.cache.CachedContent;
import com.liferay.mobile.screens.cache.CachedType;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * @author Javier Gamarra
 */
@StorIOSQLiteType(table = UserPortraitCache.TABLE_NAME)
public class UserPortraitCache implements CachedContent {

	public static final String TABLE_NAME = "user_portrait";
	public static final String MALE = "male";
	public static final String PORTRAIT_ID = "portraitId";
	public static final String UUID = "uuid";
	public static final String USER_ID = "userId";
	public static final String GROUP_ID = "groupId";

	public UserPortraitCache() {
		super();
	}

	public UserPortraitCache(long userId, boolean male, long portraitId, String uuid) {
		_userId = userId;
		_male = male;
		_portraitId = portraitId;
		_uuid = uuid;
	}

	public boolean isMale() {
		return _male;
	}

	public long getPortraitId() {
		return _portraitId;
	}

	public String getUuid() {
		return _uuid;
	}

	@Override
	public CachedType getCachedType() {
		return DefaultCachedType.USER_PORTRAIT;
	}

	@Override
	public String getId() {
		return String.valueOf(_portraitId);
	}

	@Override
	public TableCache getTableCache() {
		return new TableCache(String.valueOf(_portraitId), DefaultCachedType.USER_PORTRAIT);
	}

	@StorIOSQLiteColumn(name = MALE)
	boolean _male;
	@StorIOSQLiteColumn(name = PORTRAIT_ID)
	long _portraitId;
	@StorIOSQLiteColumn(name = UUID)
	String _uuid;
	@StorIOSQLiteColumn(name = USER_ID, key = true)
	long _userId;
}
