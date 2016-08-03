package com.liferay.mobile.screens.rating.interactor.delete;

import android.support.annotation.NonNull;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;
import com.liferay.mobile.screens.rating.interactor.BaseRatingInteractorImpl;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingDeleteInteractorImpl extends BaseRatingInteractorImpl implements RatingDeleteInteractor {

	public RatingDeleteInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void deleteRating(long classPK, String className, int ratingGroupCounts) throws Exception {
		getScreensratingsentryService().deleteRatingsEntry(classPK, className, ratingGroupCounts);
	}

	public void onEvent(RatingDeleteEvent event) {
		processEvent(event);
	}

	@NonNull
	@Override
	protected InteractorAsyncTaskCallback<JSONObject> getCallback() {
		return new RatingDeleteCallback(getTargetScreenletId());
	}
}
