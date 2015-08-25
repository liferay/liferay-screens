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
public class DDLRecordCacheStrategy implements CacheStrategy {

	@Override
	public DDLRecordCache getById(String id) {
		List query = queryGet(DDLRecordCache.class, DDLRecordCache.TABLE_NAME, WHERE_BY_ID, id);
		return query.isEmpty() ? null : (DDLRecordCache) query.get(0);
	}

	@Override
	public List<DDLRecordCache> get(String query, Object... args) {
		return queryGet(DDLRecordCache.class, DDLRecordCache.TABLE_NAME, query, args);
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
		queryDelete(DDLRecordCache.TABLE_NAME, null);
	}

	@Override
	public void clear(String id) {
		queryDelete(DDLRecordCache.TABLE_NAME, WHERE_BY_ID, id);
	}

	private static final String WHERE_BY_ID = DDLRecordCache.RECORD_ID + " = ?";
}
