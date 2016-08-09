package com.liferay.mobile.screens.rating.interactor;

import android.support.annotation.NonNull;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import com.liferay.mobile.screens.util.LiferayLogger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseRatingInteractorImpl extends BaseRemoteInteractor<RatingListener>
	implements Interactor<RatingListener> {

	public BaseRatingInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@NonNull
	protected abstract InteractorAsyncTaskCallback<JSONObject> getCallback();

	protected void processEvent(JSONObjectEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onRatingOperationFailure(event.getException());
		} else {
			try {
				JSONObject result = event.getJSONObject();
				getListener().onRatingOperationSuccess(
					new AssetRating(result.getLong("classPK"), result.getString("className"),
						toIntArray(result.getJSONArray("ratings")), result.getDouble("average"),
						result.getDouble("userScore"), result.getDouble("totalScore"), result.getInt("totalCount")));
			} catch (JSONException e) {
				LiferayLogger.e(e.getMessage());
				getListener().onRatingOperationFailure(e);
			}
		}
	}

	@NonNull
	protected ScreensratingsentryService getScreensratingsentryService() {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(getCallback());
		return new ScreensratingsentryService(session);
	}

	protected int[] toIntArray(JSONArray array) {
		int[] intArray = new int[array.length()];
		for (int i = 0; i < array.length(); i++) {
			intArray[i] = array.optInt(i);
		}
		return intArray;
	}
}
