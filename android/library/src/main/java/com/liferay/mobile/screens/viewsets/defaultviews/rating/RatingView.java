package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.rating.view.RatingViewModel;

/**
 * @author Alejandro Hernández
 */
public class RatingView extends LinearLayout implements RatingViewModel, View.OnClickListener {

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
    public void showFinishOperation(double score) {

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
        return _screenlet;
    }

    @Override
    public void setScreenlet(BaseScreenlet screenlet) {
        _screenlet = screenlet;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        _negativeButton = (Button) findViewById(R.id.negativeRatingButton);
        _possitiveButton = (Button) findViewById(R.id.positiveRatingButton);

        _negativeButton.setOnClickListener(this);
        _possitiveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    private Button _negativeButton;
    private Button _possitiveButton;
    private BaseScreenlet _screenlet;
}
