package com.liferay.mobile.screens.rating;

import com.liferay.mobile.screens.base.thread.listener.OfflineListenerNew;

/**
 * @author Alejandro Hernández
 */
public interface RatingListener extends OfflineListenerNew {

	void onRatingOperationSuccess(AssetRating assetRating);
}
