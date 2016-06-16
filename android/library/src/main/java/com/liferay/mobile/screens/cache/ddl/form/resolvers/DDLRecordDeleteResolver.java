package com.liferay.mobile.screens.cache.ddl.form.resolvers;

import android.support.annotation.NonNull;

import com.liferay.mobile.screens.cache.ddl.form.DDLRecordCache;
import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

/**
 * @author Javier Gamarra
 */
public class DDLRecordDeleteResolver extends DefaultDeleteResolver<DDLRecordCache> {

	@NonNull
	@Override
	protected DeleteQuery mapToDeleteQuery(@NonNull DDLRecordCache object) {
		return DeleteQuery.builder()
			.table(DDLRecordCache.TABLE_NAME)
			.where(DDLRecordCache.RECORD_ID + " = ?")
			.whereArgs(object.getId())
			.build();
	}
}
