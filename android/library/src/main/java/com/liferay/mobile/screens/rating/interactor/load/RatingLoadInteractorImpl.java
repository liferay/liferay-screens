package com.liferay.mobile.screens.rating.interactor.load;

import android.support.annotation.NonNull;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import com.liferay.mobile.screens.util.LiferayLogger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingLoadInteractorImpl extends BaseRemoteInteractor<RatingListener> implements RatingLoadInteractor {

	public RatingLoadInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
		_screensratingsentryService = getScreensratingsentryService();
	}

	@NonNull private ScreensratingsentryService getScreensratingsentryService() {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new RatingLoadCallback(getTargetScreenletId()));
		return new ScreensratingsentryService(session);
	}

	@Override public void loadRatings(long entryId, long classPK, String className, int ratingsGroupCount) throws Exception {
		validate(entryId, className, classPK);

		if (entryId != 0) {
			_screensratingsentryService.getRatingsEntries(entryId, ratingsGroupCount);
		} else {
			_screensratingsentryService.getRatingsEntries(classPK, className, ratingsGroupCount);
		}
	}

	public void onEvent(RatingLoadEvent event) {
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

	protected int[] toIntArray(JSONArray array) {
		int[] intArray = new int[array.length()];
		for (int i = 0; i < array.length(); ++i) {
			intArray[i] = array.optInt(i);
		}
		return intArray;
	}

	protected void validate(long entryId, String className, long classPK) {
		if (entryId == 0 && (className == null || classPK == 0)) {
			throw new IllegalArgumentException("Either entryId or className & classPK cannot" + "be empty");
		}
	}

	private final ScreensratingsentryService _screensratingsentryService;
}
