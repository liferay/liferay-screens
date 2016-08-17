package com.liferay.mobile.screens.rating.interactor.delete;

import com.liferay.mobile.screens.rating.interactor.BaseRatingInteractorImpl;
import com.liferay.mobile.screens.rating.interactor.RatingEvent;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingDeleteInteractorImpl extends BaseRatingInteractorImpl {

	@Override
	public RatingEvent execute(Object... args) throws Exception {

		final long classPK = (long) args[0];
		final String className = (String) args[1];
		int ratingGroupCounts = (int) args[2];

		JSONObject jsonObject =
			getScreensratingsentryService().deleteRatingsEntry(classPK, className, ratingGroupCounts);

		return new RatingEvent(classPK, className, jsonObject);
	}
}
