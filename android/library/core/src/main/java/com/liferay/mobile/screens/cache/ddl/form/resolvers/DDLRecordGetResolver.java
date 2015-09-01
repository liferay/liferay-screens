package com.liferay.mobile.screens.cache.ddl.form.resolvers;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.liferay.mobile.screens.cache.ddl.form.DDLRecordCache;
import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

/**
 * @author Javier Gamarra
 */
public class DDLRecordGetResolver extends DefaultGetResolver<DDLRecordCache> {
	@NonNull
	@Override
	public DDLRecordCache mapFromCursor(Cursor cursor) {

		DDLRecordCache object = new DDLRecordCache();

		object.setContent(cursor.getString(cursor.getColumnIndex(DDLRecordCache.CONTENT)));
		object.setRecordSetId(cursor.getLong(cursor.getColumnIndex(DDLRecordCache.RECORD_SET_ID)));
		object.setRecordId(cursor.getLong(cursor.getColumnIndex(DDLRecordCache.RECORD_ID)));
		object.setStructureId(cursor.getLong(cursor.getColumnIndex(DDLRecordCache.STRUCTURE_ID)));
		object.setLocale(cursor.getString(cursor.getColumnIndex(DDLRecordCache.LOCALE)));
		object.setSent(cursor.getInt(cursor.getColumnIndex(DDLRecordCache.SENT)) == 1);
		object.setGroupId(cursor.getLong(cursor.getColumnIndex(DDLRecordCache.GROUP_ID)));
		object.setDateAdded(cursor.getLong(cursor.getColumnIndex(DDLRecordCache.DATE_ADDED)));

		return object;
	}
}
