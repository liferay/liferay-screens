package com.liferay.mobile.screens.rating.interactor.load;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingLoadCallback extends InteractorAsyncTaskCallback<JSONObject> {

	public RatingLoadCallback(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new RatingLoadEvent(targetScreenletId, e);
	}

	@Override protected BasicEvent createEvent(int targetScreenletId, JSONObject result) {
		return new RatingLoadEvent(targetScreenletId, result);
	}

	@Override public JSONObject transform(Object obj) throws Exception {
		return (JSONObject) obj;
	}
}
