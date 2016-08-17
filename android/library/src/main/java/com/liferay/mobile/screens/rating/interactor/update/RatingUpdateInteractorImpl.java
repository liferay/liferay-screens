package com.liferay.mobile.screens.rating.interactor.update;

import com.liferay.mobile.screens.rating.interactor.BaseRatingInteractorImpl;
import com.liferay.mobile.screens.rating.interactor.RatingEvent;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import java.security.InvalidParameterException;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingUpdateInteractorImpl extends BaseRatingInteractorImpl {

	@Override
	public RatingEvent execute(Object... args) throws Exception {

		long classPK = (long) args[0];
		String className = (String) args[1];
		double score = (double) args[2];
		int ratingGroupCounts = (int) args[3];

		validate(score);

		ScreensratingsentryService service = getScreensratingsentryService();
		JSONObject jsonObject = service.updateRatingsEntry(classPK, className, score, ratingGroupCounts);
		return new RatingEvent(classPK, className, jsonObject);
	}

	protected void validate(double score) throws InvalidParameterException {
		if ((score > 1) || (score < 0)) {
			throw new InvalidParameterException("Score " + score + " is not a double value between 0 and 1");
		}
	}
}
