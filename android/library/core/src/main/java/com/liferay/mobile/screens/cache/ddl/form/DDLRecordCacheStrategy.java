package com.liferay.mobile.screens.cache.ddl.form;

import com.liferay.mobile.screens.cache.sql.BaseCacheStrategy;
import com.liferay.mobile.screens.cache.sql.CacheStrategy;

import java.util.List;
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class DDLRecordCacheStrategy extends BaseCacheStrategy<DDLRecordCache> implements CacheStrategy<DDLRecordCache> {

	@Override
	public DDLRecordCache getById(String id, Long groupId, Long userId, Locale locale) {
		List list = get(WHERE_BY_ID_AND_GROUP_ID, id, groupId);
		return list.isEmpty() ? null : (DDLRecordCache) list.get(0);
	}

	@Override
	protected String getQueryById() {
		return WHERE_BY_ID;
	}

	@Override
	protected String getTableName() {
		return DDLRecordCache.TABLE_NAME;
	}

	@Override
	protected Class getDomainClass() {
		return DDLRecordCache.class;
	}

	private static final String WHERE_BY_ID = DDLRecordCache.RECORD_ID + " = ?";
	private static final String WHERE_BY_ID_AND_GROUP_ID = WHERE_BY_ID + " AND " + DDLRecordCache.GROUP_ID + " = ?";
}
