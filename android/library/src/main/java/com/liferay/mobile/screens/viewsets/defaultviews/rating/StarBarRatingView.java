package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RatingBar;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.rating.AssetRating;

import static com.liferay.mobile.screens.rating.RatingScreenlet.UPDATE_RATING_ACTION;

/**
 * @author Alejandro Hernández
 */
public class StarBarRatingView extends BaseRatingView implements RatingBar.OnRatingBarChangeListener {

	public StarBarRatingView(Context context) {
		super(context);
	}

	public StarBarRatingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StarBarRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void showFinishOperation(String action, AssetRating assetRating) {
		if (progressBar != null) {
			progressBar.setVisibility(GONE);
		}
		if (content != null) {
			content.setVisibility(VISIBLE);

			userRatingBar.setRating(getRating(assetRating.getTotalScore(), userRatingBar.getNumStars()));
			averageRatingBar.setRating(getRating(assetRating.getAverage(), averageRatingBar.getNumStars()));
			totalCountTextView.setText(getResources().getString(R.string.rating_count, assetRating.getTotalCount()));
		}
	}

	@Override
	public void enableEdition(boolean editable) {
		userRatingBar.setEnabled(editable);
	}

	@Override
	public void onRatingChanged(RatingBar ratingBar, float score, boolean fromUser) {
		if (fromUser) {
			double normalizedScore = score / ratingBar.getNumStars();
			getScreenlet().performUserAction(UPDATE_RATING_ACTION, normalizedScore);
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		userRatingBar = (RatingBar) findViewById(R.id.userRatingBar);
		averageRatingBar = (RatingBar) findViewById(R.id.averageRatingBar);
		totalCountTextView = (TextView) findViewById(R.id.totalCountTextView);

		userRatingBar.setOnRatingBarChangeListener(this);
	}

	private float getRating(double score, int numStars) {
		return (float) score * numStars;
	}

	private TextView totalCountTextView;
	private RatingBar averageRatingBar;
	private RatingBar userRatingBar;
}
