package com.liferay.mobile.screens.rating.interactor.update;

import android.support.annotation.NonNull;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.security.InvalidParameterException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingUpdateInteractorImpl extends BaseRemoteInteractor<RatingListener>
	implements RatingUpdateInteractor {

	public RatingUpdateInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
		_screensratingsentryService = getScreensratingsentryService();
	}

	@NonNull private ScreensratingsentryService getScreensratingsentryService() {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new RatingUpdateCallback(getTargetScreenletId()));
		return new ScreensratingsentryService(session);
	}

	@Override public void updateRating(long classPK, String className, double score, int stepCount)
		throws Exception {
		validate(score);
		_screensratingsentryService.updateRatingEntry(classPK, className, score, stepCount);
	}

	public void onEvent(RatingUpdateEvent event) {
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
						result.getDouble("userScore"), result.getDouble("totalScore"),
						result.getInt("totalCount")));
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

	protected void validate(double score) throws InvalidParameterException {
		if ((score > 1) || (score < 0)) {
			throw new InvalidParameterException(
				"Score " + score + " is not a double value between 0 and 1");
		}
	}

	private final ScreensratingsentryService _screensratingsentryService;
}
