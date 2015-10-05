package com.liferay.mobile.screens.cache.tablecache;

import com.liferay.mobile.screens.cache.CachedType;
import com.liferay.mobile.screens.cache.sql.BaseCacheStrategy;
import com.liferay.mobile.screens.cache.sql.CacheStrategy;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.LiferayLocale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.liferay.mobile.screens.cache.sql.CacheSQL.queryGet;
import static com.liferay.mobile.screens.cache.sql.CacheSQL.querySet;

/**
 * @author Javier Gamarra
 */
public class TableCacheStrategy extends BaseCacheStrategy<TableCache> implements CacheStrategy<TableCache> {

	public TableCacheStrategy(CachedType cachedType) {
		_cachedType = cachedType;
	}

	@Override
	public TableCache getById(String id, Long groupId, Long userId, Locale locale) {

		Long defaultGroupId = groupId == null ? LiferayServerContext.getGroupId() : groupId;
		Long defaultUserId = userId == null ? (long) SessionContext.getDefaultUserId() : userId;
		String defaultLocale = locale == null ? LiferayLocale.getDefaultSupportedLocale() :
			LiferayLocale.getSupportedLocale(locale.getDisplayLanguage());

		List elements = queryGet(TableCache.class,
			TableCache.TABLE_NAME,
			TYPE_USER_LOCALE_AND_GROUP_CRITERIA
				+ " AND " + TableCache.ID + " = ? ",
			_cachedType, defaultGroupId, defaultUserId, defaultLocale, id);

		return elements.isEmpty() ? null : (TableCache) elements.get(0);
	}

	@Override
	public TableCache getById(String id) {
		return getById(id, null, null, null);
	}

	@Override
	public List get(String query, Object... args) {
		List<Object> arguments = new ArrayList<>(Arrays.asList(args));
		arguments.add(0, _cachedType.name());

		return queryGet(TableCache.class, TableCache.TABLE_NAME,
			TableCache.TYPE + " = ?" + query, arguments.toArray());
	}

	@Override
	public DatabaseResult set(TableCache object) {
		return querySet(object.getTableCache());
	}

	@Override
	protected String getQueryById() {
		return WHERE_BY_ID;
	}

	@Override
	protected String getTableName() {
		return TableCache.TABLE_NAME;
	}

	@Override
	protected Class getDomainClass() {
		return TableCache.class;
	}

	private static final String TYPE_USER_LOCALE_AND_GROUP_CRITERIA
		= TableCache.TYPE + " = ? AND "
		+ TableCache.GROUP_ID + " = ? AND "
		+ TableCache.USER_ID + " = ? AND "
		+ TableCache.LOCALE + " = ? ";

	private static final String WHERE_BY_ID = TableCache.ID + " = ?";

	private final CachedType _cachedType;
}
