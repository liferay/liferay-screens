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
				score == _userScore ? RatingScreenlet.DELETE_RATING_ACTION : RatingScreenlet.UPDATE_RATING_ACTION;
			getScreenlet().performUserAction(action, score);
		}
	}

	@Override
	public void showFinishOperation(String action, AssetRating assetRating) {
		if (_progressBar != null) {
			_progressBar.setVisibility(View.GONE);
		}
		if (_content != null) {
			_content.setVisibility(View.VISIBLE);

			_userScore = assetRating.getUserScore();

			_negativeCountLabel.setText(getContext().getString(R.string.rating_total, assetRating.getRatings()[0]));
			_positiveCountLabel.setText(getContext().getString(R.string.rating_total, assetRating.getRatings()[1]));

			if (_userScore == 1) {
				_negativeButton.setImageResource(R.drawable.default_thumb_down_outline);
				_positiveButton.setImageResource(R.drawable.default_thumb_up);
			} else if (_userScore == 0) {
				_negativeButton.setImageResource(R.drawable.default_thumb_down);
				_positiveButton.setImageResource(R.drawable.default_thumb_up_outline);
			} else {
				_negativeButton.setImageResource(R.drawable.default_thumb_down_outline);
				_positiveButton.setImageResource(R.drawable.default_thumb_up_outline);
			}
		}
	}

	@Override
	public void enableEdition(boolean editable) {
		_negativeButton.setOnClickListener(editable ? this : null);
		_negativeButton.setEnabled(editable);

		_positiveButton.setOnClickListener(editable ? this : null);
		_positiveButton.setEnabled(editable);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_negativeButton = (ImageButton) findViewById(R.id.negativeRatingButton);
		_positiveButton = (ImageButton) findViewById(R.id.positiveRatingButton);

		_negativeCountLabel = (TextView) findViewById(R.id.negativeRatingCount);
		_positiveCountLabel = (TextView) findViewById(R.id.positiveRatingCount);

		_negativeButton.setOnClickListener(this);
		_positiveButton.setOnClickListener(this);
	}

	private ImageButton _negativeButton;
	private ImageButton _positiveButton;
	private TextView _negativeCountLabel;
	private TextView _positiveCountLabel;

	private double _userScore;
}
