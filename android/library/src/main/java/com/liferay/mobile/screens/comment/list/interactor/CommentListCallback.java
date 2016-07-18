package com.liferay.mobile.screens.comment.list.interactor;

import android.util.Pair;
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.models.CommentEntry;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alejandro Hernández
 */
public class CommentListCallback extends BaseListCallback<CommentEntry> {

	public CommentListCallback(int targetScreenletId, Pair<Integer, Integer> rowsRange, Locale locale) {
		super(targetScreenletId, rowsRange, locale);
	}

	@Override public CommentEntry createEntity(Map<String, Object> stringObjectMap) {
		return null;
	}
}
