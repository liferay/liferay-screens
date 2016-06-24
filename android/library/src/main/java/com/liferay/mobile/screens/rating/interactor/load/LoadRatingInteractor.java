package com.liferay.mobile.screens.rating.interactor.load;

import com.liferay.mobile.screens.rating.interactor.BaseRatingInteractor;

/**
 * @author Alejandro Hernández
 */
public interface LoadRatingInteractor extends BaseRatingInteractor {
	void loadRatings(long assetId) throws Exception;
}
