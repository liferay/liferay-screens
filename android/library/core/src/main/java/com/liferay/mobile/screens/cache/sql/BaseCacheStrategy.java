package com.liferay.mobile.screens.cache.sql;

import com.liferay.mobile.screens.cache.CachedContent;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.List;

import static com.liferay.mobile.screens.cache.sql.CacheSQL.queryGet;
import static com.liferay.mobile.screens.cache.sql.StorIOSQLite.queryDelete;

/**
 * @author Javier Gamarra
 */
public abstract class BaseCacheStrategy<E extends CachedContent> implements CacheStrategy<E> {

	@Override
	public E getById(String id) {
		List query = queryGet(getClass(), getTableName(), getQueryById(), id);
		return query.isEmpty() ? null : (E) query.get(0);
	}

	@Override
	public List get(String query, Object... args) {
		return queryGet(getClass(), getTableName(), query, args);
	}

	@Override
	public Object set(E object) {
		PutResult putResult = (PutResult) CacheSQL.querySet(object.getTableCache());

		if (putResult.wasInserted() || putResult.wasUpdated()) {
			return CacheSQL.querySet(object);
		}
		else {
			return putResult;
		}
	}

	@Override
	public void clear() {
		queryDelete(getTableName(), null);
	}

	@Override
	public void clear(String id) {
		queryDelete(getTableName(), getQueryById(), id);
	}

	protected abstract String getQueryById();

	protected abstract String getTableName();
}
