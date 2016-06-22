package com.liferay.mobile.screens.rating;

import java.util.List;

/**
 * @author Alejandro Hernández
 */
public interface RatingListener {
  void onRetrieveRatingEntriesFailure(Exception exception);
  void onRetrieveRatingEntriesSuccess(long classPK, String className, List<RatingEntry> ratings);
  void onAddRatingEntryFailure(Exception exception);
  void onAddRatingEntrySuccess(RatingEntry entry);
  void onDeleteRatingEntryFailure(Exception exception);
  void onDeleteRatingEntrySuccess();
}
