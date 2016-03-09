package com.liferay.mobile.screens.cache.ddl.form.resolvers;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.liferay.mobile.screens.cache.ddl.form.DDLFormCache;
import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

/**
 * @author Javier Gamarra
 */
public class DDLFormGetResolver extends DefaultGetResolver<DDLFormCache> {

	@NonNull
	@Override
	public DDLFormCache mapFromCursor(@NonNull Cursor cursor) {

		DDLFormCache object = new DDLFormCache();

		object.setContent(cursor.getString(cursor.getColumnIndex(DDLFormCache.CONTENT)));
		object.setRecordSetId(cursor.getLong(cursor.getColumnIndex(DDLFormCache.RECORD_SET_ID)));
		object.setRecordId(cursor.getLong(cursor.getColumnIndex(DDLFormCache.RECORD_ID)));
		object.setStructureId(cursor.getLong(cursor.getColumnIndex(DDLFormCache.STRUCTURE_ID)));
		object.setLocale(cursor.getString(cursor.getColumnIndex(DDLFormCache.LOCALE)));

		return object;
	}
}
