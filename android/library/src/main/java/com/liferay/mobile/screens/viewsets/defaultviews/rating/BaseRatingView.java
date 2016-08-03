package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.rating.view.RatingViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;

import static com.liferay.mobile.screens.rating.RatingScreenlet.LOAD_RATINGS_ACTION;

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

	@Override
	public void showStartOperation(String actionName) {
		if (LOAD_RATINGS_ACTION.equals(actionName)) {
			if (_progressBar != null) {
				_progressBar.setVisibility(VISIBLE);
			}
			if (_content != null) {
				_content.setVisibility(GONE);
			}
		}
	}

	@Override
	public void showFinishOperation(String actionName) {
		throw new AssertionError();
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		if (_progressBar != null) {
			_progressBar.setVisibility(GONE);
		}

		if (_content != null) {
			_content.setVisibility(VISIBLE);
		}

		LiferayLogger.e(getContext().getString(R.string.ratings_error), e);
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

		_progressBar = (ProgressBar) findViewById(R.id.liferay_rating_progress);
		_content = (ViewGroup) findViewById(R.id.liferay_rating_content);
	}

	protected ProgressBar _progressBar;
	protected ViewGroup _content;
	private BaseScreenlet _screenlet;
}
