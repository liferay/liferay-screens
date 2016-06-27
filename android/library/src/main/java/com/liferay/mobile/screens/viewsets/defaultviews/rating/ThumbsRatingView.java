package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.rating.RatingEntry;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public class ThumbsRatingView extends BaseRatingView implements View.OnClickListener {

	private ImageButton _negativeButton;
	private ImageButton _possitiveButton;
	private TextView _negativeCountLabel;
	private TextView _possitiveCountLabel;
	private int _negativeCount;
	private int _possitiveCount;
	private double _userScore;

	public ThumbsRatingView(Context context) {
		super(context);
	}

	public ThumbsRatingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ThumbsRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override public void onClick(View v) {
		final int id = v.getId();

		double score = -1;

		if (id == R.id.positiveRatingButton) {
			score = 1.0;
		} else if (id == R.id.negativeRatingButton) {
			score = 0.0;
		}

		if (score != -1) {
			String action = score == _userScore ? RatingScreenlet.DELETE_RATING_ACTION
				: RatingScreenlet.ADD_RATING_ACTION;
			getScreenlet().performUserAction(action, score);
		}
	}

	@Override public void setReadOnly(boolean readOnly) {
		_negativeButton.setOnClickListener(readOnly ? null : this);
		_negativeButton.setEnabled(!readOnly);

		_possitiveButton.setOnClickListener(readOnly ? null : this);
		_possitiveButton.setEnabled(!readOnly);
	}

	@Override public void showFinishOperation(String action, Object argument) {
		if (_progressBar != null) {
			_progressBar.setVisibility(View.GONE);
		}
		if (_content != null) {
			_content.setVisibility(View.VISIBLE);

			switch (action) {
				case RatingScreenlet.LOAD_RATINGS_ACTION:
					_negativeCount = _possitiveCount = 0;
					_userScore = -1;

					for (RatingEntry entry : (List<RatingEntry>) argument) {
						updateGlobalScore(entry.getScore());
					}

					updateCountLabels();

					break;
				case RatingScreenlet.LOAD_USER_RATING_ACTION:
					_userScore = ((RatingEntry) argument).getScore();
					updateGlobalScore(_userScore, true);
					break;
				case RatingScreenlet.ADD_RATING_ACTION:
					if (_userScore == -1) {
						setUserScore(((RatingEntry) argument).getScore());
					}
					break;
				case RatingScreenlet.UPDATE_RATING_ACTION:
					if (_userScore != -1) {
						setUserScore(((RatingEntry) argument).getScore());
					}
					break;
				case RatingScreenlet.DELETE_RATING_ACTION:
					if (_userScore != -1) {
						setUserScore(-1);
					}
					break;
				default:
					break;
			}
			updateButtons();
		}
	}

	@Override protected void onFinishInflate() {
		super.onFinishInflate();

		_negativeButton = (ImageButton) findViewById(R.id.negativeRatingButton);
		_possitiveButton = (ImageButton) findViewById(R.id.positiveRatingButton);

		_negativeCountLabel = (TextView) findViewById(R.id.negativeRatingCount);
		_possitiveCountLabel = (TextView) findViewById(R.id.positiveRatingCount);

		_negativeButton.setOnClickListener(this);
		_possitiveButton.setOnClickListener(this);
	}

	private void setUserScore(double score) {
		if (score == 0) {
			updateCountLabels(_possitiveCount, _negativeCount + 1);
		} else if (score == 1) {
			updateCountLabels(_possitiveCount + 1, _negativeCount);
		} else {
			updateCountLabels();
		}

		_userScore = score;
	}

	private void updateButtons() {
		if (_userScore == 1) {
			_negativeButton.setImageResource(R.drawable.default_thumb_down_outline);
			_possitiveButton.setImageResource(R.drawable.default_thumb_up);
		} else if (_userScore == 0) {
			_negativeButton.setImageResource(R.drawable.default_thumb_down);
			_possitiveButton.setImageResource(R.drawable.default_thumb_up_outline);
		} else {
			_negativeButton.setImageResource(R.drawable.default_thumb_down_outline);
			_possitiveButton.setImageResource(R.drawable.default_thumb_up_outline);
		}
	}

	private void updateCountLabels() {
		updateCountLabels(_possitiveCount, _negativeCount);
	}

	private void updateCountLabels(int possitiveCount, int negativeCount) {
		_possitiveCountLabel.setText(getContext().getString(R.string.rating_total, possitiveCount));
		_negativeCountLabel.setText(getContext().getString(R.string.rating_total, negativeCount));
	}

	private void updateGlobalScore(double score) {
		updateGlobalScore(score, false);
	}

	private void updateGlobalScore(double score, boolean backwards) {
		if (score == 0) {
			_negativeCount += backwards ? -1 : 1;
		} else {
			_possitiveCount += backwards ? -1 : 1;
		}
	}
}
