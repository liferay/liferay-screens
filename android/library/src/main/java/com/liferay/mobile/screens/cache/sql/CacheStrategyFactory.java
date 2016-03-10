package com.liferay.mobile.screens.cache.sql;

import com.liferay.mobile.screens.cache.CachedType;
import com.liferay.mobile.screens.cache.ddl.documentupload.DocumentUploadCacheStrategy;
import com.liferay.mobile.screens.cache.ddl.form.DDLFormCacheStrategy;
import com.liferay.mobile.screens.cache.ddl.form.DDLRecordCacheStrategy;
import com.liferay.mobile.screens.cache.tablecache.TableCacheStrategy;
import com.liferay.mobile.screens.cache.userportrait.UserPortraitCacheStrategy;

import static com.liferay.mobile.screens.cache.DefaultCachedType.DDL_FORM;
import static com.liferay.mobile.screens.cache.DefaultCachedType.DDL_RECORD;
import static com.liferay.mobile.screens.cache.DefaultCachedType.DOCUMENT_UPLOAD;
import static com.liferay.mobile.screens.cache.DefaultCachedType.USER_PORTRAIT;

/**
 * @author Javier Gamarra
 */
public class CacheStrategyFactory {

	public CacheStrategy recoverStrategy(CachedType cachedType) {
		if (DOCUMENT_UPLOAD.equals(cachedType)) {
			return new DocumentUploadCacheStrategy();
		}
		else if (DDL_FORM.equals(cachedType)) {
			return new DDLFormCacheStrategy();
		}
		else if (DDL_RECORD.equals(cachedType)) {
			return new DDLRecordCacheStrategy();
		}
		else if (USER_PORTRAIT.equals(cachedType)) {
			return new UserPortraitCacheStrategy();
		}
		else {
			return new TableCacheStrategy(cachedType);
		}
	}
}
