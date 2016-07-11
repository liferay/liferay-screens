package com.liferay.mobile.screens.rating.interactor.update;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingUpdateCallback extends InteractorAsyncTaskCallback<JSONObject> {

	public RatingUpdateCallback(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new RatingUpdateEvent(targetScreenletId, e);
	}

	@Override protected BasicEvent createEvent(int targetScreenletId, JSONObject result) {
		return new RatingUpdateEvent(targetScreenletId, result);
	}

	@Override public JSONObject transform(Object obj) throws Exception {
		return (JSONObject) obj;
	}
}
