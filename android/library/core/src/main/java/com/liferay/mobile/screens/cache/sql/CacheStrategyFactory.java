package com.liferay.mobile.screens.cache.sql;

import com.liferay.mobile.screens.cache.CachedType;
import com.liferay.mobile.screens.cache.ddl.documentupload.DocumentUploadSearch;
import com.liferay.mobile.screens.cache.ddl.form.DDLFormSearch;
import com.liferay.mobile.screens.cache.ddl.form.DDLRecordSearch;
import com.liferay.mobile.screens.cache.tablecache.TableCacheSearch;
import com.liferay.mobile.screens.cache.userportrait.UserPortraitSearch;

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
			return new DocumentUploadSearch();
		}
		else if (DDL_FORM.equals(cachedType)) {
			return new DDLFormSearch();
		}
		else if (DDL_RECORD.equals(cachedType)) {
			return new DDLRecordSearch();
		}
		else if (USER_PORTRAIT.equals(cachedType)) {
			return new UserPortraitSearch();
		}
		else {
			return new TableCacheSearch(cachedType);
		}
	}

}
