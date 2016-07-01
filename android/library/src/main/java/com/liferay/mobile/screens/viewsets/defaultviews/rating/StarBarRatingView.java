package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingScreenlet;

/**
 * @author Alejandro Hernández
 */
public class StarBarRatingView extends BaseRatingView
	implements RatingBar.OnRatingBarChangeListener {
	public StarBarRatingView(Context context) {
		super(context);
	}

	public StarBarRatingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StarBarRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override public void showFinishOperation(String action, Object argument) {
		if (_progressBar != null) {
			_progressBar.setVisibility(View.GONE);
		}
		if (_content != null) {
			_content.setVisibility(View.VISIBLE);

			final AssetRating assetRating = (AssetRating) argument;

			setRatingBarRate(_userRatingBar, (float) assetRating.getUserScore());
			setRatingBarRate(_averageRatingBar, (float) assetRating.getAverage());
			_totalCountTextView.setText(
				getContext().getString(R.string.rating_count, assetRating.getTotalCount()));
		}
	}

	@Override public void setReadOnly(boolean readOnly) {
		_userRatingBar.setEnabled(!readOnly);

		if (readOnly) {
			_userRatingBar.setVisibility(View.GONE);
			_averageContainer.setOrientation(VERTICAL);
			_totalCountTextView.setPadding(0, 0, 0, 0);
		} else {
			_userRatingBar.setVisibility(View.VISIBLE);
			_averageContainer.setOrientation(HORIZONTAL);
			_totalCountTextView.setPadding(10, 0, 0, 0);
		}
	}

	@Override protected void onFinishInflate() {
		super.onFinishInflate();

		_userRatingBar = (RatingBar) findViewById(R.id.userRatingBar);
		_averageRatingBar = (RatingBar) findViewById(R.id.averageRatingBar);
		_totalCountTextView = (TextView) findViewById(R.id.totalCountTextView);
		_averageContainer = (LinearLayout) findViewById(R.id.average_container);

		_userRatingBar.setOnRatingBarChangeListener(this);
	}

	@Override public void onRatingChanged(RatingBar ratingBar, float score, boolean fromUser) {
		if (fromUser) {
			double normalizedScore = score / ratingBar.getNumStars();
			getScreenlet().performUserAction(RatingScreenlet.ADD_RATING_ACTION,
				(double) normalizedScore);
		}
	}

	private void setRatingBarRate(RatingBar ratingBar, float rating) {
		ratingBar.setRating(rating * ratingBar.getNumStars());
	}

	private TextView _totalCountTextView;
	private RatingBar _averageRatingBar;
	private RatingBar _userRatingBar;
	private LinearLayout _averageContainer;
}
