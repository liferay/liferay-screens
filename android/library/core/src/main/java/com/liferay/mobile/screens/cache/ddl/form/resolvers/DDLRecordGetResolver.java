package com.liferay.mobile.screens.cache.ddl.form.resolvers;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.liferay.mobile.screens.cache.ddl.form.DDLRecordCache;
import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

import java.util.Date;

/**
 * @author Javier Gamarra
 */
public class DDLRecordGetResolver extends DefaultGetResolver<DDLRecordCache> {

	@NonNull
	@Override
	public DDLRecordCache mapFromCursor(@NonNull Cursor cursor) {

		DDLRecordCache object = new DDLRecordCache();

		object.setContent(cursor.getString(cursor.getColumnIndex(DDLRecordCache.CONTENT)));
		object.setRecordSetId(cursor.getLong(cursor.getColumnIndex(DDLRecordCache.RECORD_SET_ID)));
		object.setRecordId(cursor.getLong(cursor.getColumnIndex(DDLRecordCache.RECORD_ID)));
		object.setStructureId(cursor.getLong(cursor.getColumnIndex(DDLRecordCache.STRUCTURE_ID)));
		object.setLocale(cursor.getString(cursor.getColumnIndex(DDLRecordCache.LOCALE)));
		object.setDirty(cursor.getInt(cursor.getColumnIndex(DDLRecordCache.DIRTY)) == 1);
		object.setSyncDate(new Date(cursor.getLong(cursor.getColumnIndex(DDLRecordCache.SYNC_DATE))));
		object.setGroupId(cursor.getLong(cursor.getColumnIndex(DDLRecordCache.GROUP_ID)));
		object.setDateAdded(cursor.getLong(cursor.getColumnIndex(DDLRecordCache.DATE_ADDED)));

		return object;
	}
}
