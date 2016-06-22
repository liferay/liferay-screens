package com.liferay.mobile.screens.rating.interactor.add;

import com.liferay.mobile.screens.rating.interactor.BaseRatingInteractor;

/**
 * @author Alejandro Hernández
 */
public interface AddRatingInteractor extends BaseRatingInteractor {
  void addRating(String className, long classPK, double score)
      throws Exception;
}
