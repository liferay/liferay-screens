package com.liferay.mobile.screens.cache.ddl.form;

import com.liferay.mobile.screens.cache.CachedContent;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.cache.sql.CacheStrategy;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.List;

import static com.liferay.mobile.screens.cache.sql.CacheSQL.queryGet;
import static com.liferay.mobile.screens.cache.sql.StorIOSQLite.queryDelete;

/**
 * @author Javier Gamarra
 */
public class DDLFormCacheStrategy implements CacheStrategy {

	@Override
	public DDLFormCache getById(String id) {
		List query = queryGet(DDLFormCache.class, DDLFormCache.TABLE_NAME, WHERE_BY_ID, id);
		return query.isEmpty() ? null : (DDLFormCache) query.get(0);
	}

	@Override
	public List<DDLFormCache> get(String query, Object... args) {
		return queryGet(DDLFormCache.class, DDLFormCache.TABLE_NAME, query, args);
	}

	@Override
	public Object set(CachedContent object) {
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
		queryDelete(DDLFormCache.TABLE_NAME, null);
	}

	@Override
	public void clear(String id) {
		queryDelete(DDLFormCache.TABLE_NAME, WHERE_BY_ID, id);
	}

	private static final String WHERE_BY_ID = DDLFormCache.RECORD_SET_ID + " = ?";
}
