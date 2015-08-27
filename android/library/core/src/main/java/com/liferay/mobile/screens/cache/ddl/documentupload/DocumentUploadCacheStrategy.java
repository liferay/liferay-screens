package com.liferay.mobile.screens.cache.ddl.documentupload;

import com.liferay.mobile.screens.cache.sql.BaseCacheStrategy;
import com.liferay.mobile.screens.cache.sql.CacheStrategy;
import com.liferay.mobile.screens.cache.userportrait.UserPortraitCache;

import java.util.List;
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class DocumentUploadCacheStrategy extends BaseCacheStrategy implements CacheStrategy {

	@Override
	public DocumentUploadCache getById(String id, Long groupId, Long userId, Locale locale) {
		List list = get(
			DocumentUploadCache.PATH + " = ? AND "
				+ DocumentUploadCache.GROUP_ID + " = ? AND "
				+ DocumentUploadCache.USER_ID + " = ? ",
			id,
			groupId,
			userId
		);
		return list.isEmpty() ? null : (DocumentUploadCache) list.get(0);
	}

	@Override
	protected String getQueryById() {
		return WHERE_BY_ID;
	}

	@Override
	protected String getTableName() {
		return DocumentUploadCache.TABLE_NAME;
	}

	@Override
	protected Class getDomainClass() {
		return DocumentUploadCache.class;
	}

	private static final String WHERE_BY_ID = DocumentUploadCache.PATH + " = ?";
}
