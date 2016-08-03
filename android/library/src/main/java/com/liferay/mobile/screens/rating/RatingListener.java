package com.liferay.mobile.screens.rating;

/**
 * @author Alejandro Hernández
 */
public interface RatingListener {
	void onRatingOperationFailure(Exception exception);

	void onRatingOperationSuccess(AssetRating assetRating);
}
