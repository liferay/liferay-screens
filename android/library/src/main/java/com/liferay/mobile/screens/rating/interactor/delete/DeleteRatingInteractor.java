package com.liferay.mobile.screens.rating.interactor.delete;

import com.liferay.mobile.screens.rating.interactor.BaseRatingInteractor;

/**
 * @author Alejandro Hernández
 */
public interface DeleteRatingInteractor extends BaseRatingInteractor {
	void deleteRating(String className, long classPK) throws Exception;
}
