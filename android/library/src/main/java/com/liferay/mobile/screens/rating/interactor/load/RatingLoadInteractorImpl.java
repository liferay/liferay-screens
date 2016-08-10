package com.liferay.mobile.screens.rating.interactor.load;

import android.support.annotation.NonNull;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;
import com.liferay.mobile.screens.rating.interactor.BaseRatingInteractorImpl;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingLoadInteractorImpl extends BaseRatingInteractorImpl implements RatingLoadInteractor {

	public RatingLoadInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void loadRatings(long entryId, long classPK, String className, int ratingsGroupCount) throws Exception {
		validate(entryId, className, classPK);

		ScreensratingsentryService service = getScreensratingsentryService();
		if (entryId != 0) {
			service.getRatingsEntries(entryId, ratingsGroupCount);
		} else {
			service.getRatingsEntries(classPK, className, ratingsGroupCount);
		}
	}

	public void onEvent(RatingLoadEvent event) {
		processEvent(event);
	}

	protected void validate(long entryId, String className, long classPK) {
		if (entryId == 0 && (className == null || classPK == 0)) {
			throw new IllegalArgumentException("Either entryId or className & classPK cannot" + "be empty");
		}
	}

	@NonNull
	@Override
	protected InteractorAsyncTaskCallback<JSONObject> getCallback() {
		return new RatingLoadCallback(getTargetScreenletId());
	}
}
