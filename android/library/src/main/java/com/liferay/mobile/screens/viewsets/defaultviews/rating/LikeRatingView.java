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
public class LikeRatingView extends BaseRatingView implements View.OnClickListener {

	public LikeRatingView(Context context) {
		super(context);
	}

	public LikeRatingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LikeRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void showFinishOperation(String action, AssetRating assetRating) {
		if (_progressBar != null) {
			_progressBar.setVisibility(View.GONE);
		}
		if (_content != null) {
			_content.setVisibility(View.VISIBLE);

			_hasUserRate = assetRating.getUserScore() != -1;
			_likeCountLabel.setText(getContext().getString(R.string.rating_total, assetRating.getTotalCount()));
			_likeButton.setImageResource(
				_hasUserRate ? R.drawable.default_thumb_up : R.drawable.default_thumb_up_outline);
		}
	}

	@Override
	public void enableEdition(boolean editable) {
		_likeButton.setOnClickListener(editable ? this : null);
		_likeButton.setEnabled(editable);
	}

	@Override
	public void onClick(View v) {
		String action = _hasUserRate ? RatingScreenlet.DELETE_RATING_ACTION : RatingScreenlet.UPDATE_RATING_ACTION;
		getScreenlet().performUserAction(action, 1.0);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_likeButton = (ImageButton) findViewById(R.id.likeRatingButton);
		_likeCountLabel = (TextView) findViewById(R.id.likeCountLabel);
	}

	private ImageButton _likeButton;
	private TextView _likeCountLabel;

	private boolean _hasUserRate = false;
}
