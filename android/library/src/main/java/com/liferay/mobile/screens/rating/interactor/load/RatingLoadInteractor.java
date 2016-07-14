package com.liferay.mobile.screens.rating.interactor.load;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.rating.RatingListener;

/**
 * @author Alejandro Hernández
 */
public interface RatingLoadInteractor extends Interactor<RatingListener> {
	void loadRatings(long entryId, long classPK, String className, int ratingsGroupCount) throws Exception;
}
