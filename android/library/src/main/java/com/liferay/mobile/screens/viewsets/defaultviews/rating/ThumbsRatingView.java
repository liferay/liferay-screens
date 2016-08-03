package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingScreenlet;

/**
 * @author Alejandro Hernández
 */
public class ThumbsRatingView extends BaseRatingView implements View.OnClickListener {

	public ThumbsRatingView(Context context) {
		super(context);
	}

	public ThumbsRatingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ThumbsRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void onClick(View v) {
		final int id = v.getId();

		double score = -1;

		if (id == R.id.positiveRatingButton) {
			score = 1.0;
		} else if (id == R.id.negativeRatingButton) {
			score = 0.0;
		}

		if (score != -1) {
			String action =
				score == userScore ? RatingScreenlet.DELETE_RATING_ACTION : RatingScreenlet.UPDATE_RATING_ACTION;
			getScreenlet().performUserAction(action, score);
		}
	}

	@Override
	public void showFinishOperation(String action, AssetRating assetRating) {
		if (progressBar != null) {
			progressBar.setVisibility(View.GONE);
		}
		if (content != null) {
			content.setVisibility(View.VISIBLE);

			userScore = assetRating.getUserScore();

			negativeCountLabel.setText(getContext().getString(R.string.rating_total, assetRating.getRatings()[0]));
			positiveCountLabel.setText(getContext().getString(R.string.rating_total, assetRating.getRatings()[1]));

			if (userScore == 1) {
				negativeButton.setImageResource(R.drawable.default_thumb_down_outline);
				positiveButton.setImageResource(R.drawable.default_thumb_up);
			} else if (userScore == 0) {
				negativeButton.setImageResource(R.drawable.default_thumb_down);
				positiveButton.setImageResource(R.drawable.default_thumb_up_outline);
			} else {
				negativeButton.setImageResource(R.drawable.default_thumb_down_outline);
				positiveButton.setImageResource(R.drawable.default_thumb_up_outline);
			}
		}
	}

	@Override
	public void enableEdition(boolean editable) {
		negativeButton.setOnClickListener(editable ? this : null);
		negativeButton.setEnabled(editable);

		positiveButton.setOnClickListener(editable ? this : null);
		positiveButton.setEnabled(editable);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		negativeButton = (ImageButton) findViewById(R.id.negativeRatingButton);
		positiveButton = (ImageButton) findViewById(R.id.positiveRatingButton);

		negativeCountLabel = (TextView) findViewById(R.id.negativeRatingCount);
		positiveCountLabel = (TextView) findViewById(R.id.positiveRatingCount);

		negativeButton.setOnClickListener(this);
		positiveButton.setOnClickListener(this);
	}

	private ImageButton negativeButton;
	private ImageButton positiveButton;
	private TextView negativeCountLabel;
	private TextView positiveCountLabel;

	private double userScore;
}
