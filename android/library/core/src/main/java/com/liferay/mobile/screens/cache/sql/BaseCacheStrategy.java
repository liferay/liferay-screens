package com.liferay.mobile.screens.cache.sql;

import com.liferay.mobile.screens.cache.CachedContent;
import java.util.List;

import static com.liferay.mobile.screens.cache.sql.CacheSQL.queryGet;
import static com.liferay.mobile.screens.cache.sql.StorIOSQLite.queryDelete;

/**
 * @author Javier Gamarra
 */
public abstract class BaseCacheStrategy<E extends CachedContent> implements CacheStrategy<E> {

	@Override
	public E getById(String id) {
		List query = queryGet(getDomainClass(), getTableName(), getQueryById(), id);
		return query.isEmpty() ? null : (E) query.get(0);
	}

	@Override
	public List get(String orderBy, String query, Object... args) {
		return queryGet(getDomainClass(), getTableName(), "", query, args);
	}

	@Override
	public DatabaseResult set(E object) {
		DatabaseResult result = CacheSQL.querySet(object.getTableCache());

		if (result.hasError() || object == object.getTableCache()) {
			return result;
		} else {
			return CacheSQL.querySet(object);
		}
	}

	@Override
	public int clear() {
		return queryDelete(getTableName(), null);
	}

	@Override
	public int clear(String id) {
		return queryDelete(getTableName(), getQueryById(), id);
	}

	protected abstract Class getDomainClass();

	protected abstract String getQueryById();

	protected abstract String getTableName();
}
