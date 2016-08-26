package com.liferay.mobile.screens.rating.interactor.delete;

import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.rating.interactor.BaseRatingInteractorImpl;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingDeleteInteractorImpl extends BaseRatingInteractorImpl {

	@Override
	public BasicThreadEvent execute(Object... args) throws Exception {

		long classPK = (long) args[0];
		String className = (String) args[1];
		int ratingGroupCounts = (int) args[2];

		JSONObject jsonObject =
			getScreensratingsentryService().deleteRatingsEntry(classPK, className, ratingGroupCounts);

		return new BasicThreadEvent(jsonObject);
	}
}
