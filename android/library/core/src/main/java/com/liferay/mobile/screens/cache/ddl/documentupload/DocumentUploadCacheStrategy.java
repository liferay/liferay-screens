package com.liferay.mobile.screens.cache.ddl.documentupload;

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
public class DocumentUploadCacheStrategy implements CacheStrategy {

	@Override
	public DocumentUploadCache getById(String id) {
		List query = queryGet(DocumentUploadCache.class, DocumentUploadCache.TABLE_NAME, WHERE_BY_ID, id);
		return query.isEmpty() ? null : (DocumentUploadCache) query.get(0);
	}

	@Override
	public List<DocumentUploadCache> get(String query, Object... args) {
		return queryGet(DocumentUploadCache.class, DocumentUploadCache.TABLE_NAME, query, args);
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
		queryDelete(DocumentUploadCache.TABLE_NAME, null);
	}

	@Override
	public void clear(String id) {
		queryDelete(DocumentUploadCache.TABLE_NAME, WHERE_BY_ID, id);
	}

	private static final String WHERE_BY_ID = DocumentUploadCache.PATH + " = ?";
}
