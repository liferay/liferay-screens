package com.liferay.mobile.screens.rating;

import java.util.List;

/**
 * @author Alejandro Hernández
 */
public interface RatingListener {
  void onRetrieveRatingEntriesFailure(Exception exception);
  void onRetrieveRatingEntriesSuccess(List<RatingEntry> ratings);
}
