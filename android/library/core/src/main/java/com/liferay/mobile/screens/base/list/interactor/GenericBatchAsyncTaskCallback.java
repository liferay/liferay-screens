package com.liferay.mobile.screens.base.list.interactor;

import com.liferay.mobile.android.task.callback.typed.GenericAsyncTaskCallback;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * @author Javier Gamarra
 */
public abstract class GenericBatchAsyncTaskCallback<T>
	extends GenericAsyncTaskCallback<T> {

	public ArrayList<T> inBackground(ArrayList<T> results) throws Exception {
		return results;
	}

	@Override
	public JSONArray inBackground(JSONArray jsonArray) throws Exception {
		results = new ArrayList<>();
		results.add(transform(jsonArray));
		results = inBackground(results);

		return null;
	}

	@Override
	public void onPostExecute(JSONArray jsonArray) throws Exception {
		onSuccess(results);
	}

	public abstract void onSuccess(ArrayList<T> results);

	@Override
	public void onSuccess(T result) {
	}

	protected ArrayList<T> results;

}