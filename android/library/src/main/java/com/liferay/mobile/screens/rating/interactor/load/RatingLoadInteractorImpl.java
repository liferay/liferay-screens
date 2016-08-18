package com.liferay.mobile.screens.rating.interactor.load;

import com.liferay.mobile.screens.rating.interactor.BaseRatingInteractorImpl;
import com.liferay.mobile.screens.rating.interactor.RatingEvent;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingLoadInteractorImpl extends BaseRatingInteractorImpl {

	@Override
	public RatingEvent execute(Object... args) throws Exception {

		long entryId = (long) args[0];
		long classPK = (long) args[1];
		String className = (String) args[2];
		int ratingGroupCounts = (int) args[3];

		validate(entryId, className, classPK);

		JSONObject jsonObject = getRatingsEntries(entryId, classPK, className, ratingGroupCounts);
		return entryId == 0 ? new RatingEvent(classPK, className, ratingGroupCounts, jsonObject)
			: new RatingEvent(entryId, jsonObject);
	}

	private JSONObject getRatingsEntries(long entryId, long classPK, String className, int ratingGroupCounts)
		throws Exception {

		ScreensratingsentryService service = getScreensratingsentryService();
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
