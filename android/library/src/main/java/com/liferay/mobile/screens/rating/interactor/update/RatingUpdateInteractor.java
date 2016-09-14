package com.liferay.mobile.screens.rating.interactor.update;

import com.liferay.mobile.screens.base.interactor.BaseCacheWriteInteractor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import com.liferay.mobile.screens.rating.interactor.RatingEvent;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import java.security.InvalidParameterException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingUpdateInteractor extends BaseCacheWriteInteractor<RatingListener, RatingEvent> {

	@Override
	public RatingEvent execute(RatingEvent event) throws Exception {

		validate(event.getScore());

		ScreensratingsentryService ratingsEntryService =
			new ScreensratingsentryService(SessionContext.createSessionFromCurrentSession());

		JSONObject jsonObject =
			ratingsEntryService.updateRatingsEntry(event.getClassPK(), event.getClassName(), event.getScore(),
				event.getRatingGroupCounts());
		event.setJSONObject(jsonObject);
		return event;
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
		getListener().error(event.getException(), RatingScreenlet.UPDATE_RATING_ACTION);
	}

	protected void validate(double score) throws InvalidParameterException {
		if ((score > 1) || (score < 0)) {
			throw new InvalidParameterException("Score " + score + " is not a double value between 0 and 1");
		}
	}

	protected int[] toIntArray(JSONArray array) {
		int[] intArray = new int[array.length()];
		for (int i = 0; i < array.length(); i++) {
			intArray[i] = array.optInt(i);
		}
		return intArray;
	}
}
