package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public class ReactionsRatingView extends BaseRatingView implements View.OnClickListener {

	public ReactionsRatingView(Context context) {
		super(context);
	}

	public ReactionsRatingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ReactionsRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void showFinishOperation(String actionName, AssetRating assetRating) {
		if (progressBar != null) {
			progressBar.setVisibility(View.GONE);
		}
		if (content != null) {
			content.setVisibility(View.VISIBLE);

			for (ImageButton button : reactions) {
				button.getDrawable()
					.setColorFilter(ContextCompat.getColor(getContext(), android.R.color.darker_gray),
						PorterDuff.Mode.SRC_ATOP);
			}

			int[] ratings = assetRating.getRatings();

			if (ratings.length != reactions.size()) {
				throw new AssertionError("The number of buttons is different than the step count");
			} else {
				for (int i = 0; i < reactions.size(); i++) {
					labels.get(i).setText(Integer.toString(ratings[i]));
				}
			}

			if ((userScore = assetRating.getUserScore()) != -1) {

				TypedValue typedValue = new TypedValue();
				getContext().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);

				reactions.get(userScore == 1 ? (reactions.size() - 1) : (int) (userScore * reactions.size()))
					.getDrawable()
					.setColorFilter(typedValue.data, PorterDuff.Mode.SRC_ATOP);
			}
		}
	}

	@Override
	public void enableEdition(boolean editable) {
		for (ImageButton button : reactions) {
			button.setOnClickListener(editable ? this : null);
			button.setEnabled(editable);
		}
	}

	@Override
	public void onClick(View v) {
		final int id = v.getId();

		double score = -1;

		for (int i = 0; i < reactions.size(); i++) {
			if (reactions.get(i).getId() == id) {
				score = (double) i / reactions.size();
				break;
			}
		}

		if (score != -1) {
			String action =
				score == userScore ? RatingScreenlet.DELETE_RATING_ACTION : RatingScreenlet.UPDATE_RATING_ACTION;
			getScreenlet().performUserAction(action, score);
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		if (content != null) {
			reactions = new ArrayList<>();
			labels = new ArrayList<>();
			findChildViewsIn(content);
		}
	}

	private void findChildViewsIn(ViewGroup viewGroup) {
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View view = viewGroup.getChildAt(i);
			if (view instanceof ImageButton) {
				reactions.add((ImageButton) view);
			} else if (view instanceof TextView) {
				labels.add((TextView) view);
			} else if (view instanceof ViewGroup) {
				findChildViewsIn((ViewGroup) view);
			}
		}
	}

	private List<ImageButton> reactions;
	private List<TextView> labels;
	private double userScore;
}
