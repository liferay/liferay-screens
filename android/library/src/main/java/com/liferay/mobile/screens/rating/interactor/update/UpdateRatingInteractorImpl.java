package com.liferay.mobile.screens.rating.interactor.update;

import android.support.annotation.NonNull;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v7.ratingsentry.RatingsEntryService;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.rating.interactor.RatingEntryFactory;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.security.InvalidParameterException;
import org.json.JSONException;

/**
 * @author Alejandro Hernández
 */
public class UpdateRatingInteractorImpl extends BaseRemoteInteractor<RatingListener>
	implements UpdateRatingInteractor {
	public UpdateRatingInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
		_ratingsEntryService = getRatingsEntryService();
	}

	@NonNull private RatingsEntryService getRatingsEntryService() {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new UpdateRatingCallback(getTargetScreenletId()));
		return new RatingsEntryService(session);
	}

	@Override public void addRating(String className, long classPK, double score) throws Exception {
		validate(score);
		_ratingsEntryService.updateEntry(className, classPK, score);
	}

	protected void validate(double score) throws InvalidParameterException {
		if ((score > 1) || (score < 0)) {
			throw new InvalidParameterException(
				"Score " + score + " is not a double value between 0 and 1");
		}
	}

	public void onEvent(UpdateRatingEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onAddRatingEntryFailure(event.getException());
		} else {
			try {
				getListener().onAddRatingEntrySuccess(
					RatingEntryFactory.createEntry(event.getJSONObject()));
			} catch (JSONException e) {
				LiferayLogger.e(e.getMessage());
			}
		}
	}

	private final RatingsEntryService _ratingsEntryService;
}
