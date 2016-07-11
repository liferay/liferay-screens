package com.liferay.mobile.screens.rating.interactor.update;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.rating.RatingListener;

/**
 * @author Alejandro Hernández
 */
public interface RatingUpdateInteractor extends Interactor<RatingListener> {
	void addRating(long classPK, String className, double score, int stepCount) throws Exception;
}
