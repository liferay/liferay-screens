package com.liferay.mobile.screens.base.list.interactor;

import com.liferay.mobile.android.callback.typed.GenericBatchCallback;

import org.json.JSONArray;

/**
 * @author Javier Gamarra
 */
public abstract class GenericBatchAsyncTaskCallback<T> extends GenericBatchCallback<T> {

	@Override
	public T inBackground(JSONArray jsonArray) throws Exception {
		return inBackground(transform(jsonArray));
	}

}