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
			if (progressBar != null) {
				progressBar.setVisibility(VISIBLE);
			}
			if (content != null) {
				content.setVisibility(GONE);
			}
		}
	}

	@Override
	public void showFinishOperation(String actionName) {
		throw new AssertionError();
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		if (progressBar != null) {
			progressBar.setVisibility(GONE);
		}

		if (content != null) {
			content.setVisibility(VISIBLE);
		}

		LiferayLogger.e(getContext().getString(R.string.ratings_error), e);
	}

	@Override
	public BaseScreenlet getScreenlet() {
		return screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		this.screenlet = screenlet;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		progressBar = (ProgressBar) findViewById(R.id.liferay_rating_progress);
		content = (ViewGroup) findViewById(R.id.liferay_rating_content);
	}

	protected ProgressBar progressBar;
	protected ViewGroup content;
	private BaseScreenlet screenlet;
}
