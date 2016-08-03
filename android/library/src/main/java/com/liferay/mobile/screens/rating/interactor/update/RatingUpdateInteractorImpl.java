package com.liferay.mobile.screens.rating.interactor.update;

import android.support.annotation.NonNull;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;
import com.liferay.mobile.screens.rating.interactor.BaseRatingInteractorImpl;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import java.security.InvalidParameterException;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingUpdateInteractorImpl extends BaseRatingInteractorImpl implements RatingUpdateInteractor {

	public RatingUpdateInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void updateRating(long classPK, String className, double score, int ratingsGroupCount) throws Exception {

		validate(score);

		ScreensratingsentryService service = getScreensratingsentryService();
		service.updateRatingsEntry(classPK, className, score, ratingsGroupCount);
	}

	public void onEvent(RatingUpdateEvent event) {
		processEvent(event);
	}

	protected void validate(double score) throws InvalidParameterException {
		if ((score > 1) || (score < 0)) {
			throw new InvalidParameterException("Score " + score + " is not a double value between 0 and 1");
		}
	}

	@NonNull
	@Override
	protected InteractorAsyncTaskCallback<JSONObject> getCallback() {
		return new RatingUpdateCallback(getTargetScreenletId());
	}
}
