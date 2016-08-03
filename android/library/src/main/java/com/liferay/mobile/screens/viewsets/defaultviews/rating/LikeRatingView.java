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
		if (progressBar != null) {
			progressBar.setVisibility(View.GONE);
		}
		if (content != null) {
			content.setVisibility(View.VISIBLE);

			hasUserRate = assetRating.getUserScore() != -1;
			likeCountLabel.setText(getContext().getString(R.string.rating_total, assetRating.getTotalCount()));
			likeButton.setImageResource(
				hasUserRate ? R.drawable.default_thumb_up : R.drawable.default_thumb_up_outline);
		}
	}

	@Override
	public void enableEdition(boolean editable) {
		likeButton.setOnClickListener(editable ? this : null);
		likeButton.setEnabled(editable);
	}

	@Override
	public void onClick(View v) {
		String action = hasUserRate ? RatingScreenlet.DELETE_RATING_ACTION : RatingScreenlet.UPDATE_RATING_ACTION;
		getScreenlet().performUserAction(action, 1.0);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		likeButton = (ImageButton) findViewById(R.id.likeRatingButton);
		likeCountLabel = (TextView) findViewById(R.id.likeCountLabel);
	}

	private ImageButton likeButton;
	private TextView likeCountLabel;

	private boolean hasUserRate = false;
}
