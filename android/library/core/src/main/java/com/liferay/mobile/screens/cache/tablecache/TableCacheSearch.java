package com.liferay.mobile.screens.cache.tablecache;

import com.liferay.mobile.screens.cache.CachedType;
import com.liferay.mobile.screens.cache.sql.CacheStrategy;
import com.liferay.mobile.screens.context.LiferayServerContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.liferay.mobile.screens.cache.sql.CacheSQL.queryGet;
import static com.liferay.mobile.screens.cache.sql.CacheSQL.querySet;
import static com.liferay.mobile.screens.cache.sql.StorIOSQLite.queryDelete;

/**
 * @author Javier Gamarra
 */
public class TableCacheSearch implements CacheStrategy<TableCache> {

	public static final String TYPE_AND_GROUP_QUERY = TableCache.CACHED_TYPE + " = ? AND "
		+ TableCache.GROUP_ID + " = ?";

	public TableCacheSearch(CachedType cachedType) {
		_cachedType = cachedType;
	}

	@Override
	public TableCache getById(String id) {

		long groupId = LiferayServerContext.getGroupId();

		List elements = queryGet(TableCache.class,
			TableCache.TABLE_NAME,
			TYPE_AND_GROUP_QUERY + " AND " + TableCache.ID + " = ? ",
			_cachedType, groupId, id);

		return elements.isEmpty() ? null : (TableCache) elements.get(0);
	}

	@Override
	public List get(String query, Object... args) {
		List<Object> arguments = new ArrayList<>(Arrays.asList(args));
		arguments.add(0, _cachedType.name());

		return queryGet(TableCache.class, TableCache.TABLE_NAME,
			TableCache.CACHED_TYPE + " = ?" + query, arguments.toArray());
	}

	@Override
	public Object set(TableCache object) {
		return querySet(object);
	}

	@Override
	public void clear() {
		queryDelete(TableCache.TABLE_NAME, null);
	}

	@Override
	public void clear(String id) {
		queryDelete(TableCache.TABLE_NAME, WHERE_BY_ID, id);
	}

	private static final String WHERE_BY_ID = TableCache.ID + " = ?";

	private final CachedType _cachedType;
}
