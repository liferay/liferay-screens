package com.liferay.mobile.screens.rating.interactor.update;

import com.liferay.mobile.screens.rating.interactor.BaseRatingInteractor;

/**
 * @author Alejandro Hernández
 */
public interface UpdateRatingInteractor extends BaseRatingInteractor {
  void addRating(String className, long classPK, double score)
      throws Exception;
}
