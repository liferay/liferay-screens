package com.liferay.mobile.screens.rating.view;

import com.liferay.mobile.screens.base.view.BaseViewModel;

/**
 * @author Alejandro Hernández
 */
public interface RatingViewModel extends BaseViewModel {
    double getScore();

    void setScore(double score);

    void showFinishOperation(double score);
}
