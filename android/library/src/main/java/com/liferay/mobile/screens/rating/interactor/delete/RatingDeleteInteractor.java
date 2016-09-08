package com.liferay.mobile.screens.rating.interactor.delete;

import com.liferay.mobile.screens.base.thread.BaseCacheWriteInteractor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import com.liferay.mobile.screens.rating.interactor.RatingEvent;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingDeleteInteractor extends BaseCacheWriteInteractor<RatingListener, RatingEvent> {

	@Override
	public RatingEvent execute(RatingEvent event) throws Exception {

		ScreensratingsentryService ratingsEntryService =
			new ScreensratingsentryService(SessionContext.createSessionFromCurrentSession());

		JSONObject jsonObject = ratingsEntryService.deleteRatingsEntry(event.getClassPK(), event.getClassName(),
			event.getRatingGroupCounts());

		return new RatingEvent(event.getClassPK(), event.getClassName(), event.getRatingGroupCounts(), jsonObject);
	}

	@Override
	public void onSuccess(RatingEvent event) throws Exception {
		JSONObject result = event.getJSONObject();
		AssetRating assetRating = new AssetRating(result.getLong("classPK"), result.getString("className"),
			toIntArray(result.getJSONArray("ratings")), result.getDouble("average"), result.getDouble("userScore"),
			result.getDouble("totalScore"), result.getInt("totalCount"));
		getListener().onRatingOperationSuccess(assetRating);
	}

	@Override
	protected void onFailure(RatingEvent event) {
		getListener().error(event.getException(), RatingScreenlet.DELETE_RATING_ACTION);
	}

	protected int[] toIntArray(JSONArray array) {
		int[] intArray = new int[array.length()];
		for (int i = 0; i < array.length(); i++) {
			intArray[i] = array.optInt(i);
		}
		return intArray;
	}
}
