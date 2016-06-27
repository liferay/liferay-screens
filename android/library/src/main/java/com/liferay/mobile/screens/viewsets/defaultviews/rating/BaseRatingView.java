package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import com.liferay.mobile.screens.rating.view.RatingViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Alejandro Hernández
 */
public abstract class BaseRatingView extends LinearLayout implements RatingViewModel {

	public BaseRatingView(Context context) {
		super(context);
	}
	public BaseRatingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public BaseRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override public void showStartOperation(String actionName) {
		if (actionName == RatingScreenlet.LOAD_RATINGS_ACTION) {
			if (_progressBar != null) {
				_progressBar.setVisibility(View.VISIBLE);
			}
			if (_content != null) {
				_content.setVisibility(View.GONE);
			}
		}
	}

	@Override public void showFinishOperation(String actionName) {
		showFinishOperation(actionName, null);
	}

	@Override public void showFailedOperation(String actionName, Exception e) {
		if (_progressBar != null) {
			_progressBar.setVisibility(View.GONE);
		}

		if (_content != null) {
			_content.setVisibility(View.VISIBLE);
		}

		switch (actionName) {
			case RatingScreenlet.LOAD_RATINGS_ACTION:
				LiferayLogger.e(getContext().getString(R.string.loading_ratings_error), e);
				break;
			case RatingScreenlet.LOAD_USER_RATING_ACTION:
				LiferayLogger.e(getContext().getString(R.string.loading_user_rating_error), e);
				break;
			case RatingScreenlet.ADD_RATING_ACTION:
				LiferayLogger.e(getContext().getString(R.string.adding_error_rating), e);
				break;
			case RatingScreenlet.UPDATE_RATING_ACTION:
				LiferayLogger.e(getContext().getString(R.string.updating_rating_error), e);
				break;
			case RatingScreenlet.DELETE_RATING_ACTION:
				LiferayLogger.e(getContext().getString(R.string.deleting_rating_error), e);
				break;
			default:
				LiferayLogger.e(getContext().getString(R.string.ratings_error), e);
				break;
		}
	}

	@Override public BaseScreenlet getScreenlet() {
		return _screenlet;
	}

	@Override public void setScreenlet(BaseScreenlet screenlet) {
		_screenlet = screenlet;
	}

	@Override protected void onFinishInflate() {
		super.onFinishInflate();

		_progressBar = (ProgressBar) findViewById(R.id.liferay_rating_progress);
		_content = (LinearLayout) findViewById(R.id.liferay_rating_content);
	}
	protected ProgressBar _progressBar;
	protected LinearLayout _content;
	private BaseScreenlet _screenlet;
	private boolean _readOnly;
}
