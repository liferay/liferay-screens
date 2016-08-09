package com.liferay.mobile.screens.rating.interactor.delete;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingDeleteCallback extends InteractorAsyncTaskCallback<JSONObject> {

	public RatingDeleteCallback(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new RatingDeleteEvent(targetScreenletId, e);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, JSONObject result) {
		return new RatingDeleteEvent(targetScreenletId, result);
	}

	@Override
	public JSONObject transform(Object obj) throws Exception {
		return (JSONObject) obj;
	}
}
