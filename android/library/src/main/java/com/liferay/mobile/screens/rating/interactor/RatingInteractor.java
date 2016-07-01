package com.liferay.mobile.screens.rating.interactor;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.rating.RatingListener;

/**
 * @author Alejandro Hernández
 */
public interface RatingInteractor extends Interactor<RatingListener> {
	void loadRatings(long entryId, long classPK, String className, int stepCount) throws Exception;

	void deleteRating(long classPK, String className, int stepCount) throws Exception;

	void addRating(long classPK, String className, double score, int stepCount) throws Exception;
}
