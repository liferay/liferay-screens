package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.rating.view.RatingViewModel;

/**
 * @author Alejandro Hernández
 */
public class RatingView extends LinearLayout implements RatingViewModel {

    public RatingView(Context context) {
        super(context);
    }

    public RatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public double getScore() {
        return 0;
    }

    @Override
    public void setScore(double score) {

    }

    @Override
    public void showStartOperation(String actionName) {

    }

    @Override
    public void showFinishOperation(String actionName) {

    }

    @Override
    public void showFailedOperation(String actionName, Exception e) {

    }

    @Override
    public BaseScreenlet getScreenlet() {
        return null;
    }

    @Override
    public void setScreenlet(BaseScreenlet screenlet) {

    }
}
