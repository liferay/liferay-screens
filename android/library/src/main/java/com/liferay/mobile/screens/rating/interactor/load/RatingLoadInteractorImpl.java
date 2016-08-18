package com.liferay.mobile.screens.rating.interactor.load;

import com.liferay.mobile.screens.base.thread.BaseCachedThreadRemoteInteractor;
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
public class RatingLoadInteractorImpl extends BaseCachedThreadRemoteInteractor<RatingListener, RatingEvent> {

	@Override
	public RatingEvent execute(Object... args) throws Exception {

		long entryId = (long) args[0];
		long classPK = (long) args[1];
		String className = (String) args[2];
		int ratingGroupCounts = (int) args[3];

		validate(entryId, className, classPK);

		JSONObject jsonObject = getRatingsEntries(entryId, classPK, className, ratingGroupCounts);

		return new RatingEvent(jsonObject);
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
	public void onFailure(Exception e) {
		getListener().error(e, RatingScreenlet.LOAD_RATINGS_ACTION);
	}

	@Override
	protected String getIdFromArgs(Object... args) throws Exception {
		long entryId = (long) args[0];
		long classPK = (long) args[1];
		String className = (String) args[2];
		int ratingGroupCounts = (int) args[3];

		return (entryId == 0 ? className + "-" + classPK : String.valueOf(entryId)) + "-" + ratingGroupCounts;
	}

	protected int[] toIntArray(JSONArray array) {
		int[] intArray = new int[array.length()];
		for (int i = 0; i < array.length(); i++) {
			intArray[i] = array.optInt(i);
		}
		return intArray;
	}

	private JSONObject getRatingsEntries(long entryId, long classPK, String className, int ratingGroupCounts)
		throws Exception {
		ScreensratingsentryService service =
			new ScreensratingsentryService(SessionContext.createSessionFromCurrentSession());
		if (entryId != 0) {
			return service.getRatingsEntries(entryId, ratingGroupCounts);
		} else {
			return service.getRatingsEntries(classPK, className, ratingGroupCounts);
		}
	}

	private void validate(long entryId, String className, long classPK) {
		if (entryId == 0 && (className == null || classPK == 0)) {
			throw new IllegalArgumentException("Either entryId or className & classPK cannot" + "be empty");
		}
	}
}
