package com.liferay.mobile.screens.cache.ddl.form.resolvers;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.liferay.mobile.screens.cache.ddl.form.DDLFormCache;
import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery;

/**
 * @author Javier Gamarra
 */
public class DDLFormPutResolver extends DefaultPutResolver<DDLFormCache> {

	@NonNull
	@Override
	protected InsertQuery mapToInsertQuery(@NonNull DDLFormCache object) {
		return InsertQuery.builder()
			.table(DDLFormCache.TABLE_NAME)
			.build();
	}

	@NonNull
	@Override
	protected UpdateQuery mapToUpdateQuery(@NonNull DDLFormCache object) {
		return UpdateQuery.builder()
			.table(DDLFormCache.TABLE_NAME)
			.where(DDLFormCache.RECORD_SET_ID + " = ? ")
			.whereArgs(object.getId())
			.build();
	}

	@NonNull
	@Override
	protected ContentValues mapToContentValues(@NonNull DDLFormCache object) {
		ContentValues contentValues = new ContentValues(5);

		contentValues.put(DDLFormCache.STRUCTURE_ID, object.getStructureId());
		contentValues.put(DDLFormCache.RECORD_ID, object.getRecordId());
		contentValues.put(DDLFormCache.RECORD_SET_ID, object.getRecordSetId());
		contentValues.put(DDLFormCache.CONTENT, object.getContent());
		contentValues.put(DDLFormCache.LOCALE, object.getLocale());

		return contentValues;
	}
}
