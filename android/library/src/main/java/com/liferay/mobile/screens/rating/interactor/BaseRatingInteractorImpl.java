package com.liferay.mobile.screens.rating.interactor;

import android.support.annotation.NonNull;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.base.thread.BaseCachedThreadRemoteInteractor;
import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class BaseRatingInteractorImpl extends BaseCachedThreadRemoteInteractor<RatingListener, OfflineEventNew>
	implements Interactor<RatingListener> {

	@Override
	public void onFailure(Exception e) {
		getListener().error(e, "");
	}

	@Override
	public void onSuccess(OfflineEventNew event) throws Exception {
		JSONObject result = event.getJSONObject();
		AssetRating assetRating = new AssetRating(result.getLong("classPK"), result.getString("className"),
			toIntArray(result.getJSONArray("ratings")), result.getDouble("average"), result.getDouble("userScore"),
			result.getDouble("totalScore"), result.getInt("totalCount"));
		getListener().onRatingOperationSuccess(assetRating);
	}

	@Override
	protected RatingEvent createEventFromArgs(Object... args) throws Exception {
		final long classPK = (long) args[0];
		final String className = (String) args[1];

		return new RatingEvent(classPK, className, 0, new JSONObject());
	}

	@NonNull
	protected ScreensratingsentryService getScreensratingsentryService() {
		return new ScreensratingsentryService(SessionContext.createSessionFromCurrentSession());
	}

	protected int[] toIntArray(JSONArray array) {
		int[] intArray = new int[array.length()];
		for (int i = 0; i < array.length(); i++) {
			intArray[i] = array.optInt(i);
		}
		return intArray;
	}
}
