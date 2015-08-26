package com.liferay.mobile.screens.cache.userportrait;

import com.liferay.mobile.screens.cache.sql.BaseCacheStrategy;
import com.liferay.mobile.screens.cache.sql.CacheStrategy;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class UserPortraitCacheStrategy extends BaseCacheStrategy<UserPortraitCache> implements CacheStrategy<UserPortraitCache> {

	@Override
	public UserPortraitCache getById(String id, Long groupId, Long userId, Locale locale) {
		return getById(id);
	}

	@Override
	protected String getQueryById() {
		return WHERE_BY_ID;
	}

	@Override
	protected String getTableName() {
		return UserPortraitCache.TABLE_NAME;
	}

	private static final String WHERE_BY_ID = UserPortraitCache.USER_ID + " = ?";
}
