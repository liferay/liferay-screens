package com.liferay.mobile.screens.cache.ddl.form;

import com.liferay.mobile.screens.cache.sql.BaseCacheStrategy;
import com.liferay.mobile.screens.cache.sql.CacheStrategy;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class DDLFormCacheStrategy extends BaseCacheStrategy<DDLFormCache> implements CacheStrategy<DDLFormCache> {

	@Override
	public DDLFormCache getById(String id, Long groupId, Long userId, Locale locale) {
		return getById(id);
	}

	@Override
	protected String getQueryById() {
		return WHERE_BY_ID;
	}

	@Override
	protected String getTableName() {
		return DDLFormCache.TABLE_NAME;
	}

	@Override
	protected Class getDomainClass() {
		return DDLFormCache.class;
	}

	private static final String WHERE_BY_ID = DDLFormCache.RECORD_SET_ID + " = ?";
}
