package com.liferay.mobile.screens.cache.ddl.form.resolvers;

import android.support.annotation.NonNull;

import com.liferay.mobile.screens.cache.ddl.form.DDLFormCache;
import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

/**
 * @author Javier Gamarra
 */
public class DDLFormDeleteResolver extends DefaultDeleteResolver<DDLFormCache> {

	@NonNull
	@Override
	protected DeleteQuery mapToDeleteQuery(@NonNull DDLFormCache object) {
		return DeleteQuery.builder()
			.table(DDLFormCache.TABLE_NAME)
			.where(DDLFormCache.RECORD_SET_ID + " = ?")
			.whereArgs(object.getId())
			.build();
	}
}
