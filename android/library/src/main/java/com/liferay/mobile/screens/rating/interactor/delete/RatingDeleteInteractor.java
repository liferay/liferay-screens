package com.liferay.mobile.screens.rating.interactor.delete;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.rating.RatingListener;

/**
 * @author Alejandro Hernández
 */
public interface RatingDeleteInteractor extends Interactor<RatingListener> {
	void deleteRating(long classPK, String className, int stepCount) throws Exception;
}
