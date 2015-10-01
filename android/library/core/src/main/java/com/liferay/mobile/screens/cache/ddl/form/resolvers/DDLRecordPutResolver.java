package com.liferay.mobile.screens.cache.ddl.form.resolvers;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.liferay.mobile.screens.cache.ddl.form.DDLRecordCache;
import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery;

/**
 * @author Javier Gamarra
 */
public class DDLRecordPutResolver extends DefaultPutResolver<DDLRecordCache> {

	@NonNull
	@Override
	protected InsertQuery mapToInsertQuery(@NonNull DDLRecordCache object) {
		return InsertQuery.builder()
			.table(DDLRecordCache.TABLE_NAME)
			.build();
	}

	@NonNull
	@Override
	protected UpdateQuery mapToUpdateQuery(@NonNull DDLRecordCache object) {
		return UpdateQuery.builder()
			.table(DDLRecordCache.TABLE_NAME)
			.where(DDLRecordCache.RECORD_ID + " = ? ")
			.whereArgs(object.getId())
			.build();
	}

	@NonNull
	@Override
	protected ContentValues mapToContentValues(@NonNull DDLRecordCache object) {
		ContentValues contentValues = new ContentValues(6);

		contentValues.put(DDLRecordCache.STRUCTURE_ID, object.getStructureId());
		contentValues.put(DDLRecordCache.RECORD_ID, object.getRecordId());
		contentValues.put(DDLRecordCache.RECORD_SET_ID, object.getRecordSetId());
		contentValues.put(DDLRecordCache.CONTENT, object.getContent());
		contentValues.put(DDLRecordCache.LOCALE, object.getLocale());
		contentValues.put(DDLRecordCache.DIRTY, object.isDirty());
		contentValues.put(DDLRecordCache.GROUP_ID, object.getGroupId());
		contentValues.put(DDLRecordCache.DATE_ADDED, object.getDateAdded());

		return contentValues;
	}
}
