package com.liferay.mobile.screens.rating.interactor.update;

import com.liferay.mobile.screens.base.thread.BaseCachedWriteThreadRemoteInteractor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import com.liferay.mobile.screens.rating.interactor.RatingEvent;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import java.security.InvalidParameterException;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.liferay.mobile.screens.R.attr.classPK;

/**
 * @author Alejandro Hernández
 */
public class RatingUpdateInteractorImpl extends BaseCachedWriteThreadRemoteInteractor<RatingListener, RatingEvent> {

	@Override
	public RatingEvent execute(RatingEvent event) throws Exception {

		validate(event.getScore());

		ScreensratingsentryService ratingsEntryService =
			new ScreensratingsentryService(SessionContext.createSessionFromCurrentSession());

		JSONObject jsonObject = ratingsEntryService.updateRatingsEntry(classPK, event.getClassName(), event.getScore(),
			event.getRatingGroupCounts());
		return new RatingEvent(classPK, event.getClassName(), event.getRatingGroupCounts(), jsonObject);
	}

	@Override
	protected RatingEvent createEvent(Object[] args) throws Exception {
		long classPK = (long) args[0];
		String className = (String) args[1];
		int ratingGroupCounts = (int) args[2];
		double score = (double) args[3];

		return new RatingEvent(classPK, className, ratingGroupCounts, score);
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
