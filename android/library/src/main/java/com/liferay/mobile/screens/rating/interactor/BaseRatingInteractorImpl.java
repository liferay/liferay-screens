package com.liferay.mobile.screens.rating.interactor;

import android.support.annotation.NonNull;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.base.thread.BaseRemoteInteractorNew;
import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class BaseRatingInteractorImpl extends BaseRemoteInteractorNew<RatingListener, BasicThreadEvent>
	implements Interactor<RatingListener> {

	@Override
	public void onFailure(Exception e) {
		getListener().onRatingOperationFailure(e);
	}

	@Override
	public void onSuccess(BasicThreadEvent event) throws Exception {
		JSONObject result = event.getJSONObject();
		AssetRating assetRating = new AssetRating(result.getLong("classPK"), result.getString("className"),
			toIntArray(result.getJSONArray("ratings")), result.getDouble("average"), result.getDouble("userScore"),
			result.getDouble("totalScore"), result.getInt("totalCount"));
		getListener().onRatingOperationSuccess(assetRating);
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
